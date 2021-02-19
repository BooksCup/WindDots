package com.wd.winddots.desktop.list.check.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.check.bean.CheckQuestionImageBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: CheckQuestionImageAdapter
 * Author: 郑
 * Date: 2021/1/15 3:33 PM
 * Description:
 */
public class CheckQuestionImageAdapter extends BaseQuickAdapter<CheckQuestionImageBean, BaseViewHolder> {
    private int itemWidth = 0;
    private int deleteIconVisibility = View.VISIBLE;

    public void setDeleteIconVisibility(int deleteIconVisibility) {
        this.deleteIconVisibility = deleteIconVisibility;
    }

    private ImagePickerAdpaterDeleteListener listener;


    public CheckQuestionImageAdapter(int layoutResId, @Nullable List<CheckQuestionImageBean> data, int itemWidth1) {
        super(layoutResId, data);
        itemWidth = itemWidth1;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CheckQuestionImageBean item) {

        RelativeLayout layout = helper.getView(R.id.item_image_picker_body);
        ImageView imageView = helper.getView(R.id.item_image_picker_image);
        ImageView deleteBtn = helper.getView(R.id.item_image_picker_delete);


        layout.getLayoutParams().height = itemWidth;
        layout.getLayoutParams().width = itemWidth;
        if (item.getImageUri() != null){
            GlideApp.with(mContext).load(item.getImageUri()).into(imageView);
        }else if (item.getImageUrl() != null){
            GlideApp.with(mContext).load(item.getImageUrl() + Utils.OSSImageSize(300)).into(imageView);
        }else {
            imageView.setImageResource(R.mipmap.add_img);
        }

        if (item.getImageUri() == null && item.getImageUrl() == null){
            deleteBtn.setVisibility(View.GONE);
        }else {
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


    public void setImagePickerAdpaterDeleteListener(ImagePickerAdpaterDeleteListener listener1){
        listener = listener1;
    }


    public interface ImagePickerAdpaterDeleteListener {
        void deleIconDidClick(CheckQuestionImageBean item);
    }
}
