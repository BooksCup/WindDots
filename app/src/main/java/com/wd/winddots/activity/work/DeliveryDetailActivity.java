package com.wd.winddots.activity.work;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.wd.winddots.GlideOptions;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.detail_goods_recycler)
    RecyclerView goodsRecyclerView;

    DetailGoodsInfoAdapter detailGoodsInfoAdapter;

    List<GoodsInfoBean> goodsInfoBeanList;

    @BindView(R.id.detail_orders_recycler)
    RecyclerView detailOrdersRecyclerView;

    DetailRelatedOrderAdapter detailRelatedOrderAdapter;

    List<RelatedOrderBean> relatedOrderBeanList;

    @BindView(R.id.factory_recycler)
    RecyclerView factoryRecyclerView;

    FactoryAdapter factoryAdapter;

    List<FactoryBean> factoryBeanList;

    @BindView(R.id.iv_contact_add)
    ImageView ivContactAdd;


    @BindView(R.id.iv_reviewer_add)
    ImageView ivReviewerAdd;



    @BindView(R.id.detail_pics)
    RecyclerView picsRecyclerView;

    PicsAdapter picsAdapter;

    List<PicBean> picBeans;

    @Override
    public int getContentView() {
        return R.layout.activity_delivery_detail;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager1 = new  LinearLayoutManager(this);
        goodsRecyclerView.setLayoutManager(layoutManager1);
        goodsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        goodsInfoBeanList = new ArrayList<>();
        detailGoodsInfoAdapter = new DetailGoodsInfoAdapter(mContext,goodsInfoBeanList);
        goodsRecyclerView.setAdapter(detailGoodsInfoAdapter);


        relatedOrderBeanList = new ArrayList<>();
        LinearLayoutManager layoutManager2 = new  LinearLayoutManager(this);
        detailOrdersRecyclerView.setLayoutManager(layoutManager2);
        detailOrdersRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        detailRelatedOrderAdapter = new DetailRelatedOrderAdapter(this);
        detailOrdersRecyclerView.setAdapter(detailRelatedOrderAdapter);

        factoryBeanList = new ArrayList<>();
        LinearLayoutManager layoutManager3 = new  LinearLayoutManager(this);
        factoryRecyclerView.setLayoutManager(layoutManager3);
        factoryRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        factoryAdapter = new FactoryAdapter(this);
        factoryRecyclerView.setAdapter(factoryAdapter);

        ivContactAdd.setVisibility(View.GONE);
        ivReviewerAdd.setVisibility(View.GONE);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
        picsRecyclerView.setLayoutManager(gridLayoutManager);
       // picsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        picsAdapter = new PicsAdapter(this);
        picsRecyclerView.setAdapter(picsAdapter);
    }

    @Override
    public void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryDetailActivity.this.finish();
            }
        });

        picsAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                List<PicBean> picBeanList = picsAdapter.getList();
                if (picBeanList == null) picBeanList = new ArrayList<>();
                if (position == picBeanList.size()-1){
                    try {
                        EasyPhotos.createAlbum(DeliveryDetailActivity.this,true,GlideEngine.getInstance())//参数说明：上下文，是否显示相机按钮，[配置Glide为图片加载引擎](https://github.com/HuanTanSheng/EasyPhotos/wiki/12-%E9%85%8D%E7%BD%AEImageEngine%EF%BC%8C%E6%94%AF%E6%8C%81%E6%89%80%E6%9C%89%E5%9B%BE%E7%89%87%E5%8A%A0%E8%BD%BD%E5%BA%93)
                                .setFileProviderAuthority("com.wd.winddots.fileprovider")//参数说明：见下方`FileProvider的配置`
                                .start(101);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        //出库物品
        GoodsInfoBean goodsInfoBean1 = new GoodsInfoBean();
        goodsInfoBean1.goodColor = "蓝色";
        goodsInfoBean1.goodSize = "l";
        goodsInfoBean1.deliveryNum = 20;
        goodsInfoBean1.checkNum =20 ;

        goodsInfoBeanList.add(goodsInfoBean1);

        GoodsInfoBean goodsInfoBean2 = new GoodsInfoBean();
        goodsInfoBean2.goodColor = "黄色";
        goodsInfoBean2.goodSize = "l";
        goodsInfoBean2.deliveryNum = 20;
        goodsInfoBean2.checkNum =20 ;

        goodsInfoBeanList.add(goodsInfoBean2);

        detailGoodsInfoAdapter.setList(goodsInfoBeanList);


        //相关订单
        RelatedOrderBean relatedOrderBean = new RelatedOrderBean();
        relatedOrderBean.orderId = "111";
        relatedOrderBean.orderName = "定制上衣（18000000000）";
        relatedOrderBean.iconUrl = "";
        relatedOrderBean.company="南京裁缝铺网络科技";
        relatedOrderBean.num = "200/300件";
        relatedOrderBean.orderType = "DFA#5799款";
        relatedOrderBean.orderNo = "#200000002000";
        relatedOrderBean.orderDate = "2021-02-27";
        List<OrderInfoBean> infoBeanList = new ArrayList<>();
        OrderInfoBean orderInfoBean1 = new OrderInfoBean();
        orderInfoBean1.orderColor = "红色";
        orderInfoBean1.orderSize = "xxl";
        orderInfoBean1.checkNum = 20;
        orderInfoBean1.deliveryNum = 10;
        infoBeanList.add(orderInfoBean1);

        OrderInfoBean orderInfoBean2 = new OrderInfoBean();
        orderInfoBean2.orderColor = "黑色";
        orderInfoBean2.orderSize = "xxl";
        orderInfoBean2.checkNum = 20;
        orderInfoBean2.deliveryNum = 10;
        infoBeanList.add(orderInfoBean2);

        relatedOrderBean.beanList = infoBeanList;
        relatedOrderBeanList.add(relatedOrderBean);

        detailRelatedOrderAdapter.setList(relatedOrderBeanList);

        //出库仓库
        FactoryBean factoryBean = new FactoryBean();
        factoryBean.factoryName = "南京泽冠->南京泽冠四楼->货架A001";
        factoryBean.personName="老周";
        factoryBean.phoneNum ="13800000000" ;
        factoryBean.factoryAddress = "南京市江宁区庄排路109号";
        List<FactoryInfoBean> list = new ArrayList<>();
        FactoryInfoBean infoBean = new FactoryInfoBean();
        infoBean.color = "红色";
        infoBean.size = "xxxl";
        infoBean.factoryNum = 200;
        infoBean.deliveryNum = 20;
        infoBean.allocationNum = 30;
        list.add(infoBean);
        factoryBean.beanList = list;
        factoryBeanList.add(factoryBean);

        factoryAdapter.setList(factoryBeanList);


        //图片
        picBeans = new ArrayList<>();
        PicBean picBean = new PicBean();
        picBeans.add(picBean);
        picsAdapter.setList(picBeans);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK) {
            //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
            ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
            if (null != resultPhotos){
                for (int i=0;i<resultPhotos.size();i++){
                    PicBean picBean = new PicBean();
                    picBean.picUrl = resultPhotos.get(i).path;
                    picBean.picId = i+"";
                    picBeans.add(0,picBean);
                }
                picsAdapter.setList(picBeans);
            }
            Log.d("chenld","photo="+resultPhotos.toString());
        }
    }
}
