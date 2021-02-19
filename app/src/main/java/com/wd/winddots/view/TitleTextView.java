package com.wd.winddots.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wd.winddots.R;

import androidx.annotation.Nullable;

/**
 * FileName: TitleTextView
 * Author: 郑
 * Date: 2020/5/19 4:21 PM
 * Description:
 */
public class TitleTextView extends LinearLayout {

    private TextView mTitleTextView;
    private TextView mContextTextView;
    private ImageView mRightIcon;


    public TitleTextView(Context context) {
        super(context);
        initView();
    }

    public TitleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        setAttr(attrs);
    }

    public TitleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setAttr(attrs);
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_title_text_cell, this, false);
        mTitleTextView = view.findViewById(R.id.view_title_text_title);
        mContextTextView = view.findViewById(R.id.view_title_text_content);
        mRightIcon = view.findViewById(R.id.view_title_text_icon_right);
        this.addView(view);
    }

    private void setAttr(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        int count = attrs.getAttributeCount();
        for (int i = 0; i < count; i++) {
            switch (attrs.getAttributeName(i)) {
                case "view_title":
                    mTitleTextView.setText(attrs.getAttributeValue(i));
                    break;
                case "view_content":
                    mContextTextView.setText(attrs.getAttributeValue(i));
                    break;
            }
        }

    }


    public void setTitle(String title) {
        mTitleTextView.setText(title);
    }

    public void setContent(String content) {
        mContextTextView.setText(content);
    }

    public void setRightIconVisibility(int visibility) {
        mRightIcon.setVisibility(visibility);
    }



}
