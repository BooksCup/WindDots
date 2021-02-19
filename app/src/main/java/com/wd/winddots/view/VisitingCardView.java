package com.wd.winddots.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.bean.UserInfoBean;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;

import cn.jmessage.support.qiniu.android.utils.StringUtils;

public class VisitingCardView extends RelativeLayout implements View.OnClickListener {

    private ImageView mIcon;
    private TextView mNameTextView;
    private TextView mPositionTextView;
    private TextView mPhoneTextView;
    private TextView mEnterpriseTextView;
    private TextView mEmailTextView;
    private TextView mAddressTextView;

    private PopupWindow mPopupWindow;

    private UserInfoBean mUserInfoBean;

    private View body;

    public void setBody(View body) {
        this.body = body;
    }

    public VisitingCardView(Context context) {
        super(context);

        setUpUI();
    }


    public VisitingCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpUI();
    }


    public VisitingCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpUI();
    }


    private void setUpUI() {
        //View view = View.inflate(getContext(), R.layout.view_visitingcard,null);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_visitingcard, this, false);
        mIcon = view.findViewById(R.id.visiting_card_icon);
        mNameTextView = view.findViewById(R.id.visiting_card_name);
        mPositionTextView = view.findViewById(R.id.visiting_card_position);
        mPhoneTextView = view.findViewById(R.id.visiting_card_phone);
        mEnterpriseTextView = view.findViewById(R.id.visiting_card_enterprise);
        mEmailTextView = view.findViewById(R.id.visiting_card_email);
        mAddressTextView = view.findViewById(R.id.visiting_card_address);
        ImageView sharedIv = view.findViewById(R.id.iv_share);
        sharedIv.setOnClickListener(this);
        this.addView(view);

    }


    public void setUpData(UserInfoBean userInfoBean) {

        mUserInfoBean = userInfoBean;

        GlideApp.with(getContext()).
                load(userInfoBean.getAvatar() + Utils.OSSImageSize(100)).
                apply(RequestOptions.bitmapTransform(new CircleCrop())).
                into(mIcon);

        String jobName = userInfoBean.getJobName();

        if (StringUtils.isNullOrEmpty(jobName)) {
            jobName = "";
        }
        mNameTextView.setText(userInfoBean.getName());
        mPhoneTextView.setText(userInfoBean.getPhone());
        mPositionTextView.setText(jobName);
        mPhoneTextView.setText(userInfoBean.getPhone());
        mEnterpriseTextView.setText(userInfoBean.getEnterpriseName());
        mEmailTextView.setText(userInfoBean.getEmail());
        mAddressTextView.setText(userInfoBean.getCompanyAddress());


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_share:
                initPopupWindow();
                break;
            case R.id.background:
                dismissPopupWindow();
                break;
            case R.id.ll_sharetosession:
                share(SendMessageToWX.Req.WXSceneSession);
                dismissPopupWindow();
                break;
            case R.id.ll_sharetotimeline:
                share(SendMessageToWX.Req.WXSceneTimeline);
                dismissPopupWindow();
                break;
            case R.id.ll_copyurl:
                //initPopupWindow();
                copyUrl();
                dismissPopupWindow();
                break;
        }
    }


    /**
     * 初始化首页弹出框
     */
    private void initPopupWindow() {

        if (mUserInfoBean == null) {
            return;
        }

        View mPopupView = View.inflate(getContext(), R.layout.pop_share, null);
//        mPopupView = new PopShortcut(getContext());
//        mPopupView.setTabBottomBarClickListener(this);
        mPopupWindow = new PopupWindow(mPopupView, LinearLayout.LayoutParams.MATCH_PARENT, Utils.getScreenHeight(getContext()));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setClippingEnabled(false);

        View background = mPopupView.findViewById(R.id.background);
        LinearLayout shareToSession = mPopupView.findViewById(R.id.ll_sharetosession);
        LinearLayout shareToTimeLine = mPopupView.findViewById(R.id.ll_sharetotimeline);
        LinearLayout copyUrl = mPopupView.findViewById(R.id.ll_copyurl);

        background.setOnClickListener(this);
        shareToSession.setOnClickListener(this);
        shareToTimeLine.setOnClickListener(this);
        copyUrl.setOnClickListener(this);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        mPopupWindow.setBackgroundDrawable(dw);
        mPopupWindow.showAtLocation(this, Gravity.TOP | Gravity.LEFT, 0, 0);
    }

    private void copyUrl(){
        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
        String webpageUrl = "http://wd.cfpu.com/card/?userId=" + mUserInfoBean.getId() + "&fromUserId=" + SpHelper.getInstance(getContext()).getUserId();
        ClipData mClipData = ClipData.newPlainText("Label", webpageUrl);
// 将ClipData内容放到系统剪贴板里。
        assert cm != null;
        cm.setPrimaryClip(mClipData);
        Toast.makeText(getContext(), getContext().getString(R.string.share_copyurl_success), Toast.LENGTH_LONG).show();
    }


    private void share(final int scene) {
        final IWXAPI wxapi = WXAPIFactory.createWXAPI(getContext(), "wx2cfb08abb06edfdf");
        // 检查手机或者模拟器是否安装了微信
        if (!wxapi.isWXAppInstalled()) {
            Toast.makeText(getContext(), getContext().getString(R.string.share_noinstall_wechat), Toast.LENGTH_LONG).show();
            return;
        }

        // 初始化一个WXWebpageObject对象
        WXWebpageObject webpageObject = new WXWebpageObject();
        // 填写网页的url
        webpageObject.webpageUrl = "http://wd.cfpu.com/card/?userId=" + mUserInfoBean.getId() + "&fromUserId=" + SpHelper.getInstance(getContext()).getUserId();

        // 用WXWebpageObject对象初始化一个WXMediaMessage对象

        String email = getContext().getString(R.string.share_position) + Utils.nullOrEmpty(mUserInfoBean.getEmail());

        if (email.length() > 19) {
            email = email.substring(0, 16) + "...";
        }

        final WXMediaMessage msg = new WXMediaMessage(webpageObject);
        // 填写网页标题、描述、位图
        msg.title = mUserInfoBean.getName();
        msg.description = getContext().getString(R.string.share_position) + Utils.nullOrEmpty(mUserInfoBean.getJobName()) +  "\n" + email + "\n" + getContext().getString(R.string.share_enterprise)  + Utils.nullOrEmpty(mUserInfoBean.getEnterpriseName());
        // 如果没有位图，可以传null，会显示默认的图片


        ImageLoadingListener listener = new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Log.e("net666", String.valueOf(loadedImage));
                msg.setThumbImage(loadedImage);
                // 构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                // transaction用于唯一标识一个请求（可自定义）
                req.transaction = "webpage";
                // 上文的WXMediaMessage对象
                req.message = msg;
                // SendMessageToWX.Req.WXSceneSession是分享到好友会话
                // SendMessageToWX.Req.WXSceneTimeline是分享到朋友圈
                req.scene = scene;
                // 向微信发送请求
                wxapi.sendReq(req);

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        };
        ImageLoader.getInstance().loadImage(mUserInfoBean.getAvatar() + Utils.OSSImageSize(100), listener);
    }


    private void dismissPopupWindow() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }


}
