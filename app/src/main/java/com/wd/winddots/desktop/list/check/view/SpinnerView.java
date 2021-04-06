package com.wd.winddots.desktop.list.check.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.wd.winddots.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * FileName: SpinnerView
 * Author: 郑
 * Date: 2021/2/26 1:51 PM
 * Description:
 */
public class SpinnerView extends LinearLayout {

    private Spinner mSpinner;

    private Context mContext;

    private SpinnerBean mSelect;

    private int mPosition = 0;

    private List<SpinnerBean> mDataSource = new ArrayList<>();

    private OnselectListener listener;

    private int textColor = -1;

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public SpinnerView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public SpinnerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public SpinnerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_spinner, this, false);
        mSpinner = view.findViewById(R.id.view_selector);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelect = mDataSource.get(i);
                mPosition = i;
                if (listener != null){
                    listener.onselect(i, SpinnerView.this);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        this.addView(view);
    }


    public void setSelectList(@NonNull final List<SpinnerBean> list) {
        mDataSource = list;
        SelectorAdapter adapter = new SelectorAdapter();
        adapter.textColor = textColor;
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(0);
    }


    private class SelectorAdapter extends BaseAdapter {

        int textColor = -1;

        @Override
        public int getCount() {
            return mDataSource.size();
        }

        @Override
        public Object getItem(int i) {
            return mDataSource.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_selector, null);
            if (view != null) {
                TextView _TextView1 = view.findViewById(R.id.item_selector_text);
                if (textColor != -1){
                    _TextView1.setTextColor(textColor);
                }
                _TextView1.setText(mDataSource.get(i).getName());
            }
            return view;
        }
    }


    public void setDefaultPosition(int position) {
        mPosition = position;
        mSpinner.setSelection(position);
    }

    public int getPosition(){
        return mPosition;
    }

    public SpinnerBean getItem() {
        return mDataSource.get(mPosition);
    }



    public void setOnselectListener(OnselectListener listener1){
        listener = listener1;
    }

    public interface OnselectListener{
        void onselect(int position, SpinnerView view);
    }


    public static class SpinnerBean{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
