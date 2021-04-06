package com.wd.winddots.activity.check.fabric;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.activity.work.GlideEngine;
import com.wd.winddots.adapter.check.fabric.FabricCheckProblemAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.desktop.list.check.view.SpinnerView;
import com.wd.winddots.entity.FabricCheckProblem;
import com.wd.winddots.entity.FabricCheckProblemBean;
import com.wd.winddots.entity.FabricCheckProblemSelect;
import com.wd.winddots.entity.FabricCheckTaskRecordPosition;
import com.wd.winddots.entity.ImageEntity;
import com.wd.winddots.utils.OSSUploadHelper;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: FabricCheckLotProcessFaultActivity
 * Author: 郑
 * Date: 2021/2/26 9:35 AM
 * Description: 添加问题
 */
public class FabricCheckProblemActivity extends BaseActivity implements FabricCheckProblemAdapter.OnAddPhotoClickListener {

    private VolleyUtil mVolleyUtil;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.et_position)
    EditText mPositionEt;

    @BindView(R.id.et_width_top)
    EditText mTopWidthEt;

    @BindView(R.id.et_width_middle)
    EditText mMiddleWidthEt;

    @BindView(R.id.et_width_bottom)
    EditText mBottomWidthEt;

    @BindView(R.id.et_remark)
    EditText mRemarkEt;

    @BindView(R.id.rv_check)
    RecyclerView mCheckRv;
    private FabricCheckProblemAdapter mAdapter;

    private List<FabricCheckTaskRecordPosition> mPisitionList = new ArrayList<>();

    private List<FabricCheckProblem> mDataSource = new ArrayList<>();

    private FabricCheckTaskRecordPosition mCurrentPosition;
    private int mCurrentPhotoIndex = -1;

    private String mRecordId;

    private List<String> mProblemList = new ArrayList<>();


    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10086) {
                hideLoadingDialog();
            } else if (msg.what == 10087) {
                hideLoadingDialog();
                mAdapter.getData().get(mCurrentPhotoIndex).setImageEntities(new ArrayList<>());
                mAdapter.refreshNotifyItemChanged(mCurrentPhotoIndex);
                showToast("上传图片失败,请稍后重试");
                return;
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabric_check_problem);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);

        initView();
        getData();
    }

    private void initView() {
        Intent intent = getIntent();
        mRecordId = intent.getStringExtra("recordId");
        String goodsName = intent.getStringExtra("goodsName");
        String goodsNo = intent.getStringExtra("goodsNo");
        String date = intent.getStringExtra("date");
        String position = intent.getStringExtra("position");
        String problemString = intent.getStringExtra("problemString");
        mProblemList.addAll(Objects.requireNonNull(JSON.parseArray(problemString, String.class)));

        assert date != null;
        if (date.length() == 10) {
            date = date.substring(5, 10);
        }

        mTitleTv.setText(Utils.nullOrEmpty(goodsNo) + "(" + Utils.nullOrEmpty(goodsName) + ") " + date + "-" + position);

        mAdapter = new FabricCheckProblemAdapter(R.layout.item_fabric_check_problem, mDataSource);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCheckRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mCheckRv.setLayoutManager(layoutManager);
        mCheckRv.setAdapter(mAdapter);
        mAdapter.setOnAddPhotoClickListener(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_previous, R.id.tv_next, R.id.ll_delete, R.id.iv_add,R.id.tv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_previous:
                onSave(R.id.tv_previous);
                break;
            case R.id.tv_next:
                onSave(R.id.tv_next);
                break;
            case R.id.ll_delete:
                onDeleteDidClick();
                break;
            case R.id.iv_add:
                //onAddDidClick();
                break;
            case R.id.tv_add:
                onAddDidClick();
                break;
        }
    }


    private void getData() {
        String url = Constant.APP_BASE_URL + "fabricCheckRecordProblem?recordId=" + mRecordId + "&enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId();
        Log.e("net666", url);
        mVolleyUtil.httpGetRequest(url, response -> {
            if (null == response) {
                return;
            }
            Log.e("net666", response);
            FabricCheckProblemBean bean = JSON.parseObject(response, FabricCheckProblemBean.class);

            FabricCheckProblemBean.FabricCheckWidth fabricCheckWidth = bean.getFabricCheckRecord();
            if (null != fabricCheckWidth){
                mTopWidthEt.setText(Utils.nullOrEmpty(fabricCheckWidth.getWidthTop()));
                mMiddleWidthEt.setText(Utils.nullOrEmpty(fabricCheckWidth.getWidthMiddle()));
                mBottomWidthEt.setText(Utils.nullOrEmpty(fabricCheckWidth.getWidthBottom()));
            }


            List<FabricCheckProblemSelect> problemSelects = bean.getFabricCheckProblemConfigList();
            List<SpinnerView.SpinnerBean> problems = new ArrayList<>();
            if (problemSelects == null || problemSelects.size() == 0) {
                problemSelects = new ArrayList<>();
                SpinnerView.SpinnerBean spinnerBean = new SpinnerView.SpinnerBean();
                spinnerBean.setName("破洞");
                problems.add(spinnerBean);
            }

            for (int i = 0; i < problemSelects.size(); i++) {
                FabricCheckProblemSelect problemSelect = problemSelects.get(i);
                SpinnerView.SpinnerBean spinnerBean = new SpinnerView.SpinnerBean();
                spinnerBean.setName(problemSelect.getTag());
                problems.add(spinnerBean);
            }

            mAdapter.problemList = problems;
            List<FabricCheckTaskRecordPosition> fabricCheckProblemConfigList = bean.getFabricCheckRecordProblemPositionList();
            if (fabricCheckProblemConfigList == null || fabricCheckProblemConfigList.size() == 0) {
                FabricCheckTaskRecordPosition position = new FabricCheckTaskRecordPosition();
                List<FabricCheckProblem> fabricCheckProblems = new ArrayList<>();
                if (mProblemList.size() > 0){
                    for (int i = 0;i < mProblemList.size();i++){
                        FabricCheckProblem problem = new FabricCheckProblem();
                        problem.setTag(mProblemList.get(i));
                        fabricCheckProblems.add(problem);
                    }
                }else {
                    fabricCheckProblems.add(new FabricCheckProblem());
                }
                position.setFabricCheckRecordProblemList(fabricCheckProblems);
                mPisitionList.add(position);
                mCurrentPosition = mPisitionList.get(0);
                mDataSource.addAll(mCurrentPosition.getFabricCheckRecordProblemList());
                mPositionEt.setText(Utils.nullOrEmpty(mCurrentPosition.getProblemPosition()));
                mRemarkEt.setText(Utils.nullOrEmpty(mCurrentPosition.getRemark()));
                mAdapter.notifyDataSetChanged();
            } else {
                for (int i = 0; i < fabricCheckProblemConfigList.size(); i++) {
                    FabricCheckTaskRecordPosition problemPosition = fabricCheckProblemConfigList.get(i);
                    List<FabricCheckProblem> problemList = problemPosition.getFabricCheckRecordProblemList();
                    if (problemList == null) {
                        problemList = new ArrayList<>();
                    }
                    for (int m = 0; m < problemList.size(); m++) {
                        FabricCheckProblem problemItem = problemList.get(m);
                        String imageJson = problemItem.getImage();
                        try {
                            List<String> imageList = JSON.parseArray(imageJson, String.class);
                            if (imageList.size() > 0) {
                                List<ImageEntity> imageEntityList = new ArrayList<>();
                                for (int n = 0; n < imageList.size(); n++) {
                                    ImageEntity imageEntity = new ImageEntity();
                                    imageEntity.setUrl(imageList.get(n));
                                    imageEntityList.add(imageEntity);
                                }
                                problemItem.setImageEntities(imageEntityList);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                    if (problemList.size() == 0) {
                        problemList.add(new FabricCheckProblem());
                    }
                }
                mPisitionList.addAll(fabricCheckProblemConfigList);
                mCurrentPosition = mPisitionList.get(0);
                mDataSource.addAll(mCurrentPosition.getFabricCheckRecordProblemList());
                mPositionEt.setText(Utils.nullOrEmpty(mCurrentPosition.getProblemPosition()));
                mRemarkEt.setText(Utils.nullOrEmpty(mCurrentPosition.getRemark()));
                mAdapter.notifyDataSetChanged();
            }

        }, volleyError -> {
            Log.e("net666", String.valueOf(volleyError));
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
    }


    /*
     * 点击上一条
     * */
    private void onPreviousDidClick() {
    }

    /*
     * 点击下一条
     * */
    private void onNextDidClick() {
    }

    private void onSave(int viewId) {
        String problemPosition = mPositionEt.getText().toString().trim();
        String remark = mRemarkEt.getText().toString().trim();

        if (StringUtils.isNullOrEmpty(problemPosition)) {
            if (R.id.tv_previous == viewId){
                toPreviousPosition();
                return;
            }
//            showToast("请先输入问题位置");
//            return;
        }


        Map<Object, Object> params = new HashMap<>();
        params.put("recordId", mRecordId);
        params.put("problemPosition", problemPosition);
        params.put("remark",Utils.nullOrEmpty(remark));
        if (!StringUtils.isNullOrEmpty(mCurrentPosition.getId())) {
            params.put("id", mCurrentPosition.getId());
        }

        params.put("widthTop",Utils.nullOrEmpty(mTopWidthEt.getText().toString().trim()));
        params.put("widthMiddle",Utils.nullOrEmpty(mMiddleWidthEt.getText().toString().trim()));
        params.put("widthBottom",Utils.nullOrEmpty(mBottomWidthEt.getText().toString().trim()));


        List<Map<String, String>> fabricCheckRecordProblemList = new ArrayList<>();
        List<FabricCheckProblem> problemList = mAdapter.getData();
        if (!StringUtils.isNullOrEmpty(problemPosition)){
            for (int i = 0; i < problemList.size(); i++) {
                FabricCheckProblem problem = problemList.get(i);
                Map<String, String> problemMap = new HashMap<>();
                problemMap.put("recordId", mRecordId);
                if (!StringUtils.isNullOrEmpty(mCurrentPosition.getId())) {
                    problemMap.put("id", mCurrentPosition.getId());
                }
                problemMap.put("problemPosition", problemPosition);
                problemMap.put("tag", problem.getTag());
                if ("A".equals(problem.getLevel())) {
                    problemMap.put("tagATimes", "1");
                } else if ("B".equals(problem.getLevel())) {
                    problemMap.put("tagBTimes", "1");
                } else if ("C".equals(problem.getLevel())) {
                    problemMap.put("tagCTimes", "1");
                } else if ("D".equals(problem.getLevel())) {
                    problemMap.put("tagDTimes", "1");
                } else {
                    continue;
                }
                List<String> imageList = new ArrayList<>();
                List<ImageEntity> imageEntityList = problem.getImageEntities();
                if (imageEntityList != null && imageEntityList.size() > 0) {
                    for (int m = 0; m < imageEntityList.size(); m++) {
                        imageList.add(imageEntityList.get(m).getUrl());
                    }
                }
                problemMap.put("image", JSON.toJSONString(imageList));
                fabricCheckRecordProblemList.add(problemMap);
            }
        }
        Log.e("net666", JSON.toJSONString(fabricCheckRecordProblemList));
//        if (fabricCheckRecordProblemList.size() == 0) {
//            Toast.makeText(this, "请先添加问题", Toast.LENGTH_LONG).show();
//            return;
//        }


        String url = Constant.APP_BASE_URL + "fabricCheckRecordProblem?&problemPosition=" + problemPosition;
        //Map<String, String> params = new HashMap<>();
        params.put("fabricCheckRecordProblemStrJson", JSON.toJSONString(fabricCheckRecordProblemList));
        Log.e("net666", JSON.toJSONString(params));

        Map<String, String> body = new HashMap<>();
        body.put("fabricCheckRecordProblemPositionStrJson", JSON.toJSONString(params));


        showLoadingDialog();
        mVolleyUtil.httpPostRequest(url, body, response -> {
            hideLoadingDialog();
            if (null == response) {
                return;
            }
            Log.e("net666", response);

            if (StringUtils.isNullOrEmpty(problemPosition) && fabricCheckRecordProblemList.size() == 0){
                showToast("保存成功");
            }else {
                FabricCheckTaskRecordPosition position = JSON.parseObject(response, FabricCheckTaskRecordPosition.class);
                if (position == null || StringUtils.isNullOrEmpty(position.getId())) {
                    showToast("保存失败,请稍后重试");
                    return;
                }
                mCurrentPosition.setId(position.getId());
                if (viewId == R.id.tv_next) {
                    toNextPosition();
                } else {
                    toPreviousPosition();
                }
            }


        }, volleyError -> {
            hideLoadingDialog();
            Log.e("net666", String.valueOf(volleyError));
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
    }


    private void toPreviousPosition() {
        int index = mPisitionList.indexOf(mCurrentPosition);
        if (index == 0) {
            showToast("已经是第一条了");
            return;
        }

        mCurrentPosition.setFabricCheckRecordProblemList(mAdapter.getData());
        mCurrentPosition.setProblemPosition(mPositionEt.getText().toString().trim());
        mCurrentPosition.setRemark(mRemarkEt.getText().toString().trim());
        index -= 1;
        mCurrentPosition = mPisitionList.get(index);
        mAdapter.setNewData(mCurrentPosition.getFabricCheckRecordProblemList());
        mPositionEt.setText(Utils.nullOrEmpty(mCurrentPosition.getProblemPosition()));
        mRemarkEt.setText(Utils.nullOrEmpty(mCurrentPosition.getRemark()));
    }

    private void toNextPosition() {

//        List<FabricCheckProblem> currentProblems = mAdapter.getData();
//        List<String> problemStringList = new ArrayList<>();
//        for (int i = 0;i < currentProblems.size();i++){
//
//        }
//
        int index = mPisitionList.indexOf(mCurrentPosition);
        if (index == mPisitionList.size() - 1) {
            FabricCheckTaskRecordPosition position = new FabricCheckTaskRecordPosition();
            List<FabricCheckProblem> fabricCheckProblems = new ArrayList<>();
            List<String> problemStringList = new ArrayList<>();
            for (int i = 0; i < mPisitionList.size(); i++) {
                List<FabricCheckProblem> problemList = mPisitionList.get(i).getFabricCheckRecordProblemList();
                for (int n = 0; n < problemList.size(); n++) {
                    problemStringList.add(problemList.get(n).getTag());
                }
            }
            problemStringList.addAll(mProblemList);
            Set<String> set = new LinkedHashSet<>(problemStringList);
            problemStringList.clear();
            problemStringList.addAll(set);
            Log.e("net666", String.valueOf(problemStringList));
            for (int i = 0;i < problemStringList.size();i++){
                FabricCheckProblem problem = new FabricCheckProblem();
                problem.setTag(problemStringList.get(i));
                fabricCheckProblems.add(problem);
            }
            position.setFabricCheckRecordProblemList(fabricCheckProblems);
            mPisitionList.add(position);
        }
        mCurrentPosition.setProblemPosition(mPositionEt.getText().toString().trim());
        mCurrentPosition.setFabricCheckRecordProblemList(mAdapter.getData());
        mCurrentPosition.setRemark(mRemarkEt.getText().toString().trim());
        index += 1;
        mCurrentPosition = mPisitionList.get(index);
        mAdapter.setNewData(mCurrentPosition.getFabricCheckRecordProblemList());
        mPositionEt.setText(Utils.nullOrEmpty(mCurrentPosition.getProblemPosition()));
        mRemarkEt.setText(Utils.nullOrEmpty(mCurrentPosition.getRemark()));
    }

    /*
     * 点击删除
     * */
    private void onDeleteDidClick() {
        List<FabricCheckProblem> data = mAdapter.getData();
        List<FabricCheckProblem> newData = new ArrayList<>();

        if (data.size() == 1) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            FabricCheckProblem item = data.get(i);
            if (!item.isSelect()) {
                newData.add(item);
            }
        }
        mAdapter.setNewData(newData);
    }

    /*
     * 点击添加
     * */
    private void onAddDidClick() {
        mAdapter.addData(new FabricCheckProblem());
    }


    /*
     * 点击添加图片
     * */
    @Override
    public void onAddPhotoDidClick(int position) {
        mCurrentPhotoIndex = position;
        int count = 9;
        List<ImageEntity> imageEntityList = mAdapter.getData().get(position).getImageEntities();
        if (imageEntityList == null || imageEntityList.size() == 0) {
            count = 9;
        } else {
            count = 9 - imageEntityList.size();
        }
        if (count == 0) {
            return;
        }
        EasyPhotos.createAlbum(FabricCheckProblemActivity.this, true, GlideEngine.getInstance())//参数说明：上下文，是否显示相机按钮，[配置Glide为图片加载引擎](https://github.com/HuanTanSheng/EasyPhotos/wiki/12-%E9%85%8D%E7%BD%AEImageEngine%EF%BC%8C%E6%94%AF%E6%8C%81%E6%89%80%E6%9C%89%E5%9B%BE%E7%89%87%E5%8A%A0%E8%BD%BD%E5%BA%93)
                .setFileProviderAuthority("com.wd.winddots.fileprovider")//参数说明：见下方`FileProvider的配置`
                .setCount(count)
                .start(101);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK) {
            //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
            ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
            if (null != resultPhotos) {
                List<ImageEntity> picBeans = new ArrayList<>();
                for (int i = 0; i < resultPhotos.size(); i++) {
                    ImageEntity imageEntity = new ImageEntity();
                    imageEntity.setPath(resultPhotos.get(i).path);
                    imageEntity.setId(i + "");
                    picBeans.add(0, imageEntity);
                }
                //picsAdapter.setList(picBeans);

                showLoadingDialog();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        for (int t = 0; t < picBeans.size(); t++) {
                            ImageEntity imageEntity = picBeans.get(t);
                            if (!StringUtils.isNullOrEmpty(imageEntity.getPath())) {
                                String imagePath = imageEntity.getPath();//Utils.uri2File(this, imageEntity.getUrl());
                                String imageUrl = OSSUploadHelper.uploadImage(imagePath);
                                if (imageUrl != null) {
                                    Log.e("net666", imageUrl);
                                    imageEntity.setUrl(imageUrl);
                                } else {
                                    Message msg = new Message();
                                    msg.what = 10087;
                                    mhandler.sendMessage(msg);
                                    return;
                                }
                            }
                        }
                        Message msg = new Message();
                        msg.what = 10086;
                        mhandler.sendMessage(msg);
                    }
                }).start();
                mAdapter.getData().get(mCurrentPhotoIndex).setImageEntities(picBeans);
                mAdapter.refreshNotifyItemChanged(mCurrentPhotoIndex);
            }
            Log.d("chenld", "photo=" + resultPhotos.toString());
        }
    }
}
