package com.wd.winddots.adapter.employee;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.winddots.R;
import com.wd.winddots.entity.User;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 员工
 *
 * @author zhou
 */
public class EmployeeAdapter extends BaseQuickAdapter<User, BaseViewHolder> {

    private String mKeyword;

    public void setKeyword(String keyword) {
        this.mKeyword = keyword;
    }

    public EmployeeAdapter(int layoutResId, @Nullable List<User> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
        SimpleDraweeView avatarSdv = helper.getView(R.id.sdv_avatar);
        if (!TextUtils.isEmpty(item.getAvatar())) {
            avatarSdv.setImageURI(Uri.parse(item.getAvatar()));
        } else {
            avatarSdv.setImageResource(R.mipmap.default_user_avatar);
        }

        String name = Utils.nullOrEmpty(item.getName());
        helper.setText(R.id.tv_name, name);
        helper.setText(R.id.tv_job_name, item.getJobName());

        TextView mDepartmentNameTv = helper.getView(R.id.tv_department_name);
        if (!TextUtils.isEmpty(item.getDepartmentName())) {
            mDepartmentNameTv.setVisibility(View.VISIBLE);
            mDepartmentNameTv.setText(item.getDepartmentName());
        } else {
            mDepartmentNameTv.setVisibility(View.GONE);
        }
    }
}
