package com.wd.winddots.activity.check.fabric;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.activity.work.GlideEngine;
import com.wd.winddots.activity.work.PicBean;
import com.wd.winddots.adapter.check.fabric.FabricCheckTaskLotProcessPositionAdapter;
import com.wd.winddots.entity.FabricCheckTaskRecordFault;
import com.wd.winddots.entity.FabricCheckTaskRecordPosition;
import com.wd.winddots.entity.ImageEntity;
import com.wd.winddots.utils.VolleyUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * FileName: FabricCheckLotProcessFaultActivity
 * Author: 郑
 * Date: 2021/2/26 9:35 AM
 * Description: 添加问题
 */
public class FabricCheckProblemActivity extends BaseActivity implements FabricCheckTaskLotProcessPositionAdapter.OnAddPhotoClickListener{

    private VolleyUtil mVolleyUtil;

    @BindView(R.id.et_position)
    EditText mPositionEt;

    @BindView(R.id.rv_check)
    RecyclerView mCheckRv;
    private FabricCheckTaskLotProcessPositionAdapter mAdapter;

    private List<FabricCheckTaskRecordPosition> mPisitionList = new ArrayList<>();

    private List<FabricCheckTaskRecordFault> mDataSource = new ArrayList<>();

    private FabricCheckTaskRecordPosition mCurrentPosition;
    private int mCurrentPhotoIndex = -1;

    private String mId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabric_check_task_lot_process_fault);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);

        for (int i = 0; i < 5; i++) {
            FabricCheckTaskRecordPosition position = new FabricCheckTaskRecordPosition();
            List<FabricCheckTaskRecordFault> fabricCheckTaskRecordFaults = new ArrayList<>();
            position.setPosition((i+1) + "");
            for (int m = 0; m < i + 1; m++) {
                fabricCheckTaskRecordFaults.add(new FabricCheckTaskRecordFault());
            }
            position.setRecordFaultList(fabricCheckTaskRecordFaults);
            mPisitionList.add(position);
        }
        mCurrentPosition = mPisitionList.get(0);
        mDataSource.addAll(mCurrentPosition.getRecordFaultList());
        mPositionEt.setText(mCurrentPosition.getPosition());


        initView();
        getData();

    }

    private void initView() {

        mAdapter = new FabricCheckTaskLotProcessPositionAdapter(R.layout.item_fabric_check_task_lot_process_fault, mDataSource);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCheckRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mCheckRv.setLayoutManager(layoutManager);
        mCheckRv.setAdapter(mAdapter);
        mAdapter.setOnAddPhotoClickListener(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_previous, R.id.tv_next,R.id.ll_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_previous:
                onPreviousDidClick();
                break;
            case R.id.tv_next:
                onNextDidClick();
                break;
            case R.id.ll_delete:
                onDeleteDidClick();
                break;
        }
    }

    private void getData() {

    }


    /*
     * 点击上一条
     * */
    private void onPreviousDidClick() {
        int index = mPisitionList.indexOf(mCurrentPosition);
        if (index == 0) {
            showToast("已经是第一条了");
            return;
        }
        mCurrentPosition.setRecordFaultList(mAdapter.getData());
        index -= 1;
        mCurrentPosition = mPisitionList.get(index);
        mAdapter.setNewData(mCurrentPosition.getRecordFaultList());
        mPositionEt.setText(mCurrentPosition.getPosition());
    }

    /*
     * 点击下一条
     * */
    private void onNextDidClick() {
        int index = mPisitionList.indexOf(mCurrentPosition);
        if (index == mPisitionList.size() - 1) {
            FabricCheckTaskRecordPosition position = new FabricCheckTaskRecordPosition();
            List<FabricCheckTaskRecordFault> fabricCheckTaskRecordFaults = new ArrayList<>();
            fabricCheckTaskRecordFaults.add(new FabricCheckTaskRecordFault());
            position.setRecordFaultList(fabricCheckTaskRecordFaults);
            position.setPosition(((index + 1) + ""));
            mPisitionList.add(position);
        }
        mCurrentPosition.setRecordFaultList(mAdapter.getData());
        index +=1;
        mCurrentPosition = mPisitionList.get(index);
        mAdapter.setNewData(mCurrentPosition.getRecordFaultList());
        mPositionEt.setText(mCurrentPosition.getPosition());
    }

    /*
    * 点击删除按钮
    * */
    private void onDeleteDidClick(){
        List<FabricCheckTaskRecordFault> data = mAdapter.getData();
        if (data.size() ==1){
            return;
        }
        for (int i = 0;i < data.size();i++){
            FabricCheckTaskRecordFault item = data.get(i);
            if (item.isSelect()){
                mAdapter.remove(i) ;
            }
        }
    }


    @Override
    public void onAddPhotoDidClick(int position) {
        mCurrentPhotoIndex = position;
        EasyPhotos.createAlbum(FabricCheckProblemActivity.this,true, GlideEngine.getInstance())//参数说明：上下文，是否显示相机按钮，[配置Glide为图片加载引擎](https://github.com/HuanTanSheng/EasyPhotos/wiki/12-%E9%85%8D%E7%BD%AEImageEngine%EF%BC%8C%E6%94%AF%E6%8C%81%E6%89%80%E6%9C%89%E5%9B%BE%E7%89%87%E5%8A%A0%E8%BD%BD%E5%BA%93)
                .setFileProviderAuthority("com.wd.winddots.fileprovider")//参数说明：见下方`FileProvider的配置`
                .start(101);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK) {
            //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
            ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
            if (null != resultPhotos){
                List<PicBean> picBeans = new ArrayList<>();
                for (int i=0;i<resultPhotos.size();i++){
                    PicBean picBean = new PicBean();
                    picBean.picUrl = resultPhotos.get(i).path;
                    picBean.picId = i+"";
                    picBeans.add(0,picBean);
                }
                //picsAdapter.setList(picBeans);
                mAdapter.getData().get(mCurrentPhotoIndex).setPicBeanList(picBeans);
                mAdapter.refreshNotifyItemChanged(mCurrentPhotoIndex);
            }
            Log.d("chenld","photo="+resultPhotos.toString());
        }
    }
}
