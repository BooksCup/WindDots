package com.wd.winddots.adapter.select;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.WareHouse;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 仓库
 *
 * @author zhou
 */
public class WareHouseAdapter extends BaseQuickAdapter<WareHouse, BaseViewHolder> {

    private String mKeyword;

    public void setKeyword(String keyword) {
        this.mKeyword = keyword;
    }

    public WareHouseAdapter(int layoutResId, @Nullable List<WareHouse> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WareHouse item) {
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_contact_name, item.getContactName())
                .setText(R.id.tv_contact_phone, item.getContactPhone())
                .setText(R.id.tv_address, item.getAddress());

        TextView mTypeTv = helper.getView(R.id.tv_type);

        // tag
        if (Constant.WAREHOUSE_IS_OWN.equals(item.getIsOwn())) {

            // 内部仓库
            mTypeTv.setText("内部仓");
            mTypeTv.setTextColor(mContext.getResources().getColor(R.color.tag_green));
            mTypeTv.setBackgroundResource(R.drawable.bg_tag_green);

        } else {

            // 外部仓库
            mTypeTv.setText("外部仓");
            mTypeTv.setTextColor(mContext.getResources().getColor(R.color.tag_orange));
            mTypeTv.setBackgroundResource(R.drawable.bg_tag_orange);

        }

    }
}
