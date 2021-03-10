package com.wd.winddots.activity.check.fabric;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.kevin.photo_browse.PhotoBrowse;
import com.kevin.photo_browse.ShowType;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.activity.work.GlideEngine;
import com.wd.winddots.adapter.check.fabric.FabricCheckProblemAdapter;
import com.wd.winddots.adapter.check.fabric.FabricCheckProblemBrowseAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.desktop.list.check.view.SpinnerView;
import com.wd.winddots.entity.FabricCheckProblem;
import com.wd.winddots.entity.FabricCheckProblemBean;
import com.wd.winddots.entity.FabricCheckProblemSelect;
import com.wd.winddots.entity.FabricCheckTaskRecordPosition;
import com.wd.winddots.entity.ImageEntity;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
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
public class FabricCheckProblemBrowseActivity extends BaseActivity implements FabricCheckProblemBrowseAdapter.OnImageClickListener {

    private VolleyUtil mVolleyUtil;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.et_position)
    TextView mPositionEt;

    @BindView(R.id.rv_check)
    RecyclerView mCheckRv;
    private FabricCheckProblemBrowseAdapter mAdapter;

    private List<FabricCheckTaskRecordPosition> mPisitionList = new ArrayList<>();

    private List<FabricCheckProblem> mDataSource = new ArrayList<>();

    private FabricCheckTaskRecordPosition mCurrentPosition;
    private int mCurrentPhotoIndex = -1;

    private String mRecordId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabric_check_problem_browse);
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

        assert date != null;
        if (date.length() == 10) {
            date = date.substring(5, 10);
        }
        mTitleTv.setText(Utils.nullOrEmpty(goodsNo) + "(" + Utils.nullOrEmpty(goodsName) + ") " + date + "-" + position);
        mAdapter = new FabricCheckProblemBrowseAdapter(R.layout.item_fabric_check_problem_brswer, mDataSource);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCheckRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mCheckRv.setLayoutManager(layoutManager);
        mCheckRv.setAdapter(mAdapter);
        mAdapter.setOnImageClickListener(this);

    }

    @OnClick({R.id.iv_back, R.id.tv_previous, R.id.tv_next})
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
            FabricCheckProblemBean bean = JSON.parseObject(response,FabricCheckProblemBean.class);


            List<FabricCheckTaskRecordPosition> problemVoList = bean.getFabricCheckRecordProblemPositionList();
            if (problemVoList == null || problemVoList.size() == 0){
                FabricCheckTaskRecordPosition position = new FabricCheckTaskRecordPosition();
                List<FabricCheckProblem> fabricCheckProblems = new ArrayList<>();
                fabricCheckProblems.add(new FabricCheckProblem());
                position.setFabricCheckRecordProblemList(fabricCheckProblems);
                mPisitionList.add(position);
                mCurrentPosition = mPisitionList.get(0);
                mDataSource.addAll(mCurrentPosition.getFabricCheckRecordProblemList());
                mPositionEt.setText(Utils.nullOrEmpty(mCurrentPosition.getProblemPosition()));
            }else {
                for (int i = 0;i < problemVoList.size();i++){
                    FabricCheckTaskRecordPosition problemPosition = problemVoList.get(i);
                    List<FabricCheckProblem> problemList = problemPosition.getFabricCheckRecordProblemList();
                    for (int m = 0;m < problemList.size();m++){
                        FabricCheckProblem problemItem = problemList.get(m);
                        String imageJson = problemItem.getImage();
                        try {
                            List<String> imageList = JSON.parseArray(imageJson,String.class);
                            if (imageList.size() > 0){
                                List<ImageEntity> imageEntityList = new ArrayList<>();
                                for (int n = 0;n < imageList.size();n++){
                                    ImageEntity imageEntity = new ImageEntity();
                                    imageEntity.setUrl(imageList.get(n));
                                    imageEntityList.add(imageEntity);
                                }
                                problemItem.setImageEntities(imageEntityList);
                            }
                            problemItem.setImages(imageList);
                        }catch (Exception ignored){
                        }
                    }
                }
                mPisitionList.addAll(problemVoList);
                mCurrentPosition = mPisitionList.get(0);
                mDataSource.addAll(mCurrentPosition.getFabricCheckRecordProblemList());
                mPositionEt.setText(Utils.nullOrEmpty(mCurrentPosition.getProblemPosition()));
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
        int index = mPisitionList.indexOf(mCurrentPosition);
        if (index == 0) {
            showToast("已经是第一条了");
            return;
        }
        mCurrentPosition.setFabricCheckRecordProblemList(mAdapter.getData());
        index -= 1;
        mCurrentPosition = mPisitionList.get(index);
        mAdapter.setNewData(mCurrentPosition.getFabricCheckRecordProblemList());
        mPositionEt.setText(mCurrentPosition.getProblemPosition());
    }

    /*
     * 点击下一条
     * */
    private void onNextDidClick() {
        int index = mPisitionList.indexOf(mCurrentPosition);
        if (index == mPisitionList.size() - 1) {
            showToast("已经是最后一条了");
            return;
        }
        mCurrentPosition.setFabricCheckRecordProblemList(mAdapter.getData());
        index +=1;
        mCurrentPosition = mPisitionList.get(index);
        mAdapter.setNewData(mCurrentPosition.getFabricCheckRecordProblemList());
        mPositionEt.setText(mCurrentPosition.getProblemPosition());
    }

    @Override
    public void onImageDidClick(int position) {
        PhotoBrowse.with(FabricCheckProblemBrowseActivity.this)
                .showType(ShowType.MULTIPLE_URL)
                .url(mAdapter.getData().get(position).getImages())
                .position(0)//初始预览位置 默认0
                .show();
    }
}
