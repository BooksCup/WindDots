package com.wd.winddots.desktop.list.invoice.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wd.winddots.R;

import androidx.annotation.Nullable;

/**
 * FileName: InvoiceDetailCell
 * Author: 郑
 * Date: 2020/7/1 3:37 PM
 * Description: 发票详情单元格
 */
public class InvoiceDetailCell extends LinearLayout {

    private TextView mTitleTextView;
    private TextView mContentextView;

    public InvoiceDetailCell(Context context) {
        super(context);
        initView();
    }

    public InvoiceDetailCell(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public InvoiceDetailCell(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_invoice_detail_cell, this, false);
        mTitleTextView  = view.findViewById(R.id.tv_title);
        mContentextView  = view.findViewById(R.id.tv_content);
        this.addView(view);
    }

    public void setTitle(String title){
        mTitleTextView.setText(title);
    }

    public void setConten(String conten){
        mContentextView.setText(conten);
    }

}
