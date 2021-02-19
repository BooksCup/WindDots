package com.wd.winddots.desktop.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.bean.UserInfoBean;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.view.VisitingCardView;

import cn.jmessage.support.qiniu.android.utils.StringUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: MeActivity
 * Author: 郑
 * Date: 2020/4/30 11:21 AM
 * Description: 个人信息详情
 */
public class MeActivity extends CommonActivity {

    public CompositeSubscription compositeSubscription;
    public MsgDataManager dataManager;


    private UserInfoBean infoBean;

    private VisitingCardView mVisitingCardView;
    private TextView mNameTextView;
    private ImageView mGenderIconl;
    private TextView mEnterpriseTextView;
    private TextView mDepartmentTextView;
    private TextView mPositionTextView;
    private TextView mJobNumTextView;
    private TextView mPhoneTextView;
    private TextView mEmailTextView;
    private TextView mAddressTextView;


    @Override
    public void initView() {
        super.initView();
        setTitleText("个人信息");
        addBadyView(R.layout.activity_me);

        mVisitingCardView = findViewById(R.id.activity_mineinfo_visiting_card);
        mGenderIconl = findViewById(R.id.activity_mineinfo_gender);
        mNameTextView = findViewById(R.id.activity_mineinfo_name);
        mEnterpriseTextView = findViewById(R.id.activity_mineinfo_enterprise);
        mDepartmentTextView = findViewById(R.id.activity_mineinfo_department);
        mPositionTextView = findViewById(R.id.activity_mineinfo_position);
        mJobNumTextView = findViewById(R.id.activity_mineinfo_jobnumber);
        mPhoneTextView = findViewById(R.id.activity_mineinfo_phone);
        mEmailTextView = findViewById(R.id.activity_mineinfo_email);
        mAddressTextView = findViewById(R.id.activity_mineinfo_address);

        compositeSubscription = new CompositeSubscription();
        dataManager = new MsgDataManager();
    }

    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter() {
        };
    }

    @Override
    public void initData() {
        super.initData();
        getData();
    }

    void getData() {

        String userId = SpHelper.getInstance(mContext).getUserId();

        compositeSubscription.add(dataManager.getUserInfo(userId, userId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast("加载失败,请稍后重试");
                    }

                    @Override
                    public void onNext(String s) {
                        Gson gson = new Gson();

                        infoBean = gson.fromJson(s, UserInfoBean.class);

                        mVisitingCardView.setUpData(infoBean);

                        mNameTextView.setText(infoBean.getName());
                        mEnterpriseTextView.setText(infoBean.getEnterpriseName());
                        mDepartmentTextView.setText(StringUtils.isNullOrEmpty(infoBean.getDepartmentName()) ? "" : infoBean.getDepartmentName());
                        mPositionTextView.setText(StringUtils.isNullOrEmpty(infoBean.getJobName()) ? "" : infoBean.getJobName());
                        mJobNumTextView.setText(StringUtils.isNullOrEmpty(infoBean.getJobNo()) ? "" : infoBean.getJobNo());
                        mPhoneTextView.setText(infoBean.getPhone());
                        mEmailTextView.setText(StringUtils.isNullOrEmpty(infoBean.getEmail()) ? "" : infoBean.getEmail());
                        mAddressTextView.setText(StringUtils.isNullOrEmpty(infoBean.getCompanyAddress()) ? "" : infoBean.getCompanyAddress());
                        if (infoBean.getGender() == 2) {
                            mGenderIconl.setImageResource(R.mipmap.girl);
                        } else {
                            mGenderIconl.setImageResource(R.mipmap.boy);
                        }
                    }
                })
        );

    }



}
