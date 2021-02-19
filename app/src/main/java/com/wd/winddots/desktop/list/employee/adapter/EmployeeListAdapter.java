package com.wd.winddots.desktop.list.employee.adapter;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.employee.bean.EmployeeListBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: EmployeeListAdapter
 * Author: 郑
 * Date: 2020/11/10 9:52 AM
 * Description:
 */
public class EmployeeListAdapter extends BaseQuickAdapter<EmployeeListBean.EmployeeItem, BaseViewHolder> {
    public EmployeeListAdapter(int layoutResId, @Nullable List<EmployeeListBean.EmployeeItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EmployeeListBean.EmployeeItem item) {
        SimpleDraweeView avatarSdv = helper.getView(R.id.sdv_avatar);
        ImageView gender = helper.getView(R.id.iv_gender);
//        GlideApp.with(mContext).load(item.getAvatar() + Utils.OSSImageSize(200)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(icon);
//        if (2 == item.getGender()){
//            gender.setImageResource(R.mipmap.woman);
//        }else {
//            gender.setImageResource(R.mipmap.men);
//        }
        if (!TextUtils.isEmpty(item.getAvatar())) {
            avatarSdv.setImageURI(Uri.parse(item.getAvatar()));
        }

        String name = Utils.nullOrEmpty(item.getName());
//        String department = mContext.getString(R.string.employee_department) + Utils.nullOrEmpty(item.getDepartmentName());
//        String position = mContext.getString(R.string.employee_position) + Utils.nullOrEmpty(item.getJobName());
//        String jobNo = mContext.getString(R.string.employee_job_no) + Utils.nullOrEmpty(item.getJobNo());
//        helper.setText(R.id.tv_name,name).setText(R.id.tv_department,department).setText(R.id.tv_position,position).setText(R.id.tv_jobnumber,jobNo);
        helper.setText(R.id.tv_name, name);
        helper.setText(R.id.tv_job_name, item.getJobName());
    }
}
