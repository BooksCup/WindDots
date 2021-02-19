package com.wd.winddots.view.selector;

import android.content.Context;
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
 * FileName: SelectView
 * Author: 郑
 * Date: 2020/5/6 11:50 AM
 * Description: 选择器
 */
public class SelectView extends LinearLayout {

    private Spinner mSpinner;

    private Context mContext;

    private SelectBean mSelect;

    private int mPosition = 0;

    private List<SelectBean> mDataSource = new ArrayList<>();

    private SelectViewOnselectListener listener;


    public SelectView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public SelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public SelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_select, this, false);
        mSpinner = view.findViewById(R.id.view_selector);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelect = mDataSource.get(i);
                mPosition = i;
                if (listener != null){
                    listener.onselect(i,SelectView.this);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        this.addView(view);
    }


    public void setSelectList(@NonNull final List<SelectBean> list) {
        mDataSource = list;
        SelectorAdapter adapter = new SelectorAdapter();
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(0);


    }


    private class SelectorAdapter extends BaseAdapter {


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

    public SelectBean getItem() {
        return mDataSource.get(mPosition);
    }



    public void setSelectViewOnselectListener(SelectViewOnselectListener listener1){
            listener = listener1;
    }

    public interface SelectViewOnselectListener{
        void onselect(int position,SelectView view);
    }

}
