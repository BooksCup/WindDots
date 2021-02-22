package com.wd.winddots.adapter.check.fabric;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

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

        }else {
            holder.llBody.setVisibility(View.VISIBLE);
            holder.llEdit.setVisibility(View.GONE);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
