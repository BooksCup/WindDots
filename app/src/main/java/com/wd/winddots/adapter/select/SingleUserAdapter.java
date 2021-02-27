package com.wd.winddots.adapter.select;


import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.winddots.R;
import com.wd.winddots.entity.User;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 用户
 *
 * @author zhou
 */
public class SingleUserAdapter extends BaseQuickAdapter<User, BaseViewHolder> {

    private String mKeyword;

    public void setKeyword(String keyword) {
        this.mKeyword = keyword;
    }

    public SingleUserAdapter(int layoutResId, @Nullable List<User> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_job_name, item.getJobName());

        SimpleDraweeView mAvatarSdv = helper.getView(R.id.sdv_avatar);
        if (!TextUtils.isEmpty(item.getAvatar())) {
            mAvatarSdv.setImageURI(Uri.parse(item.getAvatar()) + "?x-oss-process=image/resize,limit_0,m_fill,w_100,h_100/quality,q_100");
        } else {
            mAvatarSdv.setImageResource(R.mipmap.default_user_avatar);
        }
    }
}
