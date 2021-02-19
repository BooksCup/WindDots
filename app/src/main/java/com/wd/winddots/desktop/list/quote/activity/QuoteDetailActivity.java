package com.wd.winddots.desktop.list.quote.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseActivity;
import com.wd.winddots.desktop.list.quote.adapter.QuoteRecordAdapter;
import com.wd.winddots.desktop.list.quote.adapter.QuoteRecordDetailAdapter;
import com.wd.winddots.desktop.list.quote.bean.QuoteDetailBean;
import com.wd.winddots.desktop.list.quote.bean.QuoteListBean;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderItemDecoration;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderRecyclerView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.Utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: QuoteDetailActivity
 * Author: 郑
 * Date: 2020/7/20 3:37 PM
 * Description: 报价详情
 */
public class QuoteDetailActivity  extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    private CompositeSubscription compositeSubscription;
    private ElseDataManager dataManager;
    public MsgDataManager msgDataManager;
    private QuoteListBean.QuoteListItem quoteListItem;

    private RecyclerView mRecyclerView;
    private QuoteRecordAdapter mAdapter;
    private List<QuoteDetailBean.QuoteDetailItem> mDataSource = new ArrayList<>();
    private int mSelectPosition;


    private PinnedHeaderRecyclerView mPinnedHeaderRecyclerView;
    private QuoteRecordDetailAdapter mRecordDetailAdapter;
    private LinearLayoutManager mLayoutManager;




    @Override
    public int getContentView() {
        return R.layout.activity_quote_detail;
    }

    @Override
    public void initView() {


        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();
        msgDataManager = new MsgDataManager();

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        Gson gson = new Gson();
        if (data != null){
            quoteListItem = gson.fromJson(data, QuoteListBean.QuoteListItem.class);
        }

        ImageView icon = findViewById(R.id.iv_header_icon);
        if (quoteListItem.getQuoteImage() != null) {
            String imageurl =  quoteListItem.getQuoteImage();
            GlideApp.with(mContext).load(imageurl + Utils.OSSImageSize(200)).into(icon);
        }else {
            icon.setImageResource(R.mipmap.default_img);
        }
        TextView tv1 = findViewById(R.id.tv_header1);
        TextView tv2 = findViewById(R.id.tv_header2);
        TextView tv3 = findViewById(R.id.tv_header3);
        TextView tv4 = findViewById(R.id.tv_header4);
        TextView tv5 = findViewById(R.id.tv_header5);
        TextView tv6 = findViewById(R.id.tv_header6);
        tv1.setText(Utils.nullOrEmpty(quoteListItem.getMaterialNo()));
        tv2.setText(quoteListItem.getQuotePrice());
        tv3.setText(quoteListItem.getQuoteNumber());
        tv4.setText(quoteListItem.getQuoteTime());
        tv5.setText(mContext.getString(R.string.quote_detail_to_enterprise) + ": " + Utils.nullOrEmpty(quoteListItem.getToEnterpriseName()));
        tv6.setText(quoteListItem.getQuoteRate());
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRecyclerView = findViewById(R.id.rlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new QuoteRecordAdapter(R.layout.item_quote_detail, mDataSource);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);


        mPinnedHeaderRecyclerView = findViewById(R.id.rightlist);
        mPinnedHeaderRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(mContext));
        mPinnedHeaderRecyclerView.addItemDecoration(new PinnedHeaderItemDecoration());
        mRecordDetailAdapter = new QuoteRecordDetailAdapter();
        mRecordDetailAdapter.setContext(mContext);
        mPinnedHeaderRecyclerView.setAdapter(mRecordDetailAdapter);



    }

    @Override
    public void initListener() {
        mPinnedHeaderRecyclerView.setOnPinnedHeaderClickListener(new PinnedHeaderRecyclerView.OnPinnedHeaderClickListener() {
            @Override
            public void onPinnedHeaderClick(int adapterPosition) {
                mRecordDetailAdapter.switchExpand(adapterPosition);
                //标题栏被点击之后，滑动到指定位置
                mLayoutManager.scrollToPositionWithOffset(adapterPosition, 0);
            }
        });
    }

    @Override
    public void initData() {
        getData();
    }

    private void getData(){
        compositeSubscription.add(dataManager.getQuoteDetail(quoteListItem.getId()).
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
                        Gson gson = new Gson();
                        QuoteDetailBean detailBean = gson.fromJson(s,QuoteDetailBean.class);

                        List<QuoteDetailBean.QuoteDetailItem> list = detailBean.getData();
                        if (list == null){
                            list = new ArrayList<>();
                        }
                        for (int i = 0;i < list.size();i++){
                            QuoteDetailBean.QuoteDetailItem item = list.get(i);
                            String time = Utils.nullOrEmpty(item.getCreateTime());
                            if (time.length() > 10){
                                time = time.substring(0,10);
                            }else {
                                time = "";
                            }
                            String title1 = time + "(" + Utils.nullOrEmpty(item.getGoodsCount()) + quoteListItem.getQuoteGoodsInfo().getGoodsUnit() +")";
                            NumberFormat nf = NumberFormat.getNumberInstance();
                            nf.setMaximumFractionDigits(2);
                            String productionCost = Utils.numberNullOrEmpty(item.getProductionCost());//String.valueOf(nf.format());
                            Float productionCostFloat = Float.parseFloat(productionCost);
                            String productionCostS = String.valueOf(nf.format(productionCostFloat));
                            String title2 = "生产成本: " + productionCostS + "美元";
                            String tradeCost = Utils.numberNullOrEmpty(item.getTradeCost());
                            Float tradeCostFloat = Float.parseFloat(tradeCost);
                            String tradeCostS = String.valueOf(nf.format(tradeCostFloat));
                            String title3 = "生产成本: " + tradeCostS + "美元";
                            String title4 = "报价: " + Utils.numberNullOrEmpty(item.getQuotedPrice()) + "美元";
                            String grossProfitRate = item.getGrossProfitRate() == null ? "0" : item.getGrossProfitRate();
                            String grossProfitRateS = nf.format(Float.parseFloat(grossProfitRate) * 100) + "%";
                            String title5 = "毛利: " + grossProfitRateS + "," + Utils.numberNullOrEmpty(item.getGrossProfit()) + "美元";
                            item.setTitle1(title1);
                            item.setTitle2(title2);
                            item.setTitle3(title3);
                            item.setTitle4(title4);
                            item.setTitle5(title5);

                            List<ExpandGroupItemEntity<QuoteDetailBean.QuoteDetailGroup, QuoteDetailBean.QuoteMaterialItem>> dataList = new ArrayList<>();
                            ExpandGroupItemEntity<QuoteDetailBean.QuoteDetailGroup, QuoteDetailBean.QuoteMaterialItem> priceItem = new ExpandGroupItemEntity<>();
                            QuoteDetailBean.QuoteDetailGroup group1 = new QuoteDetailBean.QuoteDetailGroup();
                            group1.setTitle(mContext.getString(R.string.quote_detail_price));
                            List<QuoteDetailBean.QuoteMaterialItem> list1 = new ArrayList<>();
                            String processCostJson = item.getProcessCostJson();
                            try {
                                List<QuoteDetailBean.QuoteMaterialItem> temp = gson.fromJson(processCostJson, new TypeToken<List<QuoteDetailBean.QuoteMaterialItem>>() {
                                                            }.getType());
                                for (int m = 0;m < temp.size();m++){
                                    QuoteDetailBean.QuoteMaterialItem materialItem = temp.get(m);
                                    if (!StringUtils.isNullOrEmpty(materialItem.getTitle())){
                                        materialItem.setDataType("price");
                                        list1.add(materialItem);
                                    }
                                }
                            }catch (Exception e){
                            }
                            priceItem.setParent(group1);
                            priceItem.setChildList(list1);
                            priceItem.setExpand(true);


                            ExpandGroupItemEntity<QuoteDetailBean.QuoteDetailGroup, QuoteDetailBean.QuoteMaterialItem> materialItem = new ExpandGroupItemEntity<>();
                            QuoteDetailBean.QuoteDetailGroup group2 = new QuoteDetailBean.QuoteDetailGroup();
                            group2.setTitle(mContext.getString(R.string.quote_detail_material));
                            materialItem.setParent(group2);
                            materialItem.setChildList(item.getQuoteMaterialList());
                            materialItem.setExpand(true);

                            ExpandGroupItemEntity<QuoteDetailBean.QuoteDetailGroup, QuoteDetailBean.QuoteMaterialItem> costItem = new ExpandGroupItemEntity<>();
                            QuoteDetailBean.QuoteDetailGroup group3 = new QuoteDetailBean.QuoteDetailGroup();
                            group3.setTitle(mContext.getString(R.string.quote_detail_cost));
                            List<QuoteDetailBean.QuoteMaterialItem> list3 = new ArrayList<>();
                            String tradeCostJson = item.getTradeCostJson();
                            try {
                                List<QuoteDetailBean.QuoteMaterialItem> temp = gson.fromJson(tradeCostJson, new TypeToken<List<QuoteDetailBean.QuoteMaterialItem>>() {
                                }.getType());
                                for (int m = 0;m < temp.size();m++){
                                    QuoteDetailBean.QuoteMaterialItem item1 = temp.get(m);
                                    if (!StringUtils.isNullOrEmpty(item1.getTitle())){
                                        item1.setDataType("cost");
                                        list3.add(item1);
                                    }
                                }
                            }catch (Exception e){
                            }
                            costItem.setParent(group3);
                            costItem.setChildList(list3);
                            costItem.setExpand(true);
                            dataList.add(priceItem);
                            dataList.add(materialItem);
                            dataList.add(costItem);
                            item.setDataList(dataList);
                            if (i == 0){
                                mSelectPosition = 0;
                                item.setSelect(true);
                                mRecordDetailAdapter.setData(dataList);
                            }
                        }
                        mDataSource.addAll(detailBean.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                })
        );
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if (mSelectPosition == position){
            return;
        }

        mDataSource.get(mSelectPosition).setSelect(false);
        QuoteDetailBean.QuoteDetailItem item = mDataSource.get(position);
        item.setSelect(true);
        mSelectPosition = position;
        mRecordDetailAdapter.setData(item.getDataList());
        mAdapter.notifyDataSetChanged();
    }
}
