package com.wd.winddots.desktop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wd.winddots.R;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: CheckFilter
 * Author: 郑
 * Date: 2021/2/1 2:03 PM
 * Description: 盘点筛选
 */
public class CheckFilter extends LinearLayout {


    private EditText mGoodsNameEt;
    private EditText mGoodsNoEt;
    private EditText mTagEt;
    private EditText mCreatorEt;


    public CheckFilterOnCommitClickListener onCommitClickListener;

    public void setOnCommitClickListener(CheckFilterOnCommitClickListener onCommitClickListener) {
        this.onCommitClickListener = onCommitClickListener;
    }

    public CheckFilter(Context context) {
        super(context);
        initView();
    }

    public CheckFilter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CheckFilter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_check_filter, this, false);
        this.addView(view);
        mGoodsNameEt = view.findViewById(R.id.et_goods_name);
        mGoodsNoEt = view.findViewById(R.id.et_goods_no);
        mTagEt = view.findViewById(R.id.et_tag);
        mCreatorEt = view.findViewById(R.id.et_creator);
        TextView resetBtn = view.findViewById(R.id.tv_reset);
        TextView confirmBtn = view.findViewById(R.id.tv_commit);
        resetBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoodsNameEt.setText("");
                mGoodsNoEt.setText("");
                mTagEt.setText("");
                mCreatorEt.setText("");
            }
        });
        confirmBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCommitClickListener != null){

                    String goodsName = mGoodsNameEt.getText().toString().trim();
                    String goodsNo = mGoodsNoEt.getText().toString().trim();
                    String tag = mTagEt.getText().toString().trim();

                    String creator = mCreatorEt.getText().toString().trim();

                    Map<String,String> params = new HashMap<>();
                    if (!StringUtils.isNullOrEmpty(goodsName)){
                        params.put("productName",goodsName);
                    }
                    if (!StringUtils.isNullOrEmpty(goodsNo)){
                       // params.put("goodsName",goodsNo);
                    }
                    if (!StringUtils.isNullOrEmpty(tag)){
                        params.put("cylinderNumber",tag);
                    }

                    if (!StringUtils.isNullOrEmpty(creator)){
                        params.put("supplierName",creator);
                    }
                    onCommitClickListener.onCommitBtnDidClick(params);
                }
            }
        });
    }



    public interface CheckFilterOnCommitClickListener{
        void onCommitBtnDidClick(Map<String,String> data);
    }

}
