package com.wd.winddots.view.selector;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wd.winddots.R;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: Selector
 * Author: 郑
 * Date: 2020/5/7 10:45 AM
 * Description: 选择器控件
 */
public class Selector extends LinearLayout implements SelectView.SelectViewOnselectListener {


    private SelectView mSelectView;
    private TextView mTitleText;
    private View mPlaceholderView;
    private SelectorOnselectListener listener;
    private int mPosition = 0;


    public void selectorToLeft(boolean toLeft){
        if (toLeft){
            mPlaceholderView.setVisibility(View.GONE);
        }else {
            mPlaceholderView.setVisibility(View.VISIBLE);
        }
    }

    public Selector(Context context) {
        super(context);
        initView();
    }

    public Selector(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public Selector(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_selector, this, false);
        mSelectView = view.findViewById(R.id.view_selector_select);
        mTitleText = view.findViewById(R.id.view_selector_text);
        mPlaceholderView = view.findViewById(R.id.placeholder);
        mSelectView.setSelectViewOnselectListener(this);
        this.addView(view);
    }

    public void setTitleText(String s){
        mTitleText.setText(s);
    }


    public void setSelectList(List<SelectBean> list){
        mSelectView.setSelectList(list);
    }


    @Override
    public void onselect(int position ,SelectView view) {
        mPosition = position;
        if (listener != null){
            listener.onselect(position,Selector.this);
        }
    }


    public int getPosition(){
            return mPosition;
    }

    public SelectBean getItem(){
        return mSelectView.getItem();
    }

    public void setDefaultPosition(int position) {
        mPosition = position;
        mSelectView.setDefaultPosition(position);
    }


    public void setSelectorOnselectListener(SelectorOnselectListener listener1){
        listener = listener1;
    }

    public interface SelectorOnselectListener{
        void onselect(int position, Selector selector);
    }


}
