package com.wd.winddots.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wd.winddots.R;

import androidx.annotation.Nullable;

/**
 * FileName: TimeLineHeaderFooterView
 * Author: 郑
 * Date: 2020/5/5 5:40 PM
 * Description: 时间线顶部底部 view
 */
public class TimeLineHeaderFooterView extends LinearLayout {

    private TextView mNameText;

    public TimeLineHeaderFooterView(Context context) {
        super(context);
        initView();
    }

    public TimeLineHeaderFooterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TimeLineHeaderFooterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        View view= LayoutInflater.from(getContext()).inflate(R.layout.view_timeline_headerfooter, this, false);
        mNameText = view.findViewById(R.id.view_timeline_name);
        this.addView(view);
    }

    public void setmText(String text){
        mNameText.setText(text);
    }


}
