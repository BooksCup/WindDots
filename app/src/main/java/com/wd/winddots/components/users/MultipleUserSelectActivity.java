package com.wd.winddots.components.users;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.R;
import com.wd.winddots.components.adapter.UserSelectAdapter;
import com.wd.winddots.confifg.Const;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.User;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.LoadingDialog;

import java.io.Serializable;
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
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * 选择抄送人(多选)
 *
 * @author zhou
 */
public class MultipleUserSelectActivity extends FragmentActivity implements BaseQuickAdapter.OnItemClickListener {

    private List<User> mUserList = new ArrayList<>();
    private List<User> mSelectedUserList = new ArrayList<>();

    @BindView(R.id.et_search)
    EditText mSearchEt;

    /**
     * 所有抄送人
     */
    @BindView(R.id.rv_user)
    RecyclerView mUserRv;

    private UserSelectAdapter mAdapter;

    /**
     * 已选择抄送人
     */
    @BindView(R.id.rv_selected_user)
    RecyclerView mSelectedUserRv;

    private HeaderAdapter mHeaderAdapter;

    private List<String> mDisableIds = new ArrayList<>();
    private List<String> mSelectIds = new ArrayList<>();

    private VolleyUtil mVolleyUtil;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select);
        ButterKnife.bind(this);

        mVolleyUtil = VolleyUtil.getInstance(this);
        mLoadingDialog = LoadingDialog.getInstance(this);
        initView();
        getEnterpriseReviewerList("", SpHelper.getInstance(this).getEnterpriseId());
    }

    public void initView() {

        LinearLayoutManager userLm = new LinearLayoutManager(this);
        mUserRv.setLayoutManager(userLm);
        mAdapter = new UserSelectAdapter(R.layout.item_user_select, mUserList);
        mUserRv.setAdapter(mAdapter);

        LinearLayoutManager selectedUserLm = new LinearLayoutManager(this);
        selectedUserLm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mSelectedUserRv.setLayoutManager(selectedUserLm);
        mHeaderAdapter = new HeaderAdapter(R.layout.item_user_select_header, mSelectedUserList);
        mSelectedUserRv.setAdapter(mHeaderAdapter);
        mAdapter.setOnItemClickListener(this);

        Intent intent = getIntent();
        String disableIdsJson = intent.getStringExtra("disableIds");
        if (!StringUtils.isNullOrEmpty(disableIdsJson)) {
            Gson gson = new Gson();
            List<String> ids = gson.fromJson(disableIdsJson, new TypeToken<List<String>>() {
            }.getType());
            mDisableIds.addAll(ids);
        }
        String selectedIds = intent.getStringExtra("selectedIds");
        if (!StringUtils.isNullOrEmpty(selectedIds)) {
            List<String> selectedIdList = JSON.parseArray(selectedIds, String.class);
            mSelectIds.addAll(selectedIdList);
        }

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

    @OnClick({R.id.iv_back, R.id.ll_save, R.id.tv_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_save:
                Intent intent = new Intent();
                // 设置要回传的数据
                intent.putExtra("list", (Serializable) mSelectedUserList);
                setResult(Const.MULTIPLE_USER_SELECT_TARGRT, intent);
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
                if (mDisableIds.contains(user.getId())) {
                    user.setDisable(true);
                }

                if (mSelectIds.contains(user.getId())) {
                    user.setSelect(true);
                    if (!mSelectedUserList.contains(user)) {
                        mSelectedUserList.add(user);
                    }
                }
            }
            mUserList.clear();
            mUserList.addAll(userList);
            mAdapter.setKeyword(keyword);

            if (mSelectedUserList.size() > 0) {
                mSelectedUserRv.setVisibility(View.VISIBLE);
            } else {
                mSelectedUserRv.setVisibility(View.GONE);
            }

            mAdapter.notifyDataSetChanged();
            mHeaderAdapter.notifyDataSetChanged();

        }, volleyError -> {
            mLoadingDialog.hide();
            mVolleyUtil.handleCommonErrorResponse(MultipleUserSelectActivity.this, volleyError);
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ImageView selectedIconIv = view.findViewById(R.id.iv_selected_icon);
        User user = mUserList.get(position);
        if (user.isDisable()) {
            return;
        }
        if (user.isSelect()) {
            selectedIconIv.setImageResource(R.mipmap.unselect);
            user.setSelect(false);
            mSelectedUserList.remove(user);
            mSelectIds.remove(user.getId());
        } else {
            selectedIconIv.setImageResource(R.mipmap.select);
            user.setSelect(true);
            mSelectedUserList.add(user);
            mSelectIds.add(user.getId());
        }

        if (mSelectedUserList.size() > 0) {
            mSelectedUserRv.setVisibility(View.VISIBLE);
        } else {
            mSelectedUserRv.setVisibility(View.GONE);
        }

        mHeaderAdapter.notifyDataSetChanged();
    }

    private class HeaderAdapter extends BaseQuickAdapter<User, BaseViewHolder> {

        public HeaderAdapter(int layoutResId, @Nullable List<User> userList) {
            super(layoutResId, userList);
        }

        @Override
        protected void convert(BaseViewHolder helper, User user) {
            SimpleDraweeView mAvatarSdv = helper.getView(R.id.sdv_avatar);
            if (!TextUtils.isEmpty(user.getAvatar())) {
                mAvatarSdv.setImageURI(Uri.parse(user.getAvatar()));
            }
        }

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
