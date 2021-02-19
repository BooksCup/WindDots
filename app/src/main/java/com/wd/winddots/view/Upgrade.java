package com.wd.winddots.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wd.winddots.R;
import com.wd.winddots.mvp.widget.adapter.UpgradeAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: Upgrade
 * Author: 郑
 * Date: 2020/6/8 11:07 AM
 * Description:
 */
public class Upgrade extends LinearLayout {


    private RecyclerView mRecyclerView;
    private UpgradeAdapter mAdapter;
    private TextView titleTv;
    private List<String> mData = new ArrayList<>();
    private UpgradeActionListener listener;

    public void setUpgradeActionListener(@NonNull UpgradeActionListener listener1){
        this.listener = listener1;
    }



    public Upgrade(Context context) {
        super(context);
        initView();
    }

    public Upgrade(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public Upgrade(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_upgrade, this, false);
        titleTv = view.findViewById(R.id.tv_title);
        mRecyclerView = view.findViewById(R.id.view_upgrade_rlist);
        mAdapter = new UpgradeAdapter(R.layout.item_upgrade,mData);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        TextView cancelBtn= view.findViewById(R.id.tv_button_cancel);
        TextView upgradeBtn= view.findViewById(R.id.tv_button_upgrade);
        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onCancel();
                }
            }
        });
        upgradeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onUpgrade();
                }
            }
        });
        this.addView(view);
    }

    public void setVersion(String version) {
        titleTv.setText(version);
    }


    public void setData(List<String> list) {
        mData.clear();
        mData.addAll(list);
        mAdapter.notifyDataSetChanged();
    }



    public interface UpgradeActionListener {
        void onCancel();

        void onUpgrade();
    }


}
