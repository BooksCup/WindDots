package com.wd.winddots.adapter.check.fabric;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.check.view.SpinnerView;
import com.wd.winddots.entity.FabricCheckTaskRecordFault;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: FabricCheckTaskLotProcessPositionAdapter
 * Author: 郑
 * Date: 2021/2/23 11:47 AM
 * Description:
 */
public class FabricCheckTaskLotProcessPositionAdapter extends BaseQuickAdapter<FabricCheckTaskRecordFault, BaseViewHolder> {


    private OnAddPhotoClickListener mOnAddPhotoClickListener;

    public void setOnAddPhotoClickListener(OnAddPhotoClickListener onAddPhotoClickListener) {
        mOnAddPhotoClickListener = onAddPhotoClickListener;
    }

    public FabricCheckTaskLotProcessPositionAdapter(int layoutResId, @Nullable List<FabricCheckTaskRecordFault> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FabricCheckTaskRecordFault item) {


        SpinnerView faultSpinner = helper.getView(R.id.sv_question);
        SpinnerView levelSpinner = helper.getView(R.id.sv_level);
        LinearLayout selectLl = helper.getView(R.id.ll_select);
        ImageView selectIv = helper.getView(R.id.iv_select);
        ImageView addIv = helper.getView(R.id.iv_add);
        ImageView addImageIv = helper.getView(R.id.iv_add_photo);

        List<SpinnerView.SpinnerBean> beans = new ArrayList<>();
        SpinnerView.SpinnerBean spinnerBean1 = new SpinnerView.SpinnerBean();
        spinnerBean1.setName("破洞");
        SpinnerView.SpinnerBean spinnerBean2 = new SpinnerView.SpinnerBean();
        spinnerBean2.setName("污渍");
        SpinnerView.SpinnerBean spinnerBean3 = new SpinnerView.SpinnerBean();
        spinnerBean3.setName("缺斤少两");
        SpinnerView.SpinnerBean spinnerBean4 = new SpinnerView.SpinnerBean();
        spinnerBean4.setName("脱线");
        SpinnerView.SpinnerBean spinnerBean5 = new SpinnerView.SpinnerBean();
        spinnerBean5.setName("起球");
        beans.add(spinnerBean1);
        beans.add(spinnerBean2);
        beans.add(spinnerBean3);
        beans.add(spinnerBean4);
        beans.add(spinnerBean5);
        faultSpinner.setSelectList(beans);

        List<SpinnerView.SpinnerBean> levelBeans = new ArrayList<>();
        SpinnerView.SpinnerBean bean1 = new SpinnerView.SpinnerBean();
        bean1.setName("A");
        SpinnerView.SpinnerBean bean2 = new SpinnerView.SpinnerBean();
        bean2.setName("B");
        SpinnerView.SpinnerBean bean3 = new SpinnerView.SpinnerBean();
        bean3.setName("C");
        SpinnerView.SpinnerBean bean4 = new SpinnerView.SpinnerBean();
        bean4.setName("D");
        levelBeans.add(bean1);
        levelBeans.add(bean2);
        levelBeans.add(bean3);
        levelBeans.add(bean4);
        levelSpinner.setSelectList(levelBeans);

        if (item.getPicBeanList() != null && item.getPicBeanList().size() > 0){
            GlideApp.with(mContext).load(item.getPicBeanList().get(0).picUrl).into(addImageIv);
        }else {
            addImageIv.setImageResource(R.mipmap.add2);
        }

        if (item.isSelect()){
            selectIv.setImageResource(R.mipmap.select);
        }else {
            selectIv.setImageResource(R.mipmap.unselect);
        }
        selectLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setSelect(!item.isSelect());
                if (item.isSelect()){
                    selectIv.setImageResource(R.mipmap.select);
                }else {
                    selectIv.setImageResource(R.mipmap.unselect);
                }
            }
        });

        addIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FabricCheckTaskRecordFault newItem = JSON.parseObject(JSON.toJSONString(item),FabricCheckTaskRecordFault.class);
                addData(newItem);
            }
        });


        addImageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnAddPhotoClickListener != null){
                    mOnAddPhotoClickListener.onAddPhotoDidClick(helper.getPosition());
                }
            }
        });
    }


    public interface OnAddPhotoClickListener{
        void onAddPhotoDidClick(int position);
    }
}
