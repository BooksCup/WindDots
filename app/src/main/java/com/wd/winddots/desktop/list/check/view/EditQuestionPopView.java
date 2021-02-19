package com.wd.winddots.desktop.list.check.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wd.winddots.R;

import androidx.annotation.Nullable;

/**
 * FileName: EditQuestionPopView
 * Author: 郑
 * Date: 2021/1/26 9:38 AM
 * Description: 编辑问题弹窗
 */
public class EditQuestionPopView extends RelativeLayout {

    private EditText mQuestionEt;
    private EditQuestionPopViewOnCommitDidClickListener listener;

    public void setEditQuestionPopViewOnCommitDidClickListener(EditQuestionPopViewOnCommitDidClickListener listener1){
        listener = listener1;
    }

    public EditQuestionPopView(Context context) {
        super(context);
        initView();
    }

    public EditQuestionPopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public EditQuestionPopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_add_check_question, this, false);
        mQuestionEt = view.findViewById(R.id.et_question);
        TextView commitBtn = view.findViewById(R.id.btn_commit);
        commitBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCommitBtnDidClick(mQuestionEt.getText().toString().trim());
            }
        });
        this.addView(view);
    }


    public String getText(){
        return mQuestionEt.getText().toString().trim();
    }


    public interface EditQuestionPopViewOnCommitDidClickListener{
        void onCommitBtnDidClick(String questionContent);
    }

}
