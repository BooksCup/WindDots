package com.wd.winddots.fast.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wd.winddots.R;

import androidx.annotation.Nullable;

/**
 * FileName: MineClaimingAddDetailFooterView
 * Author: 郑
 * Date: 2020/5/7 12:25 PM
 * Description: 添加报销费用明细底部 view
 */
public class MineClaimingAddDetailFooterView extends LinearLayout {

    private LinearLayout mAddIcon;
    private ClaimingAddDetailFooterAddIconViewListener listener;
    private EditText mRemarkEditText;

    public MineClaimingAddDetailFooterView(Context context) {
        super(context);
        initView();
    }

    public MineClaimingAddDetailFooterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MineClaimingAddDetailFooterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        View view= LayoutInflater.from(getContext()).inflate(R.layout.view_claimingadd_detail_footer, this, false);
        mRemarkEditText  = view.findViewById(R.id.view_claimingadd_footertext);
        mAddIcon = view.findViewById(R.id.view_claimingadd_addicon);
        mAddIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.addIconDidClick();
                }
            }
        });
        this.addView(view);
    }

    public void setOnAddIconClickListener(ClaimingAddDetailFooterAddIconViewListener listener1){
        listener = listener1;
    }

    public String getText() {
        return mRemarkEditText.getText().toString().trim();
    }

    public void setText(String text){
        mRemarkEditText.setText(text);
    }


    public interface ClaimingAddDetailFooterAddIconViewListener {
     void addIconDidClick();
    }

}
