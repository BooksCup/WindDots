package com.wd.winddots.activity.select;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.wd.winddots.R;
import com.wd.winddots.adapter.select.RelatedCompanyAdapter;
import com.wd.winddots.entity.RelatedCompany;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择-往来单位
 *
 * @author zhou
 */
public class SelectRelatedCompanyActivity extends FragmentActivity {

    @BindView(R.id.et_related_company)
    EditText mRelatedCompanyEt;

    @BindView(R.id.tv_search)
    TextView mSearchTv;

    @BindView(R.id.rv_related_company)
    RecyclerView mRelatedCompanyRv;

    RelatedCompanyAdapter mAdapter;
    List<RelatedCompany> mRelatedCompanyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_related_company);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRelatedCompanyRv.setLayoutManager(layoutManager);
        mRelatedCompanyRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new RelatedCompanyAdapter(R.layout.item_mine_claiming_relationenterpirse, mRelatedCompanyList);
        mRelatedCompanyRv.setAdapter(mAdapter);
    }

}
