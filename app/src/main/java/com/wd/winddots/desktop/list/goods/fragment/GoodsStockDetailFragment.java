package com.wd.winddots.desktop.list.goods.fragment;

import android.util.Log;

import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.desktop.list.goods.adapter.GoodsStockAdapter;
import com.wd.winddots.desktop.list.goods.bean.GoodsStockBean;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderItemDecoration;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderRecyclerView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.Utils;


import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: GoodsStockDetailFragment
 * Author: 郑
 * Date: 2020/7/29 10:02 AM
 * Description: 物品库存详情
 */
public class GoodsStockDetailFragment extends BaseFragment {

    private CompositeSubscription compositeSubscription;
    private ElseDataManager dataManager;

    private String mGoodsId;

    private PinnedHeaderRecyclerView mPinnedHeaderRecyclerView;
    private GoodsStockAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;


    public static BaseFragment newInstance(String goodsId) {
        GoodsStockDetailFragment frament = new GoodsStockDetailFragment();
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
        return R.layout.fragment_goods_detail_stock;
    }

    @Override
    public void initView() {
        mPinnedHeaderRecyclerView = mView.findViewById(R.id.rlist);
        mPinnedHeaderRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(mContext));
        mPinnedHeaderRecyclerView.addItemDecoration(new PinnedHeaderItemDecoration());
        mAdapter = new GoodsStockAdapter();
        //mRecordDetailAdapter.setContext(mContext);
        mPinnedHeaderRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        mPinnedHeaderRecyclerView.setOnPinnedHeaderClickListener(new PinnedHeaderRecyclerView.OnPinnedHeaderClickListener() {
            @Override
            public void onPinnedHeaderClick(int adapterPosition) {
                mAdapter.switchExpand(adapterPosition);
                //标题栏被点击之后，滑动到指定位置
                mLayoutManager.scrollToPositionWithOffset(adapterPosition, 0);
            }
        });
    }

    @Override
    public void initData() {
        getData();
    }


    private void getData() {
        compositeSubscription.add(dataManager.getGoodsStockDetail(mGoodsId).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Observer<String>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("net666", String.valueOf(e));
                            }

                            @Override
                            public void onNext(String s) {

                                Log.e("net666", s);
                                Gson gson = new Gson();
                                GoodsStockBean bean = gson.fromJson(s, GoodsStockBean.class);
                                if (bean.getCode() != 0) {
                                    return;
                                }
                                GoodsStockBean.Goods goods = bean.getData().getGoods();
                                List<ExpandGroupItemEntity<GoodsStockBean.StockRecordItem, GoodsStockBean.StockItem>> dataList = new ArrayList<>();
                                ExpandGroupItemEntity<GoodsStockBean.StockRecordItem, GoodsStockBean.StockItem> firstItem = new ExpandGroupItemEntity<>();
                                GoodsStockBean.StockRecordItem header = new GoodsStockBean.StockRecordItem();
                                header.setViewType("listHeader");
                                GoodsStockBean.StockItem titleItem = new GoodsStockBean.StockItem();
                                titleItem.setName1(bean.getData().getGoods().getX());
                                titleItem.setName2(bean.getData().getGoods().getY());
                                titleItem.setName5("数量");
                                titleItem.setViewType("listHeader");

                                int type = 0;
                                if (!StringUtils.isNullOrEmpty(goods.getY())) {
                                    titleItem.setType(2);
                                    type = 2;//有两个规格
                                } else {
                                    titleItem.setType(1);
                                    type = 1;//有一个规格
                                }

                                List<GoodsStockBean.StockItem> stock = bean.getData().getStock();
                                if (stock == null) {
                                    stock = new ArrayList<>();
                                }
                                for (int i = 0; i < stock.size(); i++) {
                                    GoodsStockBean.StockItem item = stock.get(i);
                                    item.setName1(item.getX());
                                    item.setName2(item.getY());
                                    item.setType(type);
                                    item.setName5(Utils.numberNullOrEmpty(item.getResidualNumber()));
                                    item.setViewType("listHeader");
                                }
                                bean.getData().getStock().add(0, titleItem);
                                firstItem.setExpand(true);
                                firstItem.setChildList(bean.getData().getStock());
                                firstItem.setParent(header);
                                if (stock.size()>1){
                                    dataList.add(firstItem);
                                }

                                List<GoodsStockBean.StockRecordItem> stockRecord = bean.getData().getStockRecord();
                                if (stockRecord == null){
                                    stockRecord = new ArrayList<>();
                                }
                                for (int i = 0;i < stockRecord.size();i++){
                                    GoodsStockBean.StockRecordItem stockRecordItem = stockRecord.get(i);
                                    ExpandGroupItemEntity<GoodsStockBean.StockRecordItem, GoodsStockBean.StockItem> group = new ExpandGroupItemEntity<>();
                                    group.setExpand(false);
                                    group.setParent(stockRecordItem);
                                    List<GoodsStockBean.StockItem> stockItemList = stockRecordItem.getStockApplicationInRecordList();
                                    if (stockItemList == null){
                                        stockItemList = new ArrayList<>();
                                    }
                                    GoodsStockBean.StockItem titleItem1 = new GoodsStockBean.StockItem();
                                    titleItem1.setType(type);
                                    titleItem1.setName1("入库时间");
                                    titleItem1.setName2(goods.getX());
                                    titleItem1.setName3(goods.getY());
                                    titleItem1.setName4("库存数量");
                                    titleItem1.setName5("入库数量");
                                    for (int n = 0;n < stockItemList.size();n++){
                                        GoodsStockBean.StockItem item = stockItemList.get(n);
                                        item.setType(type);
                                        item.setName1(Utils.nullOrEmpty(item.getCreateTime()));
                                        item.setName2(Utils.nullOrEmpty(item.getX()));
                                        item.setName3(Utils.nullOrEmpty(item.getY()));
                                        item.setName4(Utils.numberNullOrEmpty(item.getResidualNumber()));
                                        item.setName5(Utils.numberNullOrEmpty(item.getCount()));
                                    }
                                    stockItemList.add(0,titleItem1);
                                    group.setChildList(stockItemList);
                                    dataList.add(group);
                                }
                                mAdapter.setData(dataList);
                            }
                        })
        );
    }
}
