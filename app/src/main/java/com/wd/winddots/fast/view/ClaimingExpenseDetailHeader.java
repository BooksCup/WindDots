package com.wd.winddots.fast.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;

import com.wd.winddots.R;
import com.wd.winddots.view.TitleTextView;

import androidx.annotation.Nullable;

/**
 * FileName: ClaimingExpenseDetailHeader
 * Author: 郑
 * Date: 2020/5/20 10:03 AM
 * Description: 费用明细顶部
 */
public class ClaimingExpenseDetailHeader extends LinearLayout {


    private TitleTextView mAmountCell;
    private TitleTextView mDepartmentCell;
    private TitleTextView mRemarkCell;


    public ClaimingExpenseDetailHeader(Context context) {
        super(context);
        initView();
    }

    public ClaimingExpenseDetailHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ClaimingExpenseDetailHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_claiming_expense_detail_header, this, false);
        mAmountCell = view.findViewById(R.id.view_claiming_expense_detail_header_amount);
        mDepartmentCell = view.findViewById(R.id.view_claiming_expense_detail_header_department);
        mRemarkCell = view.findViewById(R.id.view_claiming_expense_detail_header_remark);
        mAmountCell.setTitle("金额");
        mDepartmentCell.setTitle("部门");
        mRemarkCell.setTitle("备注");
        this.addView(view);
    }

    public void setAmount(String amount){
        mAmountCell.setContent(amount);
    }

    public void setDepartment(String name){
        mDepartmentCell.setContent(name);
    }

    public void setRemark(String remark){
        mRemarkCell.setContent(remark);
    }

}
