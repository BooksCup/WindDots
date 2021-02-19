package com.wd.winddots.components.adapter;

import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.winddots.R;
import com.wd.winddots.entity.User;
import com.wd.winddots.utils.TextHighLight;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: UserSelectAdapter
 * Author: 郑
 * Date: 2020/5/6 4:06 PM
 * Description:
 */
public class UserSelectAdapter extends BaseQuickAdapter<User, BaseViewHolder> {

    private String mKeyword;

    public UserSelectAdapter(int layoutResId, @Nullable List<User> data) {
        super(layoutResId, data);
    }

    public void setKeyword(String keyword) {
        this.mKeyword = keyword;
    }

    @Override
    protected void convert(BaseViewHolder helper, final User user) {

        String[] keywords = new String[mKeyword.length()];
        for (int i = 0; i < mKeyword.length(); i++) {
            keywords[i] = String.valueOf(mKeyword.charAt(i));
        }

        if (!TextUtils.isEmpty(user.getName())) {
            SpannableStringBuilder nameBuilder = TextHighLight.
                    matcherSearchContent(mContext, user.getName(), keywords);
            helper.setText(R.id.tv_name, nameBuilder);
        } else {
            helper.setText(R.id.tv_name, user.getName());
        }

        helper.setText(R.id.tv_job_name, user.getJobName());

        TextView departmentNameTv = helper.getView(R.id.tv_department_name);
        if (TextUtils.isEmpty(user.getDepartmentName())) {
            departmentNameTv.setVisibility(View.INVISIBLE);
        } else {
            departmentNameTv.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_department_name, user.getDepartmentName());
        }

        ImageView imageView = helper.getView(R.id.iv_selected_icon);
        if (user.isDisable()) {
            imageView.setImageResource(R.mipmap.selectgray);
        } else if (user.isSelect()) {
            imageView.setImageResource(R.mipmap.select);
        } else {
            imageView.setImageResource(R.mipmap.unselect);
        }

        SimpleDraweeView mAvatarSdv = helper.getView(R.id.sdv_avatar);
        if (!TextUtils.isEmpty(user.getAvatar())) {
            mAvatarSdv.setImageURI(Uri.parse(user.getAvatar()));
        }
    }
}
