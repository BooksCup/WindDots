package com.wd.winddots.desktop.list.goods.fragment;

import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.desktop.list.goods.adapter.QuoteRecordAdapter;
import com.wd.winddots.desktop.list.goods.bean.QuoteRecordBean;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: QuoteRecordFragment
 * Author: 郑
 * Date: 2020/7/28 3:09 PM
 * Description:报价记录
 */
public class QuoteRecordFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {


    private CompositeSubscription compositeSubscription;
    private ElseDataManager dataManager;

    private int page = 1;
    private int pageSize = 10;

    private String mGoodsId;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private QuoteRecordAdapter mAdapter;

    private List<QuoteRecordBean.QuoteRecordItem> mDataSource = new ArrayList<>();

    public static BaseFragment newInstance(String goodsId) {
        QuoteRecordFragment frament = new QuoteRecordFragment();
        frament.compositeSubscription = new CompositeSubscription();
        frament.dataManager = new ElseDataManager();
        frament.mGoodsId = goodsId;
        return frament;
    }


    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_goods_detail_quote_record;
    }

    @Override
    public void initView() {
        mRecyclerView = mView.findViewById(R.id.fragemnt_mine_attendance_rlist);
        mSwipeRefreshLayout = mView.findViewById(R.id.fragemnt_mine_attendance_srfl);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mAdapter = new QuoteRecordAdapter(R.layout.item_goods_quote_record, mDataSource);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        mAdapter.setOnLoadMoreListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setEnableLoadMore(true);
    }


    @Override
    public void onLoadMoreRequested() {
        Log.e("xxxx","onLoadMoreRequested");
        page +=1;
        getData();
    }

    @Override
    public void onRefresh() {
        Log.e("xxxx","onRefresh");
        page = 1;
        mSwipeRefreshLayout.setRefreshing(true);
        mAdapter.setEnableLoadMore(true);
        getData();
    }





    @Override
    public void initData() {
        mSwipeRefreshLayout.setRefreshing(true);
        getData();
    }


    private void getData() {
        Map data = new HashMap();
        data.put("enterpriseId", SpHelper.getInstance(mContext).getEnterpriseId());
        data.put("goodsId", mGoodsId);
        data.put("deleteStatus", "0");
        data.put("offerType", "");
//        data.put("sort", "desc");
//        data.put("sortSearch", "tq.create_time");
//        data.put("groupFlag", 0);
//        data.put("exchangeGroupFlag", 0);
        RequestBody requestBody = Utils.map2requestBody(data);
        compositeSubscription.add(dataManager.getGoodsQuoteRecordList(requestBody, page, pageSize).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net666", String.valueOf(e));
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(String s) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Log.e("net666", s);
                        Gson gson = new Gson();
                        QuoteRecordBean bean = gson.fromJson(s, QuoteRecordBean.class);
                        if (page == 1){
                            mDataSource.clear();
                        }
                        List<QuoteRecordBean.QuoteRecordItem> list = bean.getData();
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        for (int n = 0; n < list.size(); n++) {
                            QuoteRecordBean.QuoteRecordItem item = list.get(n);
                            String quotePrice = mContext.getString(R.string.goods_quote_record_price) + ": ";
                            quotePrice = quotePrice + Utils.numberNullOrEmpty(item.getOfferPrice()) + Utils.getCurrencyByString(mContext, item.getCurrency());
                            String quotePerson = mContext.getString(R.string.goods_quote_record_person) + ": ";
                            quotePerson = quotePerson + Utils.nullOrEmpty(item.getOfferAgent());
                            String time = mContext.getString(R.string.goods_quote_record_date) + ": ";
                            if (!StringUtils.isNullOrEmpty(item.getOfferTime()) && item.getOfferTime().length() > 10) {
                                time = time + item.getOfferTime().substring(0, 10);
                            }

                            item.setQuotePrice(quotePrice);
                            item.setQuotePerson(quotePerson);
                            item.setQuoteTime(time);
                        }
                        if (mDataSource.size() + bean.getData().size() <= bean.getTotalCount()){
                            mDataSource.addAll(bean.getData());
                        }
                        mAdapter.notifyDataSetChanged();
                        mAdapter.loadMoreComplete();
                        if (mDataSource.size() >= bean.getTotalCount()) {
                            mAdapter.setEnableLoadMore(false);
                        }
                    }
                })
        );

       
    }
}
