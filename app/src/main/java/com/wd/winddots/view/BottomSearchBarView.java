package com.wd.winddots.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wd.winddots.R;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 底部搜索
 *
 * @author zhou
 */
public class BottomSearchBarView extends RelativeLayout {

    @BindView(R.id.iv_add)
    ImageView mAddIv;

    @BindView(R.id.iv_search)
    ImageView mSearchIv;

    private BottomSearchBarViewClickListener mBottomSearchBarViewClickListener;

    public BottomSearchBarView(Context context) {
        super(context);
        initView();
    }

    public BottomSearchBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BottomSearchBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_bottom_search_bar, this, false);
        ButterKnife.bind(this, view);
        mAddIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomSearchBarViewClickListener != null) {
                    mBottomSearchBarViewClickListener.onAddIconDidClick();
                }
            }
        });

        mSearchIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomSearchBarViewClickListener != null) {
                    mBottomSearchBarViewClickListener.onSearchIconDidClick();
                }
            }
        });
        this.addView(view);
    }

    public void setOnIconClickListener(@Nullable BottomSearchBarViewClickListener listener) {
        mBottomSearchBarViewClickListener = listener;
    }

    public void setAddIconVisibility(int visibility) {
        mAddIv.setVisibility(visibility);
    }

    public void setSearchIconVisibility(int visibility) {
        mSearchIv.setVisibility(visibility);
    }

    public interface BottomSearchBarViewClickListener {
        /*
         * 点击添加按钮
         * */
        void onAddIconDidClick();

        /*
         * 点击搜索按钮
         * */
        void onSearchIconDidClick();
    }

}
