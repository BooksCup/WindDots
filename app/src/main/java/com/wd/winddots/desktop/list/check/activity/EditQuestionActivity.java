package com.wd.winddots.desktop.list.check.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.desktop.list.check.adapter.CheckQuestionAdapter;
import com.wd.winddots.desktop.list.check.bean.CheckQuestionBean;
import com.wd.winddots.desktop.list.check.view.EditQuestionPopView;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: EditQuestionActivity
 * Author: 郑
 * Date: 2021/1/25 12:11 PM
 * Description: 编辑问题
 */
public class EditQuestionActivity extends FragmentActivity implements BaseQuickAdapter.OnItemClickListener, EditQuestionPopView.EditQuestionPopViewOnCommitDidClickListener {

    private VolleyUtil mVolleyUtil;


    @BindView(R.id.rv_question)
    RecyclerView mQuestionRv;

    @BindView(R.id.ll_body)
    LinearLayout mBtnShortCut;
    private PopupWindow mPopupWindow;

    private CheckQuestionAdapter mQuestionAdapter;

    private List<CheckQuestionBean> mQuestionData = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_edit_question);
        ButterKnife.bind(this);

        mVolleyUtil = VolleyUtil.getInstance(this);
        initView();
    }

    private void initView(){
        CheckQuestionBean bean7 = new CheckQuestionBean();
        mQuestionData.add(bean7);
        mQuestionAdapter = new CheckQuestionAdapter(R.layout.item_check_question,mQuestionData);
        mQuestionAdapter.setOnItemClickListener(this);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mQuestionRv.setLayoutManager(manager);
        mQuestionRv.setAdapter(mQuestionAdapter);
        getQuestionList();
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    /*
    * 获取数据
    * */
    private void getQuestionList(){

        String url = Constant.APP_BASE_URL + "fabricQcProblemConfig?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId();
        mVolleyUtil.httpGetRequest(url,response -> {
            List<CheckQuestionBean> data = JSON.parseArray(response,CheckQuestionBean.class);
            data.add(new CheckQuestionBean());
            mQuestionAdapter.setNewData(data);
        }, volleyError->{
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
    }


    /*
    * item 点击事件
    * */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CheckQuestionBean bean = mQuestionAdapter.getData().get(position);
        if (StringUtils.isNullOrEmpty(bean.getTag())){
            showAlert(bean);
        }
    }

    private void showAlert(CheckQuestionBean questionBean){
        EditQuestionPopView popView = new EditQuestionPopView(this);
        popView.setEditQuestionPopViewOnCommitDidClickListener(this);
        mPopupWindow  = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(mBtnShortCut, Gravity.TOP| Gravity.LEFT,0,0);
    }

    /*
    * 点击提交按钮
    * */
    @Override
    public void onCommitBtnDidClick(String questionContent) {



        if (StringUtils.isNullOrEmpty(questionContent)){
            Toast.makeText(this,getString(R.string.check_question_add_empty),Toast.LENGTH_LONG).show();
            return;
        }

        for (int i = 0;i < mQuestionAdapter.getData().size() - 1;i++){
            CheckQuestionBean questionBean = mQuestionAdapter.getData().get(i);
            if (questionBean.getTag().equals(questionContent)){
                Toast.makeText(this,getString(R.string.check_question_add_already_exists),Toast.LENGTH_LONG).show();
                return;
            }
        }
        mPopupWindow.dismiss();
        String url = Constant.APP_BASE_URL + "fabricQcProblemConfig";
        Map<String, String> params = new HashMap<String, String>();
        params.put("tag",questionContent);
        params.put("enterpriseId", SpHelper.getInstance(this).getEnterpriseId());
        mVolleyUtil.httpPostRequest(url,params,response -> {
            getQuestionList();
        }, volleyError->{
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });

    }
}
