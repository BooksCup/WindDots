package com.wd.winddots.desktop.list.employee.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wd.winddots.R;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.desktop.list.employee.adapter.UserApplyAdapter;
import com.wd.winddots.entity.UserApply;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户申请
 *
 * @author zhou
 */
public class UserApplyActivity extends FragmentActivity implements UserApplyAdapter.UserApplyListener {

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.rv_user_apply)
    RecyclerView mUserApplyRv;

    private UserApplyAdapter mAdapter;

    private List<UserApply> mUserApplyList = new ArrayList<>();

    private VolleyUtil mVolleyUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_apply);
        ButterKnife.bind(this);

        mVolleyUtil = VolleyUtil.getInstance(this);
        initView();
    }

    @OnClick({R.id.iv_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mUserApplyRv.setLayoutManager(layoutManager);
        mUserApplyRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new UserApplyAdapter(this, R.layout.item_user_apply, mUserApplyList);
        mUserApplyRv.setAdapter(mAdapter);
        mAdapter.setUserApplyListener(this);
        getUserApplyList();
    }

    /**
     * 获取用户申请列表
     */
    private void getUserApplyList() {
        final String url = Constant.APP_BASE_URL + "userApply?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId();
        mVolleyUtil.httpGetRequest(url, response -> {
            List<UserApply> userApplyList = JSON.parseArray(response, UserApply.class);
            if (null != userApplyList && userApplyList.size() > 0) {
                mTitleTv.setText("成员申请(" + userApplyList.size() + ")");
            }
            mUserApplyList.addAll(userApplyList);
            mAdapter.notifyDataSetChanged();
        }, volleyError -> {
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
    }

    @Override
    public void updateUserApplyNumber(int userApplyNumber) {
        if (userApplyNumber > 0) {
            mTitleTv.setText("成员申请(" + userApplyNumber + ")");
        } else {
            mTitleTv.setText("成员申请");
        }
    }
}
