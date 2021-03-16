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
import com.wd.winddots.entity.FabricCheckProblem;
import com.wd.winddots.entity.ImageEntity;
import com.wd.winddots.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: FabricCheckTaskLotProcessPositionAdapter
 * Author: 郑
 * Date: 2021/2/23 11:47 AM
 * Description:
 */
public class FabricCheckProblemAdapter extends BaseQuickAdapter<FabricCheckProblem, BaseViewHolder> {


    public List<SpinnerView.SpinnerBean> problemList = new ArrayList<>();
    private List<SpinnerView.SpinnerBean> levelList = new ArrayList<>();

    private OnAddPhotoClickListener mOnAddPhotoClickListener;

    public void setOnAddPhotoClickListener(OnAddPhotoClickListener onAddPhotoClickListener) {
        mOnAddPhotoClickListener = onAddPhotoClickListener;
    }

    public FabricCheckProblemAdapter(int layoutResId, @Nullable List<FabricCheckProblem> data) {
        super(layoutResId, data);
        SpinnerView.SpinnerBean bean0 = new SpinnerView.SpinnerBean();
        bean0.setName("");
        SpinnerView.SpinnerBean bean1 = new SpinnerView.SpinnerBean();
        bean1.setName("A");
        SpinnerView.SpinnerBean bean2 = new SpinnerView.SpinnerBean();
        bean2.setName("B");
        SpinnerView.SpinnerBean bean3 = new SpinnerView.SpinnerBean();
        bean3.setName("C");
        SpinnerView.SpinnerBean bean4 = new SpinnerView.SpinnerBean();
        bean4.setName("D");
        levelList.add(bean0);
        levelList.add(bean1);
        levelList.add(bean2);
        levelList.add(bean3);
        levelList.add(bean4);
    }

    @Override
    protected void convert(BaseViewHolder helper, FabricCheckProblem item) {

        SpinnerView faultSpinner = helper.getView(R.id.sv_question);
        SpinnerView levelSpinner = helper.getView(R.id.sv_level);
        LinearLayout selectLl = helper.getView(R.id.ll_select);
        ImageView selectIv = helper.getView(R.id.iv_select);
        ImageView addIv = helper.getView(R.id.iv_add);
        ImageView addImageIv = helper.getView(R.id.iv_add_photo);

        faultSpinner.setSelectList(problemList);
        levelSpinner.setSelectList(levelList);

        levelSpinner.setOnselectListener(new SpinnerView.OnselectListener() {
            @Override
            public void onselect(int position, SpinnerView view) {
                item.setLevel(levelList.get(position).getName());
                if (position == 0){
                    item.setTagATimes(null);
                    item.setTagBTimes(null);
                    item.setTagCTimes(null);
                    item.setTagDTimes(null);
                }else if (position == 1){
                    item.setTagATimes("1");
                    item.setTagBTimes(null);
                    item.setTagCTimes(null);
                    item.setTagDTimes(null);
                }else if (position == 2){
                    item.setTagATimes(null);
                    item.setTagBTimes("1");
                    item.setTagCTimes(null);
                    item.setTagDTimes(null);
                }else if (position == 3){
                    item.setTagATimes(null);
                    item.setTagBTimes(null);
                    item.setTagCTimes("1");
                    item.setTagDTimes(null);
                }else if (position == 4){
                    item.setTagATimes(null);
                    item.setTagBTimes(null);
                    item.setTagCTimes(null);
                    item.setTagDTimes("1");
                }
            }
        });
        if (!StringUtils.isNullOrEmpty(item.getTagATimes())){
            levelSpinner.setDefaultPosition(1);
        }else if (!StringUtils.isNullOrEmpty(item.getTagBTimes())){
            levelSpinner.setDefaultPosition(2);
        }else if (!StringUtils.isNullOrEmpty(item.getTagCTimes())){
            levelSpinner.setDefaultPosition(3);
        }else if (!StringUtils.isNullOrEmpty(item.getTagDTimes())){
            levelSpinner.setDefaultPosition(4);
        }else {
            levelSpinner.setDefaultPosition(0);
        }
        if (StringUtils.isNullOrEmpty(item.getTag())){
            item.setTag(problemList.get(0).getName());
        }

        int index = 0;
        for (int i = 0;i < problemList.size();i++){
            SpinnerView.SpinnerBean problem = problemList.get(i);
            if (item.getTag().equals(problem.getName())){
                index = i;
            }
        }
        faultSpinner.setDefaultPosition(index);
        faultSpinner.setOnselectListener(new SpinnerView.OnselectListener() {
            @Override
            public void onselect(int position, SpinnerView view) {
                item.setTag(problemList.get(position).getName());
            }
        });




        if (item.getImageEntities() != null && item.getImageEntities().size() > 0){
            ImageEntity imageEntity = item.getImageEntities().get(0);
            if (!StringUtils.isNullOrEmpty(imageEntity.getPath())){
                GlideApp.with(mContext).load(imageEntity.getPath()).into(addImageIv);
            }else if (!StringUtils.isNullOrEmpty(imageEntity.getUrl())){
                GlideApp.with(mContext).load(imageEntity.getUrl() + Utils.OSSImageSize(100)).into(addImageIv);
            }
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
                FabricCheckProblem newItem = JSON.parseObject(JSON.toJSONString(item), FabricCheckProblem.class);
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
