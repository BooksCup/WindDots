package com.wd.winddots.adapter.check.fabric;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.check.bean.CheckGoodsBean;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.RecyclerExpandBaseAdapter;
import com.wd.winddots.entity.FabricCheckLotInfo;
import com.wd.winddots.entity.FabricCheckTask;
import com.wd.winddots.utils.CommonUtil;
import com.wd.winddots.utils.Utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 面料盘点任务
 *
 * @author zhou
 */
public class FabricCheckTaskAdapter extends RecyclerExpandBaseAdapter<FabricCheckTask, FabricCheckLotInfo, RecyclerView.ViewHolder> {

    private OnSubItemClickListener onSubItemClickListener;

    private Context mContext;

    public FabricCheckTaskAdapter(Context context){
        this.mContext = context;
    }


    public void setOnSubItemClickListener(OnSubItemClickListener onSubItemClickListener) {
        this.onSubItemClickListener = onSubItemClickListener;
    }

    /**
     * 悬浮标题栏被点击的时候，展开收起切换功能
     */
    public void switchExpand(int adapterPosition) {
        int groupIndex = mIndexMap.get(adapterPosition).getGroupIndex();
        ExpandGroupItemEntity entity = mDataList.get(groupIndex);
        entity.setExpand(!entity.isExpand());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      /*  if (viewType == VIEW_TYPE_ITEM_TIME) {*/
            TitleItemHolder holder = new TitleItemHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fabric_check_task, parent, false));
            return holder;
/*        } else {
            SubItemHolder itemHolder = new SubItemHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fabric_check_lot_info, parent, false));

            return itemHolder;
        }*/
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      //  if (getItemViewType(position) == VIEW_TYPE_ITEM_TIME) {
            int groupIndex = mIndexMap.get(position).getGroupIndex();
            TitleItemHolder itemHolder = (TitleItemHolder) holder;
            itemHolder.itemView.setTag(mDataList.get(groupIndex));
            FabricCheckTask fabricCheckTask = mDataList.get(groupIndex).getParent();

            String goodsInfo = fabricCheckTask.getGoodsName() + "(" + fabricCheckTask.getGoodsNo() + ")";
            itemHolder.mGoodsInfoTv.setText(goodsInfo);
            itemHolder.mOrderNoTv.setText("#" + fabricCheckTask.getOrderNo());
            itemHolder.mThemeTv.setText(fabricCheckTask.getOrderTheme());

            String goodsPhoto = CommonUtil.getFirstPhotoFromJsonList(fabricCheckTask.getGoodsPhotos());
            if (!TextUtils.isEmpty(goodsPhoto)) {
                itemHolder.mGoodsPhotoSdv.setImageURI(Uri.parse(goodsPhoto));
            } else {
                itemHolder.mGoodsPhotoSdv.setImageResource(R.mipmap.icon_default_goods);
            }

        holder.itemView.setOnClickListener(v -> {
            if ((((TitleItemHolder) holder).llInfo).getVisibility() == View.VISIBLE){
                (((TitleItemHolder) holder).llInfo).setVisibility(View.GONE);
            }else {
                (((TitleItemHolder) holder).llInfo).setVisibility(View.VISIBLE);
                ((TitleItemHolder) holder).updateUI(position,fabricCheckTask.getFabricCheckLotInfoList());
            }

            ((TitleItemHolder) holder).llInfoHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<FabricCheckLotInfo> lotInfoList = ((TitleItemHolder) holder).checkInfoAdapter.getLotInfos();
                    FabricCheckLotInfo fabricCheckLotInfo = new FabricCheckLotInfo(true);
                    if (null == lotInfoList){
                        lotInfoList = new ArrayList<>();
                    }
                    lotInfoList.add(fabricCheckLotInfo);
                    fabricCheckTask.setFabricCheckLotInfoList(lotInfoList);
                    ((TitleItemHolder) holder).checkInfoAdapter.notifyDataSetChanged();
                }
            });

          /*  ExpandGroupItemEntity entity = (ExpandGroupItemEntity) v.getTag();
            entity.setExpand(!entity.isExpand());
            notifyDataSetChanged();*/
        });

/*        } else {
            SubItemHolder subHolder = (SubItemHolder) holder;
            int groupIndex = mIndexMap.get(position).getGroupIndex();
            int childIndex = mIndexMap.get(position).getChildIndex();
            final FabricCheckLotInfo subItem = mDataList.get(groupIndex).getChildList().get(childIndex);
            subHolder.itemView.setTag(subItem);
            if (childIndex == 0) {
                subHolder.mHeaderLl.setVisibility(View.VISIBLE);
                subHolder.mBodyLl.setVisibility(View.GONE);
                subHolder.mEditLl.setVisibility(View.GONE);
            } else {
                subHolder.mHeaderLl.setVisibility(View.GONE);
                if (subItem.isEdit()) {
                    subHolder.mBodyLl.setVisibility(View.GONE);
                    subHolder.mEditLl.setVisibility(View.VISIBLE);
                } else {
                    subHolder.mBodyLl.setVisibility(View.VISIBLE);
                    subHolder.mEditLl.setVisibility(View.GONE);
                }
            }
            subHolder.mLotNoTv.setText(Utils.nullOrEmpty(subItem.getLotNo()));
            subHolder.mLengthTv.setText(Utils.nullOrEmpty(subItem.getLength()));
            subHolder.mWeightTv.setText(Utils.nullOrEmpty(subItem.getWeight()));
//            subHolder.body.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onSubItemClickListener.onItemClick(subItem);
//                }
//            });

            subHolder.mAddIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDataList.get(groupIndex).getChildList().add(new FabricCheckLotInfo(true));
                    notifyDataSetChanged();
                }
            });
        }*/

    }

     class TitleItemHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mGoodsPhotoSdv;
        TextView mGoodsInfoTv;
        TextView mRelatedCompanyTv;
        TextView mOrderNoTv;
        TextView mThemeTv;
        LinearLayout llInfo;
        RecyclerView infoRecycler;
        LinearLayout llInfoHeader;

        CheckInfoAdapter checkInfoAdapter;

        TitleItemHolder(View itemView) {
            super(itemView);
            mGoodsPhotoSdv = itemView.findViewById(R.id.sdv_goods_photo);
            mGoodsInfoTv = itemView.findViewById(R.id.tv_goods_info);
            mRelatedCompanyTv = itemView.findViewById(R.id.tv_related_company);
            mOrderNoTv = itemView.findViewById(R.id.tv_order_no);
            mThemeTv = itemView.findViewById(R.id.tv_theme);
            llInfo = itemView.findViewById(R.id.ll_info);
            infoRecycler = itemView.findViewById(R.id.body_recycler);

            checkInfoAdapter = new CheckInfoAdapter();
            LinearLayoutManager layoutManager = new  LinearLayoutManager(mContext);
            infoRecycler.setLayoutManager(layoutManager);
            infoRecycler.setAdapter(checkInfoAdapter);

            llInfoHeader = itemView.findViewById(R.id.ll_header);
        }

        private void  updateUI(int position, List<FabricCheckLotInfo> lotInfoList){
            checkInfoAdapter.setLotInfos(lotInfoList);
        }

    }

    static class SubItemHolder extends RecyclerView.ViewHolder {

        LinearLayout mHeaderLl;
        LinearLayout mBodyLl;
        LinearLayout mEditLl;
        TextView mLotNoTv;
        TextView mNumTv;
        TextView mWeightTv;
        TextView mLengthTv;
        ImageView mAddIv;

        SubItemHolder(View itemView) {
            super(itemView);
            mHeaderLl = itemView.findViewById(R.id.ll_header);
            mBodyLl = itemView.findViewById(R.id.ll_body);
            mEditLl = itemView.findViewById(R.id.ll_edit);
            mLotNoTv = itemView.findViewById(R.id.tv_lot_no);
            mNumTv = itemView.findViewById(R.id.tv_num);
            mWeightTv = itemView.findViewById(R.id.tv_weight);
            mLengthTv = itemView.findViewById(R.id.tv_length);
            mAddIv = itemView.findViewById(R.id.iv_add);
        }
    }

    public interface OnSubItemClickListener {
        void onItemClick(CheckGoodsBean.CheckGang gang);
    }


}
