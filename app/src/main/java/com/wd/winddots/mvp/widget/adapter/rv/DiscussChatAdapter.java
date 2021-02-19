package com.wd.winddots.mvp.widget.adapter.rv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.bean.resp.DiscussChatBean;
import com.wd.winddots.components.users.UserInfoActivity;
import com.wd.winddots.utils.CommonUtil;
import com.wd.winddots.utils.Logg;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.glide.CornerTransform;

import java.util.ArrayList;
import java.util.List;

public class DiscussChatAdapter extends BaseQuickAdapter<DiscussChatBean.DataBean.CommentsBean, BaseViewHolder> {
    private final CornerTransform mTransformation;

    public Activity mActivity;

    public DiscussChatAdapter(Context context, int layoutResId, @Nullable List<DiscussChatBean.DataBean.CommentsBean> data) {
        super(layoutResId, data);
        mTransformation = new CornerTransform(context, CommonUtil.dip2px(context, 5));
        //只是绘制左上角和右上角圆角
        mTransformation.setExceptCorner(false, false, false, false);
    }

    @Override
    protected void convert(BaseViewHolder helper, final DiscussChatBean.DataBean.CommentsBean item) {

        Logg.d("CommentsBean: " + item.getContent());
        RelativeLayout meLayout = (RelativeLayout) helper.getView(R.id.item_discuss_chat_me_layout);
        RelativeLayout elseLayout = (RelativeLayout) helper.getView(R.id.item_discuss_chat_else_layout);

        ImageView meIvHeader = (ImageView) helper.getView(R.id.item_discuss_chat_me_header_iv);
        ImageView elseIvHeader = (ImageView) helper.getView(R.id.item_discuss_chat_else_header_iv);

        TextView meTvName = (TextView) helper.getView(R.id.item_discuss_chat_me_name_tv);
        TextView elseTvName = (TextView) helper.getView(R.id.item_discuss_chat_else_name_tv);

        TextView meTvContent = (TextView) helper.getView(R.id.item_discuss_chat_me_content_tv);
        TextView elseTvContent = (TextView) helper.getView(R.id.item_discuss_chat_else_content_tv);

        ImageView meIvImg = (ImageView) helper.getView(R.id.item_discuss_chat_me_content_img_iv);
        ImageView elseIvImg = (ImageView) helper.getView(R.id.item_discuss_chat_else_content_img_iv);

        TextView tvTime = (TextView) helper.getView(R.id.item_discuss_chat_title_time_tv);


        meIvHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UserInfoActivity.class);
                intent.putExtra("id", SpHelper.getInstance(mContext).getUserId());
                mActivity.startActivity(intent);
            }
        });

        elseIvHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UserInfoActivity.class);
                intent.putExtra("id", item.getUserId());
                mActivity.startActivity(intent);
            }
        });



        String userAvatar = item.getUserAvatar();
        String content = item.getContent();
        String relativePhotosJson = item.getRelativePhotos();
        String userName = item.getUserName();
        String createTime = item.getCreateTime();

        String image = "";
        if (!StringUtils.isNullOrEmpty(relativePhotosJson) && relativePhotosJson.length() > 10){
            List<String> relativePhotos = null;
            try {
                Gson gson = new Gson();
                relativePhotos = gson.fromJson(relativePhotosJson, new TypeToken<List<String>>() {
                                            }.getType());
            }catch (Exception e){
                relativePhotos = new ArrayList<>();
            }

            if (relativePhotos.size() > 0){
                image = relativePhotos.get(0);
            }
        }

       if (!StringUtils.isNullOrEmpty(createTime)){
           tvTime.setVisibility(View.VISIBLE);
           tvTime.setText(CommonUtil.subTime2Min(createTime));
       }else {
           tvTime.setVisibility(View.GONE);
       }


        if (SpHelper.getInstance(mContext).getUserId().equals(item.getUserId())) {// TODO: 2020/4/22 me
            elseLayout.setVisibility(View.GONE);
            meLayout.setVisibility(View.VISIBLE);

            GlideApp.with(mContext)
                    .load(userAvatar)
                    .transform(mTransformation)
                    .into(meIvHeader);

            meTvContent.setText(content);

            if (TextUtils.isEmpty(image)) {
                meIvImg.setVisibility(View.GONE);
            } else {
                meIvImg.setVisibility(View.VISIBLE);
                GlideApp.with(mContext)
                        .load(image)
                        .into(meIvImg);
            }
        } else {// TODO: 2020/4/22 else
            meLayout.setVisibility(View.GONE);
            elseLayout.setVisibility(View.VISIBLE);

            GlideApp.with(mContext)
                    .load(userAvatar)
                    .transform(mTransformation)
                    .into(elseIvHeader);

            elseTvName.setText(userName);

            elseTvContent.setText(content);

            if (TextUtils.isEmpty(image)) {
                elseIvImg.setVisibility(View.GONE);
            } else {
                elseIvImg.setVisibility(View.VISIBLE);
                GlideApp.with(mContext)
                        .load(image)
                        .into(elseIvImg);
            }
        }
    }
}
