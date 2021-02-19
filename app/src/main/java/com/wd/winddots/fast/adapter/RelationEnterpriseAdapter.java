package com.wd.winddots.fast.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.fast.bean.RelationEnterpriseBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: RelationEnterpriseAdapter
 * Author: 郑
 * Date: 2020/5/13 2:47 PM
 * Description:
 */
public class RelationEnterpriseAdapter extends BaseQuickAdapter<RelationEnterpriseBean.Enterprise, BaseViewHolder>
implements BaseQuickAdapter.OnItemClickListener {

    private Context mContext;
    private RelationEnterpriseAccountAdapter mAdapter;
    private OnAccountSelectListener accountSelectListener;
    private RelationEnterpriseBean.Enterprise mData;
    private boolean mAccountSelect;

    public RelationEnterpriseAdapter(int layoutResId, @Nullable List<RelationEnterpriseBean.Enterprise> data,Context context,boolean accountSelect) {
        super(layoutResId, data);
        mContext = context;
        mAccountSelect = accountSelect;
    }

    public void setOnAccountSelectListener(OnAccountSelectListener listener){
      accountSelectListener = listener;
    }

    @Override
    protected void convert(BaseViewHolder helper, final RelationEnterpriseBean.Enterprise item) {

        mData = item;

        String law = "法定代表人: " +  (StringUtils.isNullOrEmpty(item.getOperName()) ? "" : item.getOperName());
        String time = "法定代表人: ";// +  (StringUtils.isNullOrEmpty(item.getOperName()) ? "" : item.getOperName()) ;
        if (!StringUtils.isNullOrEmpty(item.getStartDate()) && item.getStartDate().length() > 10){
            time = time + item.getStartDate().substring(0,10);
        }

        String address = "地址: " +  (StringUtils.isNullOrEmpty(item.getAddress()) ? "" : item.getAddress()) ;



        helper.setText(R.id.item_mine_claiming_enterprise_name,item.getName()).
                setText(R.id.item_mine_claiming_enterprise_law,law).
                setText(R.id.item_mine_claiming_enterprise_time,time).
                setText(R.id.item_mine_claiming_enterprise_address,address);

        final ImageView icon = helper.getView(R.id.item_mine_claiming_enterprise_icon);



        final RecyclerView recyclerView = helper.getView(R.id.item_mine_claiming_enterprise_rlist);

        if (item.isOpen()){
            recyclerView.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.GONE);
        }

        final List<RelationEnterpriseBean.Account> bankList = item.getExchangeEnterpriseAccountList();
        if (bankList == null || bankList.size() == 0){
            recyclerView.setVisibility(View.GONE);
        }else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
            mAdapter = new RelationEnterpriseAccountAdapter(R.layout.item_mine_claiming_relationenterpirse_account,bankList,item);
            recyclerView.setAdapter(mAdapter);

            int dp = 75 * bankList.size();
            int height = Utils.dip2px(mContext, dp);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.getLayoutParams().height = height;


            mAdapter.setOnItemClickListener(this);


        }


        LinearLayout linearLayout = helper.getView(R.id.item_mine_claiming_enterprise_body);

        if (mAccountSelect){
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bankList == null || bankList.size() == 0){
                        return;
                    }
                    if (item.isOpen()){
                        recyclerView.setVisibility(View.GONE);
                        item.setOpen(false);
                        icon.setImageResource(R.mipmap.icon_top);
                    }else {
                        recyclerView.setVisibility(View.VISIBLE);
                        item.setOpen(true);
                        icon.setImageResource(R.mipmap.icon_bottom);
                    }
                }
            });
        }else {
            icon.setVisibility(View.GONE);
        }




    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        RelationEnterpriseAccountAdapter adapter1 = (RelationEnterpriseAccountAdapter) adapter;
        RelationEnterpriseBean.Enterprise enterprise = adapter1.getEnterprise();
        enterprise.setAccountPosition(position);
        if (accountSelectListener != null){
            accountSelectListener.onAccountSelect(enterprise);
        }
    }



    public interface OnAccountSelectListener{
        void onAccountSelect(RelationEnterpriseBean.Enterprise enterprise);
    }

}
