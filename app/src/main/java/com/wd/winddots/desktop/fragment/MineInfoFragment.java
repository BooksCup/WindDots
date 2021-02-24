package com.wd.winddots.desktop.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.activity.stock.out.StockOutActivity;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.bean.UserInfoBean;
import com.wd.winddots.desktop.activity.MeActivity;
import com.wd.winddots.desktop.activity.SettingActivity;
import com.wd.winddots.mvp.widget.PointsActivity;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.view.VisitingCardView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MineInfoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public CompositeSubscription compositeSubscription;
    public MsgDataManager dataManager;
    private String userId;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private VisitingCardView mVisitingCardView;
    private TextView mPointTextView;
    private LinearLayout mPointCell;
    private UserInfoBean mineInfo;
    private LinearLayout mSettingCell;


    public static MineInfoFragment newInstance() {
        MineInfoFragment fragment = new MineInfoFragment();
        fragment.compositeSubscription = new CompositeSubscription();
        fragment.dataManager = new MsgDataManager();
        return fragment;
    }

//    public static MineInfoFragment () {
//        MineInfoFragment fragment = new MineInfoFragment();
//        fragment.compositeSubscription = new CompositeSubscription();
//        fragment.dataManager = new MsgDataManager();
//        return fragment;
//    }

    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter() {
        };
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_tap_mineinfo;
    }

    @Override
    public void initView() {
        userId = SpHelper.getInstance(mContext.getApplicationContext()).getString("id");
        mSwipeRefreshLayout = mView.findViewById(R.id.fragment_mineinfo_srl);
        mVisitingCardView = mView.findViewById(R.id.fragment_mineinfo_visiting_card);
        mPointTextView = mView.findViewById(R.id.fragment_mineinfo_point);
        mPointCell = mView.findViewById(R.id.fragment_mineinfo_pointcell);
        mSettingCell = mView.findViewById(R.id.fragment_mineinfo_settingcell);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void initListener() {

        mPointCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PointsActivity.class);
                intent.putExtra("points", mineInfo.getPoint());
                startActivity(intent);
            }
        });


        mVisitingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MeActivity.class);
                //intent.putExtra("points",mineInfo.getPoint());
                startActivity(intent);
            }
        });


        mSettingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, SettingActivity.class);
//                //intent.putExtra("points",mineInfo.getPoint());
//                startActivity(intent);
                Intent intent = new Intent(mContext, StockOutActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void initData() {
        mSwipeRefreshLayout.setRefreshing(true);
        getData();
    }

    @Override
    public void onRefresh() {
        getData();
    }

    private void getData() {

        compositeSubscription.add(dataManager.getUserInfo(userId, userId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(String s) {
                        Gson gson = new Gson();
                        mineInfo = gson.fromJson(s, UserInfoBean.class);
                        mSwipeRefreshLayout.setRefreshing(false);
                        mVisitingCardView.setUpData(mineInfo);
                        String point = mineInfo.getPoint();
                        if (StringUtils.isNullOrEmpty(point)) {
                            point = "0分";
                        } else {
                            point = point + "分";
                        }
                        mPointTextView.setText(point);
                    }
                })
        );
    }

}
