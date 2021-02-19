package com.wd.winddots.desktop.list.order.fragment;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.desktop.list.goods.adapter.GoodsStockAdapter;
import com.wd.winddots.desktop.list.goods.bean.GoodsStockBean;
import com.wd.winddots.desktop.list.order.adapter.OrderNumberAdapter;
import com.wd.winddots.desktop.list.order.bean.OrderNumberBean;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderItemDecoration;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderRecyclerView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.SpHelper;
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
 * FileName: OrderNumberFragment
 * Author: 郑
 * Date: 2020/8/10 10:41 AM
 * Description: 订单数量
 */
public class OrderNumberFragment extends BaseFragment {

    private CompositeSubscription compositeSubscription;
    private ElseDataManager dataManager;

    private String mOrderId;

    private PinnedHeaderRecyclerView mPinnedHeaderRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private OrderNumberAdapter mAdapter;


    public static BaseFragment newInstance(String orderId) {
        OrderNumberFragment frament = new OrderNumberFragment();
        frament.compositeSubscription = new CompositeSubscription();
        frament.dataManager = new ElseDataManager();
        frament.mOrderId = orderId;
        return frament;
    }

    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_order_number;
    }

    @Override
    public void initView() {
        mPinnedHeaderRecyclerView = mView.findViewById(R.id.rlist);
        mPinnedHeaderRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(mContext));
        mPinnedHeaderRecyclerView.addItemDecoration(new PinnedHeaderItemDecoration());
        mAdapter = new OrderNumberAdapter();
        //mRecordDetailAdapter.setContext(mContext);
        mPinnedHeaderRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        getData();
    }

    private void getData() {
        compositeSubscription.add(dataManager.getOrderNumber(mOrderId).
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
                        OrderNumberBean bean = gson.fromJson(s, OrderNumberBean.class);
                        if (!"0".equals(bean.getCode())) {
                            return;
                        }

                        OrderNumberBean.OrderNumberExt ext = bean.getObj().getExt();
                        if (ext == null) {
                            return;
                        }

                        OrderNumberBean.GoodsObj goods = gson.fromJson(ext.getGoodsJson(), OrderNumberBean.GoodsObj.class);


                        String deliveryTimeJson = bean.getObj().getExt().getDeliveryTimeJson();
                        if (StringUtils.isNullOrEmpty(deliveryTimeJson)) {
                            return;
                        }

                        List<OrderNumberBean.DeliveryTime> deliveryTimeList = gson.fromJson(deliveryTimeJson, new TypeToken<List<OrderNumberBean.DeliveryTime>>() {
                        }.getType());

                        if (deliveryTimeList == null || deliveryTimeList.size() == 0) {
                            return;
                        }

                        List<ExpandGroupItemEntity<OrderNumberBean.DeliveryTime, OrderNumberBean.DeliveryTimeDetailItem>> dataList = new ArrayList<>();
                        ExpandGroupItemEntity<OrderNumberBean.DeliveryTime, OrderNumberBean.DeliveryTimeDetailItem> firstItem = new ExpandGroupItemEntity<>();
                        OrderNumberBean.DeliveryTime header = new OrderNumberBean.DeliveryTime();
                        header.setName1("交货日期");
                        header.setName2("数量");
                        header.setName3("备注");
                        header.setViewType("header");
                        firstItem.setParent(header);
                        firstItem.setChildList(new ArrayList<OrderNumberBean.DeliveryTimeDetailItem>());
                        dataList.add(firstItem);

                        List<OrderNumberBean.GoodsSpecItem> goodsSpecList = goods.getGoodsSpecList();
                        if (goodsSpecList == null || goodsSpecList.size() == 0) {
                            return;
                        }

                        int type = 0;
                        if (!StringUtils.isNullOrEmpty(goods.getY())) {
                            type = 2;//有两个规格
                        } else {
                            type = 1;//有一个规格
                        }

                        for (int i = 0; i < deliveryTimeList.size(); i++) {
                            OrderNumberBean.DeliveryTime deliveryTime = deliveryTimeList.get(i);
                            List<OrderNumberBean.DeliveryTimeDetailItem> deliveryTimeDetailList = deliveryTime.getDeliveryTimeDetailList();
                            for (int m = 0; m < deliveryTimeDetailList.size(); m++) {
                                OrderNumberBean.DeliveryTimeDetailItem deliveryTimeDetailItem = deliveryTimeDetailList.get(m);
                                deliveryTimeDetailItem.setType(type);
                                for (int n = 0; n < goodsSpecList.size(); n++) {
                                    OrderNumberBean.GoodsSpecItem specItem = goodsSpecList.get(n);
                                    if (deliveryTimeDetailItem.getGoodsSpecId().equals(specItem.getId())) {
                                        deliveryTimeDetailItem.setName1(specItem.getX());
                                        if (type == 2) {
                                            deliveryTimeDetailItem.setName2(specItem.getY());
                                            deliveryTimeDetailItem.setName3(Utils.numberNullOrEmpty(deliveryTimeDetailItem.getCount()) + Utils.nullOrEmpty(goods.getGoodsUnit()));
                                        } else {
                                            deliveryTimeDetailItem.setName2(Utils.numberNullOrEmpty(deliveryTimeDetailItem.getCount()) + Utils.nullOrEmpty(goods.getGoodsUnit()));
                                        }
                                    }
                                }
                            }
                            OrderNumberBean.DeliveryTimeDetailItem titleItem = new OrderNumberBean.DeliveryTimeDetailItem();
                            titleItem.setViewType("listHeader");
                            titleItem.setType(type);
                            titleItem.setName1(goods.getX());
                            if (type == 2) {
                                titleItem.setName2(goods.getY());
                                titleItem.setName3("数量");
                            } else {
                                titleItem.setName2("数量");
                            }
                            deliveryTimeDetailList.add(0, titleItem);
                            OrderNumberBean.DeliveryTimeDetailItem itemHeader = new OrderNumberBean.DeliveryTimeDetailItem();
                            itemHeader.setName1(goods.getX());
                            deliveryTime.setName1(Utils.nullOrEmpty(deliveryTime.getDeliveryTime()));
                            deliveryTime.setName2(Utils.numberNullOrEmpty(deliveryTime.getDeliveryCount()) + Utils.nullOrEmpty(goods.getGoodsUnit()));
                            deliveryTime.setName3(Utils.nullOrEmpty(deliveryTime.getRemark()));
                            ExpandGroupItemEntity<OrderNumberBean.DeliveryTime, OrderNumberBean.DeliveryTimeDetailItem> group = new ExpandGroupItemEntity<>();
                            group.setParent(deliveryTime);
                            group.setChildList(deliveryTime.getDeliveryTimeDetailList());
                            deliveryTime.setViewType("listHeader");
                            dataList.add(group);
                        }
                        mAdapter.setData(dataList);
                    }
                })
        );
    }
}
