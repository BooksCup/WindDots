package com.wd.winddots.fast.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: MineClaimingImagePickerAdpater
 * Author: 郑
 * Date: 2020/5/11 9:44 AM
 * Description:
 */
public class MineClaimingImagePickerAdpater extends BaseQuickAdapter<ApplyDetailBean.Invoice, BaseViewHolder> {


    private int itemWidth = 0;
    private int deleteIconVisibility = View.VISIBLE;

    public void setDeleteIconVisibility(int deleteIconVisibility) {
        this.deleteIconVisibility = deleteIconVisibility;
    }

    private MineClaimingImagePickerAdpaterDeleteListener listener;


    public MineClaimingImagePickerAdpater(int layoutResId, @Nullable List<ApplyDetailBean.Invoice> data, int itemWidth1) {
        super(layoutResId, data);
        itemWidth = itemWidth1;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ApplyDetailBean.Invoice item) {

        RelativeLayout layout = helper.getView(R.id.item_image_picker_body);
        ImageView imageView = helper.getView(R.id.item_image_picker_image);
        ImageView deleteBtn = helper.getView(R.id.item_image_picker_delete);


        layout.getLayoutParams().height = itemWidth;
        layout.getLayoutParams().width = itemWidth;
        if (item.getUri() != null){
            GlideApp.with(mContext).load(item.getUri()).into(imageView);
        }else if (item.getInvoiceImage() != null){
            GlideApp.with(mContext).load(item.getInvoiceImage() + Utils.OSSImageSize(300)).into(imageView);
        }else {
            imageView.setImageResource(R.mipmap.add_img);
        }

        if (item.getUri() == null && item.getInvoiceImage() == null){
            deleteBtn.setVisibility(View.GONE);
        }else {
            //deleteBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(deleteIconVisibility);
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.deleIconDidClick(item);
                }
            }
        });

    }


    public void setMineClaimingImagePickerAdpaterDeleteListener(MineClaimingImagePickerAdpaterDeleteListener listener1){
        listener = listener1;
    }


    public interface MineClaimingImagePickerAdpaterDeleteListener {
        void deleIconDidClick(ApplyDetailBean.Invoice item);
    }
}
