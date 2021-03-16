package com.wd.winddots.adapter.check.fabric;


import android.text.TextUtils;
import android.widget.ImageView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.entity.FabricCheckTask;
import com.wd.winddots.utils.CommonUtil;
import com.wd.winddots.utils.Utils;


import androidx.annotation.Nullable;


import java.util.List;

/**
 * 面料盘点任务
 *
 * @author zhou
 */
public class FabricCheckTaskAdapter extends BaseQuickAdapter<FabricCheckTask, BaseViewHolder> {

    public FabricCheckTaskAdapter(int layoutResId, @Nullable List<FabricCheckTask> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FabricCheckTask item) {
        String goodsInfo = Utils.nullOrEmpty(item.getGoodsName()) + "(" + Utils.nullOrEmpty(item.getGoodsNo()) + ")";
        helper.setText(R.id.tv_goods_info, goodsInfo)
                .setText(R.id.tv_order_no, "#" + Utils.nullOrEmpty(item.getOrderNo()))
                .setText(R.id.tv_theme, Utils.nullOrEmpty(item.getOrderTheme()))
                .setText(R.id.tv_related_company, Utils.nullOrEmpty(item.getRelatedCompanyShortName()));

        ImageView icon = helper.getView(R.id.sdv_goods_photo);
        String goodsPhoto = CommonUtil.getFirstPhotoFromJsonList(item.getGoodsPhotos());
        if (!TextUtils.isEmpty(goodsPhoto)) {
            GlideApp.with(mContext).load(goodsPhoto + Utils.OSSImageSize(200)).into(icon);
        } else {
            icon.setImageResource(R.mipmap.icon_default_goods);
        }
    }


//    private OnSubItemClickListener onSubItemClickListener;
//
//    private Context mContext;
//
//    public FabricCheckTaskAdapter(Context context) {
//        this.mContext = context;
//    }
//
//
//    public void setOnSubItemClickListener(OnSubItemClickListener onSubItemClickListener) {
//        this.onSubItemClickListener = onSubItemClickListener;
//    }
//
//    /**
//     * 悬浮标题栏被点击的时候，展开收起切换功能
//     */
//    public void switchExpand(int adapterPosition) {
//        int groupIndex = mIndexMap.get(adapterPosition).getGroupIndex();
//        ExpandGroupItemEntity entity = mDataList.get(groupIndex);
//        entity.setExpand(!entity.isExpand());
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        /*  if (viewType == VIEW_TYPE_ITEM_TIME) {*/
//        TitleItemHolder holder = new TitleItemHolder(
//                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fabric_check_task, parent, false));
//        return holder;
///*        } else {
//            SubItemHolder itemHolder = new SubItemHolder(
//                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fabric_check_lot_info, parent, false));
//
//            return itemHolder;
//        }*/
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        //  if (getItemViewType(position) == VIEW_TYPE_ITEM_TIME) {
//        int groupIndex = mIndexMap.get(position).getGroupIndex();
//        TitleItemHolder itemHolder = (TitleItemHolder) holder;
//        itemHolder.itemView.setTag(mDataList.get(groupIndex));
//        FabricCheckTask fabricCheckTask = mDataList.get(groupIndex).getParent();
//
//        String goodsInfo = fabricCheckTask.getGoodsName() + "(" + fabricCheckTask.getGoodsNo() + ")";
//        itemHolder.mGoodsInfoTv.setText(goodsInfo);
//        itemHolder.mOrderNoTv.setText("#" + fabricCheckTask.getOrderNo());
//        itemHolder.mThemeTv.setText(fabricCheckTask.getOrderTheme());
//
////        if (fabricCheckTask.getDeliveryDates() != null && fabricCheckTask.getDeliveryDates().size() > 0){
////            itemHolder.tvDate.setText(fabricCheckTask.getDeliveryDates().get(0).getDeliveryTime());
////        }else {
////            itemHolder.tvDate.setText("");
////        }
//        String goodsPhoto = CommonUtil.getFirstPhotoFromJsonList(fabricCheckTask.getGoodsPhotos());
//        if (!TextUtils.isEmpty(goodsPhoto)) {
//           // itemHolder.mGoodsPhotoSdv.setImageURI(Uri.parse(goodsPhoto));
//            GlideApp.with(mContext).load(goodsPhoto + Utils.OSSImageSize(200)).into(itemHolder.mGoodsPhotoSdv);
//        } else {
//            itemHolder.mGoodsPhotoSdv.setImageResource(R.mipmap.icon_default_goods);
//        }
//
//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(mContext, FabricCheckOrderProcessActivity.class);
//            intent.putExtra("data", JSON.toJSONString(fabricCheckTask));
//            mContext.startActivity(intent);
//        });
//
//    }
//
//    class TitleItemHolder extends RecyclerView.ViewHolder {
//
//        ImageView mGoodsPhotoSdv;
//        TextView mGoodsInfoTv;
//        TextView mRelatedCompanyTv;
//        TextView mOrderNoTv;
//        TextView mThemeTv;
//        LinearLayout llInfo;
//
//        LinearLayout llInfoHeader;
//        TextView tvDate;
//
//
//        TitleItemHolder(View itemView) {
//            super(itemView);
//            mGoodsPhotoSdv = itemView.findViewById(R.id.sdv_goods_photo);
//            mGoodsInfoTv = itemView.findViewById(R.id.tv_goods_info);
//            mRelatedCompanyTv = itemView.findViewById(R.id.tv_related_company);
//            mOrderNoTv = itemView.findViewById(R.id.tv_order_no);
//            mThemeTv = itemView.findViewById(R.id.tv_theme);
//            llInfo = itemView.findViewById(R.id.ll_info);
//            tvDate = itemView.findViewById(R.id.tv_date);
//
//
//            llInfoHeader = itemView.findViewById(R.id.ll_header);
//        }
//
//
//    }
//
//    static class SubItemHolder extends RecyclerView.ViewHolder {
//
//        LinearLayout mHeaderLl;
//        LinearLayout mBodyLl;
//        LinearLayout mEditLl;
//        TextView mLotNoTv;
//        TextView mNumTv;
//        TextView mWeightTv;
//        TextView mLengthTv;
//        ImageView mAddIv;
//
//        SubItemHolder(View itemView) {
//            super(itemView);
//            mHeaderLl = itemView.findViewById(R.id.ll_header);
//            mBodyLl = itemView.findViewById(R.id.ll_body);
//            mEditLl = itemView.findViewById(R.id.ll_edit);
//            mLotNoTv = itemView.findViewById(R.id.tv_lot_no);
//            mNumTv = itemView.findViewById(R.id.tv_num);
//            mWeightTv = itemView.findViewById(R.id.tv_weight);
//            mLengthTv = itemView.findViewById(R.id.tv_length);
//            mAddIv = itemView.findViewById(R.id.iv_add);
//        }
//    }
//
//    public interface OnSubItemClickListener {
//        void onItemClick(CheckGoodsBean.CheckGang gang);
//    }


}
