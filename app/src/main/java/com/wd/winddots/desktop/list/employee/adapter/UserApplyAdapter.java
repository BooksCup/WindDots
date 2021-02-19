package com.wd.winddots.desktop.list.employee.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.UserApply;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.LoadingDialog;
import com.wd.winddots.view.dialog.ConfirmDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;

/**
 * 用户申请
 *
 * @author zhou
 */
public class UserApplyAdapter extends BaseQuickAdapter<UserApply, BaseViewHolder> {

    private VolleyUtil mVolleyUtil;
    private LoadingDialog mDialog;
    private Context mContext;

    public UserApplyAdapter(Context context, int layoutResId, @Nullable List<UserApply> userApplyList) {
        super(layoutResId, userApplyList);
        mContext = context;
        mVolleyUtil = VolleyUtil.getInstance(mContext);
        mDialog = LoadingDialog.getInstance(mContext);
    }

    @Override
    protected void convert(BaseViewHolder helper, final UserApply userApply) {
        helper.setText(R.id.tv_name, userApply.getName()).setText(R.id.tv_phone, userApply.getPhone());

        TextView agreeTv = helper.getView(R.id.tv_agree);
        TextView refuseTv = helper.getView(R.id.tv_refuse);

        agreeTv.setOnClickListener(view -> {
            // 同意
            operateUserApply(userApply.getId(), Constant.OPERATE_STATUS_AGREE, helper.getLayoutPosition());
        });

        refuseTv.setOnClickListener(view -> {
            // 拒绝
            final ConfirmDialog mConfirmDialog = new ConfirmDialog(mContext, "拒绝申请",
                    "是否拒绝\"" + userApply.getName() + "\"",
                    "是", "否");
            mConfirmDialog.setOnDialogClickListener(new ConfirmDialog.OnDialogClickListener() {
                @Override
                public void onOkClick() {
                    operateUserApply(userApply.getId(), Constant.OPERATE_STATUS_REFUSE, helper.getLayoutPosition());
                }

                @Override
                public void onCancelClick() {
                    mConfirmDialog.dismiss();
                }
            });
            // 点击空白处消失
            mConfirmDialog.setCancelable(false);
            mConfirmDialog.show();
        });

    }

    private void operateUserApply(final String applyId, final String operateStatus, final int position) {
        mDialog.show();
        final String url = Constant.APP_BASE_URL + "userApply/" + applyId;
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("operateStatus", operateStatus);
        mVolleyUtil.httpPutRequest(url, paramMap, response -> {
            mDialog.dismiss();
            getData().remove(position);
            notifyDataSetChanged();
            if (null != userApplyListener) {
                userApplyListener.updateUserApplyNumber(getData().size());
            }
        }, volleyError -> {
            mVolleyUtil.handleCommonErrorResponse(mContext, volleyError);
        });
    }

    public interface UserApplyListener {
        void updateUserApplyNumber(int userApplyNumber);
    }

    public void setUserApplyListener(UserApplyListener userApplyListener) {
        this.userApplyListener = userApplyListener;
    }

    private UserApplyListener userApplyListener;
}