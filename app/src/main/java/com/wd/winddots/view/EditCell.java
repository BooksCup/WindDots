package com.wd.winddots.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wd.winddots.R;

import androidx.annotation.Nullable;

/**
 * FileName: EditeCell
 * Author: 郑
 * Date: 2020/6/12 11:21 AM
 * Description:
 */
public class EditCell extends LinearLayout {

    private TextView mTitleTv;
    public EditText mContentEt;


    public EditCell(Context context) {
        super(context);
        initView();
    }

    public EditCell(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        setAttr(attrs);
    }

    public EditCell(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setAttr(attrs);
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_edit_cell, this, false);
        mTitleTv = view.findViewById(R.id.tv_title);
        mContentEt = view.findViewById(R.id.et_content);
        this.addView(view);
    }

    private void setAttr(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        String title = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "edite_cell_title");
        String hint = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "edite_cell_hint");
        mTitleTv.setText(title);
        mContentEt.setHint(hint);
    }

    public void setTitle(String title) {
        mTitleTv.setText(title);
    }

    public void setHint(String hint) {
        mContentEt.setHint(hint);
    }

    public void setText(String text) {
        mContentEt.setText(text);
    }

    public String getText() {
        return mContentEt.getText().toString().trim();
    }

    public void setInputType(int inputType) {
        mContentEt.setInputType(inputType);
    }

}
