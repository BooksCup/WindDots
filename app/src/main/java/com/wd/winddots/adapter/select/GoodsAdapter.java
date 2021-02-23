package com.wd.winddots.adapter.select;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.winddots.R;
import com.wd.winddots.entity.Goods;
import com.wd.winddots.utils.CommonUtil;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 物品
 *
 * @author zhou
 */
public class GoodsAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {

    private String mKeyword;

    public GoodsAdapter(int layoutResId, @Nullable List<Goods> data) {
        super(layoutResId, data);
    }

    public void setKeyword(String keyword) {
        this.mKeyword = keyword;
    }

    @Override
    protected void convert(BaseViewHolder helper, Goods item) {

        String goodsInfo = item.getGoodsName() + "(" + item.getGoodsNo() + ")";
        String stockInfo = item.getStockNum() + item.getGoodsUnit();
        helper.setText(R.id.tv_goods_info, goodsInfo)
                .setText(R.id.tv_stock_num, stockInfo);

        TextView mAttr1Tv = helper.getView(R.id.tv_attr1);
        TextView mAttr2Tv = helper.getView(R.id.tv_attr2);

        List<String> attrList = CommonUtil.attrJsonToAttrStrList(item.getAttrList());
        if (null != attrList) {
            if (attrList.size() == 0) {
                mAttr1Tv.setVisibility(View.INVISIBLE);
                mAttr2Tv.setVisibility(View.INVISIBLE);
            } else if (attrList.size() == 1) {
                mAttr1Tv.setVisibility(View.VISIBLE);
                mAttr2Tv.setVisibility(View.INVISIBLE);
                mAttr1Tv.setText(attrList.get(0));
            } else if (attrList.size() >= 2) {
                mAttr1Tv.setVisibility(View.VISIBLE);
                mAttr2Tv.setVisibility(View.VISIBLE);
                mAttr1Tv.setText(attrList.get(0));
                mAttr2Tv.setText(attrList.get(1));
            }
        }

        SimpleDraweeView mGoodsPhotoSdv = helper.getView(R.id.sdv_goods_photo);
        String goodsPhoto = CommonUtil.getFirstPhotoFromJsonList(item.getGoodsPhotos());
        if (!TextUtils.isEmpty(goodsPhoto)) {
            mGoodsPhotoSdv.setImageURI(Uri.parse(goodsPhoto));
        } else {
            mGoodsPhotoSdv.setImageResource(R.mipmap.icon_default_goods);
        }

    }

}
