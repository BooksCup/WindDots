package com.wd.winddots.adapter.check.fabric;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wd.winddots.R;
import com.wd.winddots.entity.FabricCheckLotInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckInfoAdapter  extends RecyclerView.Adapter<CheckInfoAdapter.ViewHolder>{

    private List<FabricCheckLotInfo> lotInfos;

    public void setLotInfos(List<FabricCheckLotInfo> lotInfos) {
        this.lotInfos = lotInfos;
        notifyDataSetChanged();
    }

    public List<FabricCheckLotInfo> getLotInfos() {
        return lotInfos;
    }

    public void addInfo(FabricCheckLotInfo lotInfo){
        if (null != lotInfos){
            lotInfos.add(lotInfo);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check_info, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        FabricCheckLotInfo fabricCheckLotInfo = lotInfos.get(position);
        if (fabricCheckLotInfo.isEdit()){
            holder.llBody.setVisibility(View.GONE);
            holder.llEdit.setVisibility(View.VISIBLE);
            holder.etGh.setText(fabricCheckLotInfo.getLotNo());
            holder.etJs.setText(fabricCheckLotInfo.getNum());
            holder.etZl.setText(fabricCheckLotInfo.getWeight());
            holder.etSl.setText(fabricCheckLotInfo.getLength());
            holder.etGh.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    fabricCheckLotInfo.setLotNo(s.toString());
                    lotInfos.set(position,fabricCheckLotInfo);
                }
            });

            holder.etJs.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    fabricCheckLotInfo.setNum(s.toString());
                    lotInfos.set(position,fabricCheckLotInfo);
                }
            });

            holder.etSl.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    fabricCheckLotInfo.setLength(s.toString());
                    lotInfos.set(position,fabricCheckLotInfo);
                }
            });

            holder.etZl.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    fabricCheckLotInfo.setWeight(s.toString());
                    lotInfos.set(position,fabricCheckLotInfo);
                }
            });
            holder.ivSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fabricCheckLotInfo.setEdit(false);
                    notifyDataSetChanged();
                }
            });
        }else {
            holder.llBody.setVisibility(View.VISIBLE);
            holder.llEdit.setVisibility(View.GONE);

            holder.tvLotNo.setText(fabricCheckLotInfo.getLotNo());
            holder.tvNum.setText(fabricCheckLotInfo.getNum());
            holder.tvWeight.setText(fabricCheckLotInfo.getWeight());
            holder.tvLength.setText(fabricCheckLotInfo.getLength());
        }
    }

    @Override
    public int getItemCount() {
        return lotInfos!= null?lotInfos.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ll_body)
        LinearLayout llBody;


        @BindView(R.id.ll_edit)
        LinearLayout llEdit;

        @BindView(R.id.et_gh)
        EditText etGh;

        @BindView(R.id.et_js)
        EditText etJs;

        @BindView(R.id.et_zl)
        EditText etZl;

        @BindView(R.id.et_sl)
        EditText etSl;

        @BindView(R.id.iv_save)
        ImageView ivSave;

        @BindView(R.id.tv_lot_no)
        TextView tvLotNo;

        @BindView(R.id.tv_num)
        TextView tvNum;

        @BindView(R.id.tv_weight)
        TextView tvWeight;

        @BindView(R.id.tv_length)
        TextView tvLength;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
