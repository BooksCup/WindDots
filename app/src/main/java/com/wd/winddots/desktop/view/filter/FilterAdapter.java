package com.wd.winddots.desktop.view.filter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;

import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: FilterAdapter
 * Author: 郑
 * Date: 2020/7/2 2:15 PM
 * Description:
 */
public class FilterAdapter extends BaseQuickAdapter<FilterBean, BaseViewHolder> {

    private FilterAdapterOnItemDeleteListener listener;

    public void setFilterAdapterOnItemDeleteListener(FilterAdapterOnItemDeleteListener listener1){
        listener =listener1;
    }

    public FilterAdapter(int layoutResId, @Nullable List<FilterBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final FilterBean item) {

        helper.setIsRecyclable(false);

        helper.setText(R.id.tv_position,(helper.getAdapterPosition() + 1) + ".");
        final EditText editText = helper.getView(R.id.et_content);
        if (!StringUtils.isNullOrEmpty(item.getValue())){
            editText.setText(item.getValue());
        }else {
            editText.setText("");
        }
        List<FilterBean> data = this.getData();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                item.setValue(editText.getText().toString().trim());
                Log.e("input" + helper.getAdapterPosition() ,editText.getText().toString().trim());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ImageView deleteIv = helper.getView(R.id.iv_delete);
        deleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onDelete(item);
                }
            }
        });
    }


    public interface FilterAdapterOnItemDeleteListener{
        void onDelete(FilterBean item);
    }

}
