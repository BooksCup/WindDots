package com.wd.winddots.adapter.stock.in;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.wd.winddots.R;
import com.wd.winddots.entity.GoodsSpec;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsSpecAdapter extends RecyclerView.Adapter<GoodsSpecAdapter.ViewHolder> {

    private Context mContext;
    private List<GoodsSpec> mGoodsSpecList;
    public TextChangeListener mTextChangeListener;

    public void setTextChangeListener(TextChangeListener textChangeListener) {
        this.mTextChangeListener = textChangeListener;
    }

    public GoodsSpecAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<GoodsSpec> goodsSpecList) {
        this.mGoodsSpecList = goodsSpecList;
        notifyDataSetChanged();
    }

    public List<GoodsSpec> getList() {
        return this.mGoodsSpecList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_spec, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodsSpec goodsSpec = mGoodsSpecList.get(position);
        if (TextUtils.isEmpty(goodsSpec.getY())) {
            holder.mYTv.setVisibility(View.GONE);
            holder.mXTv.setText(goodsSpec.getX());
        } else {
            holder.mXTv.setText(goodsSpec.getX());
            holder.mYTv.setText(goodsSpec.getY());
        }

        holder.mNumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                goodsSpec.setNum(holder.mNumEt.getText().toString().trim());
                if (null != mTextChangeListener) {
                    mTextChangeListener.stockInNumChange();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mGoodsSpecList != null ? mGoodsSpecList.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_x)
        TextView mXTv;

        @BindView(R.id.tv_y)
        TextView mYTv;

        @BindView(R.id.et_num)
        EditText mNumEt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface TextChangeListener {
        void stockInNumChange();
    }
}
