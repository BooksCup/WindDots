package com.wd.winddots.components.users;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.components.adapter.UserSelectAdapter;
import com.wd.winddots.confifg.Const;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.User;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.LoadingDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择审批人(单选)
 *
 * @author zhou
 */
public class SingleUserSelectActivity extends FragmentActivity implements BaseQuickAdapter.OnItemClickListener {

    private List<User> mUserList = new ArrayList<>();

    @BindView(R.id.et_search)
    EditText mSearchEt;

    @BindView(R.id.rv_user)
    RecyclerView mUserRv;

    private UserSelectAdapter mAdapter;

    private List<String> mDisabledIds = new ArrayList<>();

    private VolleyUtil mVolleyUtil;
    private LoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select_single);
        ButterKnife.bind(this);

        mVolleyUtil = VolleyUtil.getInstance(this);
        mLoadingDialog = LoadingDialog.getInstance(this);
        initView();
        getEnterpriseReviewerList("", SpHelper.getInstance(this).getEnterpriseId());
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                String keyword = mSearchEt.getText().toString().trim();
                getEnterpriseReviewerList(keyword, SpHelper.getInstance(this).getEnterpriseId());
                // 隐藏软键盘
                hideSoftKeyboard(this);
                break;
        }
    }

    public void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mUserRv.setLayoutManager(layoutManager);
        mAdapter = new UserSelectAdapter(R.layout.item_user_select, mUserList);
        mUserRv.setAdapter(mAdapter);

        Intent intent = getIntent();
        String disabledIds = intent.getStringExtra("disableIds");
        if (!TextUtils.isEmpty(disabledIds)) {
            List<String> disabledIdList = JSON.parseArray(disabledIds, String.class);
            mDisabledIds.addAll(disabledIdList);
        }

        mAdapter.setOnItemClickListener(this);

        mSearchEt.setOnEditorActionListener((v, actionId, event) -> {
            if ((actionId == 0 || actionId == 3) && event != null) {
                String keyword = mSearchEt.getText().toString().trim();
                getEnterpriseReviewerList(keyword, SpHelper.getInstance(this).getEnterpriseId());
                // 隐藏软键盘
                hideSoftKeyboard(this);
                return true;
            }
            return false;

        });
    }

    private void getEnterpriseReviewerList(final String keyword, final String enterpriseId) {
        String url;
        try {
            url = Constant.APP_BASE_URL + "enterprise/" + enterpriseId + "/user?keyword=" + URLEncoder.encode(keyword, "utf-8");
        } catch (UnsupportedEncodingException e) {
            url = Constant.APP_BASE_URL + "enterprise/" + enterpriseId + "/user?keyword=" + keyword;
        }

        mVolleyUtil.httpGetRequest(url, response -> {
            mLoadingDialog.hide();
            List<User> userList = JSON.parseArray(response, User.class);
            for (User user : userList) {
                if (mDisabledIds.contains(user.getId())) {
                    user.setDisable(true);
                }

            }
            mUserList.clear();
            mUserList.addAll(userList);
            mAdapter.setKeyword(keyword);

            mAdapter.notifyDataSetChanged();
        }, volleyError -> {
            mLoadingDialog.hide();
            mVolleyUtil.handleCommonErrorResponse(SingleUserSelectActivity.this, volleyError);
        });
    }

    /**
     * item点击事件
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        User user = mUserList.get(position);
        if (user.isDisable()) {
            return;
        }

        Intent intent = new Intent();
        // 设置要回传的数据
        intent.putExtra("user", user);
        setResult(Const.SINGLE_USER_SELECT_TARGRT, intent);
        finish();
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
