package com.wd.winddots.activity.work;

import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wd.winddots.activity.work.GlideEngine.*;

public class AddDeliveryActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.iv_delivery_add)
    ImageView ivAddBtn;

    @BindView(R.id.iv_delete_btn)
    ImageView ivDelBtn;

    @BindView(R.id.ll_root_info)
    LinearLayout rootInfo;

    @BindView(R.id.ll_delivery_goods_info)
    LinearLayout deliveryGoodsInfo;

    @BindView(R.id.rl_delivery_info)
    RelativeLayout rlInfo;

    @BindView(R.id.iv_expand)
    ImageView ivExpandBtn;

    @BindView(R.id.goods_recycler)
    RecyclerView goodsRecyclerView;

    GoodsInfoAdapter mAdapter;

    @BindView(R.id.orders_recycler)
    RecyclerView ordersRecyclerView;

    RelatedOrderAdapter relatedOrderAdapter;

    @BindView(R.id.add_pic)
    RecyclerView addRecyclerView;

    PicsAdapter picsAdapter;

    @BindView(R.id.iv_add_btn)
    ImageView ivRelatedAddBtn;

    @BindView(R.id.iv_del_btn)
    ImageView ivRelatedDelBtn;

    List<RelatedOrderBean> relatedOrderBeans;

    List<PicBean> picBeans;

    @Override
    public int getContentView() {
        return R.layout.activity_add_delivery;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager1 = new  LinearLayoutManager(this);
        goodsRecyclerView.setLayoutManager(layoutManager1);
        goodsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new GoodsInfoAdapter(this);
        goodsRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager2 = new  LinearLayoutManager(this);
        ordersRecyclerView.setLayoutManager(layoutManager2);
        ordersRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        relatedOrderAdapter = new RelatedOrderAdapter(this);
        ordersRecyclerView.setAdapter(relatedOrderAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
        addRecyclerView.setLayoutManager(gridLayoutManager);
       // addRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        picsAdapter = new PicsAdapter(this);
        addRecyclerView.setAdapter(picsAdapter);
    }

    @Override
    public void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDeliveryActivity.this.finish();
            }
        });

        //出库物品添加按钮事件
        ivAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rootInfo.getVisibility() == View.GONE){
                    rootInfo.setVisibility(View.VISIBLE);
                    ivAddBtn.setVisibility(View.GONE);
                    ivDelBtn.setVisibility(View.VISIBLE);
                }else {
                    rootInfo.setVisibility(View.GONE);
                }
            }
        });

        //出库物品删除按钮事件
        ivDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rootInfo.getVisibility() == View.VISIBLE){
                    rootInfo.setVisibility(View.GONE);
                    ivAddBtn.setVisibility(View.VISIBLE);
                    ivDelBtn.setVisibility(View.GONE);
                }
            }
        });
        //出库物品信息展开事件
        ivExpandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deliveryGoodsInfo.getVisibility()==View.GONE){
                    deliveryGoodsInfo.setVisibility(View.VISIBLE);
                }else {
                    deliveryGoodsInfo.setVisibility(View.GONE);
                }
            }
        });

        //相关订单添加事件
        ivRelatedAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ordersRecyclerView.getVisibility()==View.GONE){
                    ordersRecyclerView.setVisibility(View.VISIBLE);
                }
                RelatedOrderBean relatedOrderBean = new RelatedOrderBean();
                relatedOrderBean.orderId = "11"+relatedOrderBeans.size()+1;
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
                orderInfoBean1.orderNum = 20;
                orderInfoBean1.deliveryNum = 10;
                infoBeanList.add(orderInfoBean1);

                OrderInfoBean orderInfoBean2 = new OrderInfoBean();
                orderInfoBean2.orderColor = "黑色";
                orderInfoBean2.orderSize = "xxl";
                orderInfoBean2.orderNum = 20;
                orderInfoBean2.deliveryNum = 10;
                infoBeanList.add(orderInfoBean2);

                relatedOrderBean.beanList = infoBeanList;

                relatedOrderBeans.add(relatedOrderBean);
                relatedOrderAdapter.setList(relatedOrderBeans);
            }
        });

        //相关订单删除事件
        ivRelatedDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relatedOrderBeans.size()>0){
                    relatedOrderBeans.remove(0);
                }
                relatedOrderAdapter.setList(relatedOrderBeans);
            }
        });

        picsAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                List<PicBean> picBeanList = picsAdapter.getList();
                if (picBeanList == null) picBeanList = new ArrayList<>();
                if (position == picBeanList.size()-1){
                    try {
                        EasyPhotos.createAlbum(AddDeliveryActivity.this,true,GlideEngine.getInstance())//参数说明：上下文，是否显示相机按钮，[配置Glide为图片加载引擎](https://github.com/HuanTanSheng/EasyPhotos/wiki/12-%E9%85%8D%E7%BD%AEImageEngine%EF%BC%8C%E6%94%AF%E6%8C%81%E6%89%80%E6%9C%89%E5%9B%BE%E7%89%87%E5%8A%A0%E8%BD%BD%E5%BA%93)
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
        relatedOrderBeans = new ArrayList<>();

        List<GoodsInfoBean> goodsInfoBeans = new ArrayList<>();
        GoodsInfoBean bean1= new GoodsInfoBean();
        bean1.goodColor = "红色";
        bean1.goodSize = "xxl";
        bean1.goodNum = 20;
        goodsInfoBeans.add(bean1);

        GoodsInfoBean bean2= new GoodsInfoBean();
        bean2.goodColor = "蓝色";
        bean2.goodSize = "xxxl";
        bean2.goodNum = 30;
        goodsInfoBeans.add(bean2);

        mAdapter.setGoodsInfoBeans(goodsInfoBeans);

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
