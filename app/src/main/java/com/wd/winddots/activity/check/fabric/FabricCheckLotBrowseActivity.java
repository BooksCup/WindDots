package com.wd.winddots.activity.check.fabric;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kevin.photo_browse.PhotoBrowse;
import com.kevin.photo_browse.ShowType;
import com.kevin.photo_browse.callabck.ClickCallback;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.adapter.check.fabric.FabricCheckLotBrowseImageAdapter;
import com.wd.winddots.adapter.check.fabric.FabricCheckTaskLotBrowseAdapter;
import com.wd.winddots.adapter.check.fabric.FabricCheckTaskLotProcessAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.FabricCheckTaskLot;
import com.wd.winddots.entity.FabricCheckTaskRecord;
import com.wd.winddots.entity.ProblemImage;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: FabricCheckLotBrowseActivity
 * Author: 郑
 * Date: 2021/3/1 11:39 AM
 * Description:
 */
public class FabricCheckLotBrowseActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    private VolleyUtil mVolleyUtil;

    @BindView(R.id.rv_check)
    RecyclerView mCheckRv;
    private FabricCheckTaskLotBrowseAdapter mAdapter;
    private List<FabricCheckTaskLot> mDataSource = new ArrayList<>();

    @BindView(R.id.rv_images)
    RecyclerView mImageRv;
    private FabricCheckLotBrowseImageAdapter mImageAdapter;
    private List<ProblemImage> mProblemImageList = new ArrayList<>();


    @BindView(R.id.tv_title)
    TextView mTitleTv;





    private String mId;
    private String mGoodsName;
    private String mGoodsNo;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabric_check_lot_browse);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);

        initView();
        getData();

    }

    private void initView(){
        Intent intent = getIntent();
        mId = intent.getStringExtra("data");
        mGoodsName = intent.getStringExtra("goodsName");
        mGoodsNo = intent.getStringExtra("goodsNo");
        mTitleTv.setText(Utils.nullOrEmpty(mGoodsNo) + "(" + Utils.nullOrEmpty(mGoodsName) + ")");
        mAdapter = new FabricCheckTaskLotBrowseAdapter(R.layout.item_fabric_check_task_lot_browse,mDataSource);
        mAdapter.goodsName = mGoodsName;
        mAdapter.goodsNo = mGoodsNo;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCheckRv.setLayoutManager(layoutManager);
        mCheckRv.setAdapter(mAdapter);

        mImageAdapter = new FabricCheckLotBrowseImageAdapter(R.layout.item_fabric_check_lot_browse_image, mProblemImageList);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mImageRv.setLayoutManager(layoutManager1);
        mImageRv.setAdapter(mImageAdapter);
        mImageAdapter.setOnItemClickListener(this);

    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }


    private void getData(){
        String url = Constant.APP_BASE_URL + "fabricCheckRecord/searchAll?checkLotInfoId=" + mId;
        Log.e("net666",url);
        mVolleyUtil.httpGetRequest(url, response -> {
            Log.e("net666",response);
            if (null == response){
                return;
            }
            FabricCheckLot fabricCheckLot = JSON.parseObject(response,FabricCheckLot.class);
            List<FabricCheckTaskLot> list = fabricCheckLot.getFabricQcRecordAllByCheckLIIdVoList();
            mDataSource.addAll(list);
            mAdapter.notifyDataSetChanged();

            mProblemImageList.addAll(fabricCheckLot.getProblemImageClassifyList());
            mImageAdapter.notifyDataSetChanged();

        }, volleyError -> {
            Log.e("net666", String.valueOf(volleyError));
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PhotoBrowse.with(this)
                .showType(ShowType.MULTIPLE_URL)
                .url(mImageAdapter.getData().get(position).getPhotos())
                .title("")//选传，不配置则不显示
                .position(0)//初始预览位置 默认0
                .callback(new ClickCallback() {
                    @Override
                    public void onClick(Activity activity, String url, int position) {
                        super.onClick(activity, url, position);
                        //Toast.makeText(.this, "点击", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLongClick(Activity activity, String url, int position) {
                        super.onLongClick(activity, url, position);
                        //Toast.makeText(MainActivity.this, "长按", Toast.LENGTH_LONG).show();
                    }
                })//点击事件回调，默认点击退出
                .show();
    }

    private static class FabricCheckLot{
        private List<FabricCheckTaskLot> fabricQcRecordAllByCheckLIIdVoList;
        private List<ProblemImage> problemImageClassifyList;

        public List<FabricCheckTaskLot> getFabricQcRecordAllByCheckLIIdVoList() {
            return fabricQcRecordAllByCheckLIIdVoList;
        }

        public void setFabricQcRecordAllByCheckLIIdVoList(List<FabricCheckTaskLot> fabricQcRecordAllByCheckLIIdVoList) {
            this.fabricQcRecordAllByCheckLIIdVoList = fabricQcRecordAllByCheckLIIdVoList;
        }

        public List<ProblemImage> getProblemImageClassifyList() {
            return problemImageClassifyList;
        }

        public void setProblemImageClassifyList(List<ProblemImage> problemImageClassifyList) {
            this.problemImageClassifyList = problemImageClassifyList;
        }
    }



}
