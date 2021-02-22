package com.wd.winddots.adapter.select;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.winddots.R;
import com.wd.winddots.entity.RelatedCompany;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 往来单位
 *
 * @author zhou
 */
public class RelatedCompanyAdapter extends BaseQuickAdapter<RelatedCompany, BaseViewHolder> {

    private String mKeyword;

    public void setKeyword(String keyword) {
        this.mKeyword = keyword;
    }

    public RelatedCompanyAdapter(int layoutResId, @Nullable List<RelatedCompany> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RelatedCompany item) {

        String legalPersonName;
        if (TextUtils.isEmpty(item.getLegalPersonName())) {
            legalPersonName = "法人:暂无";
        } else {
            legalPersonName = "法人:" + item.getLegalPersonName();
        }

        String address;
        if (TextUtils.isEmpty(item.getAddress())) {
            address = "地址:暂无";
        } else {
            address = "地址:" + item.getAddress();
        }

        helper.setText(R.id.tv_company_name, item.getName())
                .setText(R.id.tv_legal_person_name, legalPersonName)
                .setText(R.id.tv_address, address);

        SimpleDraweeView mRelatedCompanyPhotoSdv = helper.getView(R.id.sdv_related_company_logo);
        if (!TextUtils.isEmpty(item.getLogo())) {
            mRelatedCompanyPhotoSdv.setImageURI(Uri.parse(item.getLogo()));
        } else {
            mRelatedCompanyPhotoSdv.setImageResource(R.mipmap.icon_default_goods);
        }

    }
}
