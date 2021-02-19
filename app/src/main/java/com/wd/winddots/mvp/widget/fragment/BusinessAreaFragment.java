package com.wd.winddots.mvp.widget.fragment;



import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.bean.resp.BusinessBean;
import com.wd.winddots.business.activity.MaterialsPriceActivity;
import com.wd.winddots.business.activity.RateCurrencyActivity;
import com.wd.winddots.business.view.ExchangeRateHeader;
import com.wd.winddots.mvp.widget.adapter.rv.BusinessAreaAdapter;
import com.wd.winddots.mvp.view.BusinessareaView;
import com.wd.winddots.mvp.presenter.impl.BusinessAreaPresenterImpl;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.SyLinearLayoutManager;
import com.wd.winddots.view.BottomSearchBarView;

import java.util.ArrayList;
import java.util.List;

public class BusinessAreaFragment extends BaseFragment<BusinessareaView, BusinessAreaPresenterImpl>
        implements SwipeRefreshLayout.OnRefreshListener,
        BusinessareaView , BaseQuickAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;

    private BottomSearchBarView mBottomSearchBarView;

    private ExchangeRateHeader mHeader;

    private List<BusinessBean.MaterialsPrice> mBusinessBeanList = new ArrayList<>();
    private BusinessAreaAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static BusinessAreaFragment newInstance() {
        BusinessAreaFragment fragment = new BusinessAreaFragment();
        return fragment;
    }



    @Override
    public BusinessAreaPresenterImpl initPresenter() {
        return new BusinessAreaPresenterImpl();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_businessarea;
    }

    @Override
    public void initView() {
        mRecyclerView =  mView.findViewById(R.id.fragment_businessarea_rv);
        mBottomSearchBarView = mView.findViewById(R.id.fragment_businessarea_searchbar);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

//        mBusinessBeanList.add(new BusinessBean.MaterialsPrice());
//        mBusinessBeanList.add(new BusinessBean.MaterialsPrice());
//        mBusinessBeanList.add(new BusinessBean.MaterialsPrice());
//        mBusinessBeanList.add(new BusinessBean.MaterialsPrice());
//        mBusinessBeanList.add(new BusinessBean.MaterialsPrice());
//        mBusinessBeanList.add(new BusinessBean.MaterialsPrice());
//        mBusinessBeanList.add(new BusinessBean.MaterialsPrice());

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        mHeader = new ExchangeRateHeader(mContext);
        mAdapter = new BusinessAreaAdapter(R.layout.item_business,mBusinessBeanList);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setHeaderView(mHeader);
        mHeader.setMaterialsMoreDidClickListener(new ExchangeRateHeader.HeaderMaterialsMoreDidClickListener() {
            @Override
            public void materialsMoreDidClick() {
                Intent intent = new Intent(mContext, MaterialsPriceActivity.class);
//                Gson gson = new Gson();
//                String jsonS = gson.toJson(item);
//                intent.putExtra("data", jsonS);
                startActivity(intent);
            }
        });

        mSwipeRefreshLayout = mView.findViewById(R.id.srl);
    }

    @Override
    public void initListener() {
        presenter.loadBusinessAreaData();
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        presenter.loadBusinessAreaData();
    }


    @Override
    public void onLoadBusinessAreaDataSuccess(BusinessBean bean) {
        mBusinessBeanList.clear();
        mBusinessBeanList.addAll(bean.getWeavePriceList());
        mAdapter.notifyDataSetChanged();
        mHeader.setData(bean.getHotExchange());
    }

    @Override
    public void onLoadBusinessAreaDataError(String msg) {
        mSwipeRefreshLayout.setRefreshing(false);
        showToast(mContext.getString(R.string.toast_loading_error));
    }

    @Override
    public void onLoadBusinessAreaDataComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        BusinessBean.MaterialsPrice item = mBusinessBeanList.get(position);
        Intent intent = null;
//        if ("0".equals(item.getMomentsType())){//原材料价格
////
////            if (item.getList() == null || item.getList().size() == 0){
////                return;
////            }
//            intent  = new Intent(mContext, MaterialsPriceActivity.class);
//            Gson gson = new Gson();
//            String jsonS = gson.toJson(item);
//            intent.putExtra("data", jsonS);
//            startActivity(intent);
//        }else if ("1".equals(item.getMomentsType())){
//            intent  = new Intent(mContext, RateCurrencyActivity.class);
//            Gson gson = new Gson();
//            String jsonS = gson.toJson(item);
//            intent.putExtra("data", jsonS);
//            startActivity(intent);
//        }
    }


}
