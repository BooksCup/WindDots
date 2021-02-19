package com.wd.winddots.mvp.widget.adapter.rv;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.wd.winddots.GlideApp;
import com.wd.winddots.MyApplication;
import com.wd.winddots.R;
import com.wd.winddots.bean.PrivateChatMsgModeBean;
import com.wd.winddots.bean.resp.PrivateChatHistoryBean;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.CommonUtil;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.glide.CornerTransform;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class PrivateChatAdapter extends BaseQuickAdapter<PrivateChatHistoryBean.MessageListBean, BaseViewHolder> {

    private final CornerTransform mTransformation;


    private String meAvatar = "";
    private String elseAvatar = "";

    public final MsgDataManager dataManager;
    public final CompositeSubscription compositeSubscription;


    //头像数据
    public void setPrivateChatHistoryBean(PrivateChatHistoryBean privateChatHistoryBean) {
        List<PrivateChatHistoryBean.AvatarMapBean> avatarMap = privateChatHistoryBean.getAvatarMap();
        int avatarMapSize = avatarMap.size();
        for (int i = 0; i < avatarMapSize; i++) {
            PrivateChatHistoryBean.AvatarMapBean avatarMapBean = avatarMap.get(i);
            String userAvatar = avatarMapBean.getUserAvatar();
            String userId = avatarMapBean.getUserId();

            if (MyApplication.USER_ID.equals(userId)) {
                meAvatar = userAvatar;
            } else {
                elseAvatar = userAvatar;
            }
        }
    }

    public PrivateChatAdapter(Context context, int layoutResId, @Nullable List<PrivateChatHistoryBean.MessageListBean> data) {
        super(layoutResId, data);
        mTransformation = new CornerTransform(context, CommonUtil.dip2px(context, 5));
        //只是绘制左上角和右上角圆角
        mTransformation.setExceptCorner(false, false, false, false);


        dataManager = new MsgDataManager();
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    protected void convert(BaseViewHolder helper, PrivateChatHistoryBean.MessageListBean item) {

        helper.addOnClickListener(R.id.item_private_chat_else_msg_mode_chat_ll);

        RelativeLayout elseLayout = (RelativeLayout) helper.getView(R.id.item_private_chat_else_layout);
        RelativeLayout meLayout = (RelativeLayout) helper.getView(R.id.item_private_chat_me_layout);

        RelativeLayout meTcModeLayout = (RelativeLayout) helper.getView(R.id.item_private_chat_me_text_chat_mode_layout);//气泡模式布局
        RelativeLayout elseTcModeLayout = (RelativeLayout) helper.getView(R.id.item_private_chat_else_text_chat_mode_layout);//气泡模式布局


        ImageView elseIvModeImgSrc = (ImageView) helper.getView(R.id.item_private_chat_else_msg_mode_text_iv);
        ImageView meIvModeImgSrc = (ImageView) helper.getView(R.id.item_private_chat_me_msg_mode_text_iv);


        ImageView elseIvHeader = (ImageView) helper.getView(R.id.item_private_chat_else_header_iv);
        ImageView meIvHeader = (ImageView) helper.getView(R.id.item_private_chat_me_header_iv);

        TextView elseTvModeText = (TextView) helper.getView(R.id.item_private_chat_else_msg_mode_text_tv);
        TextView meTvModeText = (TextView) helper.getView(R.id.item_private_chat_me_msg_mode_text_tv);
        LinearLayout elseModeChatLayout = (LinearLayout) helper.getView(R.id.item_private_chat_else_msg_mode_chat_ll);
        LinearLayout meModeChatLayout = (LinearLayout) helper.getView(R.id.item_private_chat_me_msg_mode_chat_ll);

        TextView elseTvModeChatTitle = (TextView) helper.getView(R.id.item_private_chat_else_msg_mode_chat_title_tv);
        TextView elseTvModeChatContent = (TextView) helper.getView(R.id.item_private_chat_else_msg_mode_chat_content_tv);
        TextView elseTvModeChatTime = (TextView) helper.getView(R.id.item_private_chat_else_msg_mode_chat_time_tv);
        ImageView elseIvModeChatImg = (ImageView) helper.getView(R.id.item_private_chat_else_msg_mode_chat_img_iv);

        TextView meTvModeChatTitle = (TextView) helper.getView(R.id.item_private_chat_me_msg_mode_chat_title_tv);
        TextView meTvModeChatContent = (TextView) helper.getView(R.id.item_private_chat_me_msg_mode_chat_content_tv);
        TextView meTvModeChatTime = (TextView) helper.getView(R.id.item_private_chat_me_msg_mode_chat_time_tv);
        ImageView meIvModeChatImg = (ImageView) helper.getView(R.id.item_private_chat_me_msg_mode_chat_img_iv);

        TextView tvTime = (TextView) helper.getView(R.id.item_private_chat_title_time_tv);
//        helper.setText(R.id.item_private_chat_title_time_tv, item.getCreateTime());

        String fromId = item.getFromId();

        String msgType = item.getMsgType();

        String body = item.getBody();

        String showTime = item.getShowTime();

        if (TextUtils.isEmpty(showTime)) {
            tvTime.setVisibility(View.GONE);
        } else {
            tvTime.setVisibility(View.VISIBLE);
            if ("single_chat".equals(msgType)) {
                tvTime.setText(subTimeMin(item.getCreateTime()));
            } else {
                tvTime.setText(showTime);
            }
        }


        if (SpHelper.getInstance(mContext.getApplicationContext()).getString("id").equals(fromId)) {// TODO: 2020/4/16 自己发送的消息
            meLayout.setVisibility(View.VISIBLE);
            elseLayout.setVisibility(View.GONE);

            GlideApp.with(mContext)
                    .load(meAvatar)
                    .transform(mTransformation)
                    .into(meIvHeader);


            if ("image".equals(msgType)) {//图片
                meTcModeLayout.setVisibility(View.GONE);
                meIvModeImgSrc.setVisibility(View.VISIBLE);

                getMessageImage(meIvModeImgSrc, item);
            } else {// 气泡
                meIvModeImgSrc.setVisibility(View.GONE);
                meTcModeLayout.setVisibility(View.VISIBLE);

                if ("text".equals(msgType)) {//文本
                    meModeChatLayout.setVisibility(View.GONE);
                    meTvModeText.setVisibility(View.VISIBLE);

                    Gson gson = new Gson();
                    PrivateChatMsgModeBean.TextBean textBean = gson.fromJson(body, PrivateChatMsgModeBean.TextBean.class);
                    if (textBean != null) {
                        meTvModeText.setText(textBean.getText());
                    }
                } else if ("single_chat".equals(msgType)) {//chat
                    meTvModeText.setVisibility(View.GONE);
                    meModeChatLayout.setVisibility(View.VISIBLE);

                    Gson gson = new Gson();
                    PrivateChatMsgModeBean.ChatBean chatBean = gson.fromJson(body, PrivateChatMsgModeBean.ChatBean.class);
                    if (chatBean != null) {
                        PrivateChatMsgModeBean.ChatBean.ExtrasBean chatExtras = chatBean.getExtras();
                        String title = chatExtras.getTitle();
                        if (!TextUtils.isEmpty(title)) {
                            meTvModeChatTitle.setVisibility(View.VISIBLE);
                            meTvModeChatTitle.setText(title);
                        } else {
                            meTvModeChatTitle.setVisibility(View.GONE);
                        }
                        String content = chatExtras.getContent();
                        if (!TextUtils.isEmpty(content)) {
                            meTvModeChatContent.setVisibility(View.VISIBLE);
                            meTvModeChatContent.setText(content);
                        } else {
                            meTvModeChatContent.setVisibility(View.GONE);
                        }
                        String startTime = chatExtras.getStartTime();
                        String endTime = chatExtras.getEndTime();
                        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
                            meTvModeChatTime.setVisibility(View.VISIBLE);
                            meTvModeChatTime.setText(subTimeDate(startTime) + " ～ " + subTimeDate(endTime));
                        } else {
                            meTvModeChatTime.setVisibility(View.GONE);
                        }
                        meIvModeChatImg.setVisibility(View.GONE);

                    }
                }
            }
        } else {// TODO: 2020/4/16 别人发送的消息
            meLayout.setVisibility(View.GONE);
            elseLayout.setVisibility(View.VISIBLE);


            GlideApp.with(mContext)
                    .load(elseAvatar)
                    .transform(mTransformation)
                    .into(elseIvHeader);

            if ("image".equals(msgType)) {//图片
                elseTcModeLayout.setVisibility(View.GONE);
                elseIvModeImgSrc.setVisibility(View.VISIBLE);
                getMessageImage(elseIvModeImgSrc, item);
            } else {
                elseIvModeImgSrc.setVisibility(View.GONE);
                elseTcModeLayout.setVisibility(View.VISIBLE);

                if ("text".equals(msgType)) {//文本
                    elseModeChatLayout.setVisibility(View.GONE);
                    elseTvModeText.setVisibility(View.VISIBLE);

                    Gson gson = new Gson();
                    PrivateChatMsgModeBean.TextBean textBean = gson.fromJson(body, PrivateChatMsgModeBean.TextBean.class);
                    if (textBean != null) {
                        elseTvModeText.setText(textBean.getText());
                    }
                } else if ("single_chat".equals(msgType)) {//chat
                    elseTvModeText.setVisibility(View.GONE);
                    elseModeChatLayout.setVisibility(View.VISIBLE);

                    Gson gson = new Gson();
                    PrivateChatMsgModeBean.ChatBean chatBean = gson.fromJson(body, PrivateChatMsgModeBean.ChatBean.class);
                    if (chatBean != null) {
                        PrivateChatMsgModeBean.ChatBean.ExtrasBean chatExtras = chatBean.getExtras();
                        String title = chatExtras.getTitle();
                        if (!TextUtils.isEmpty(title)) {
                            elseTvModeChatTitle.setVisibility(View.VISIBLE);
                            elseTvModeChatTitle.setText(title);
                        } else {
                            elseTvModeChatTitle.setVisibility(View.GONE);
                        }
                        String content = chatExtras.getContent();
                        if (!TextUtils.isEmpty(content)) {
                            elseTvModeChatContent.setVisibility(View.VISIBLE);
                            elseTvModeChatContent.setText(content);
                        } else {
                            elseTvModeChatContent.setVisibility(View.GONE);
                        }
                        String startTime = chatExtras.getStartTime();
                        String endTime = chatExtras.getEndTime();
                        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
                            elseTvModeChatTime.setVisibility(View.VISIBLE);
                            elseTvModeChatTime.setText(subTimeDate(startTime) + " ～ " + subTimeDate(endTime));
                        } else {
                            elseTvModeChatTime.setVisibility(View.GONE);
                        }
                        elseIvModeChatImg.setVisibility(View.GONE);

                    }
                }
            }
        }
    }

    // 极光获取图片连接
    private void getMessageImage(final ImageView ivImage, final PrivateChatHistoryBean.MessageListBean item) {
        String body = item.getBody();
        Gson gson = new Gson();
        PrivateChatMsgModeBean.ImageBean imageBean = gson.fromJson(body, PrivateChatMsgModeBean.ImageBean.class);

        // TODO: 2020/4/17 实时接收的消息和加载列表消息的图片body不一样 getMedia_id为实时的 getMediaId为加载的
        String mediaId = imageBean.getMediaId();
        if (TextUtils.isEmpty(mediaId)) {
            mediaId = imageBean.getMedia_id();
        }

        // 网络加载过的图片就直接获取
        if (!TextUtils.isEmpty(item.getRealImgUrl())) {
            GlideApp.with(mContext)
                    .load(item.getRealImgUrl())
                    .placeholder(R.mipmap.default_img)
                    .error(R.mipmap.default_img)
                    .skipMemoryCache(true)//默认为false
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .centerCrop()
                    .into(ivImage);
            return;
        }

        compositeSubscription.add(dataManager.getImage(mediaId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String data) {
                        //真实图片地址加载出过后就直接设置值 之后就不需要网络加载了
                        item.setRealImgUrl(data);
                        GlideApp.with(mContext)
                                .load(data)
                                .placeholder(R.mipmap.default_img)
                                .error(R.mipmap.default_img)
                                .skipMemoryCache(true)//默认为false
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .dontAnimate()
                                .centerCrop()
                                .into(ivImage);
                    }
                }));
    }


    // 截取时间到日期
    private String subTimeDate(String time) {
        String subTime = time.substring(0, time.indexOf(" "));
        return subTime;
    }

    // 截取时间到分钟
    private String subTimeMin(String time) {
        String subTime = time.substring(0, time.lastIndexOf(":"));
        return subTime;
    }
}
