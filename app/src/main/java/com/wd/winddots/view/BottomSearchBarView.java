package com.wd.winddots.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wd.winddots.R;

import androidx.annotation.Nullable;

public class BottomSearchBarView extends RelativeLayout {

    private ImageView mAddIcon;
    private ImageView mSeaechIcon;
    private BottomSearchBarViewClickListener iconClickListener;


    public BottomSearchBarView(Context context) {
        super(context);
        setUpUI();
    }

    public BottomSearchBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpUI();
    }

    public BottomSearchBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpUI();
    }


    private void setUpUI() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_bottom_searchbar, this, false);
        mAddIcon = view.findViewById(R.id.bottom_search_add);
        mSeaechIcon = view.findViewById(R.id.bottom_search_search);
        mAddIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iconClickListener != null) {
                    iconClickListener.onAddIconDidClick();
                }
            }
        });

        mSeaechIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iconClickListener != null) {
                    iconClickListener.onSearchIconDidClick();
                }
            }
        });
        this.addView(view);
    }

    public void setOnIconClickListener(@Nullable BottomSearchBarViewClickListener listener) {
        iconClickListener = listener;
    }

    public void setAddIconVisibility(int visibility){
        mAddIcon.setVisibility(visibility);
    }

    public void setSearchIconVisibility(int visibility){
        mSeaechIcon.setVisibility(visibility);
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
