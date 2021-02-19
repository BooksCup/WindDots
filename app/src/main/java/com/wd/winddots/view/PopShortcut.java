package com.wd.winddots.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wd.winddots.R;

import androidx.annotation.NonNull;

/**
 * FileName: PopShortcut
 * Author: 郑
 * Date: 2020/5/4 2:11 PM
 * Description: 快捷 pop 页面
 */
public class PopShortcut extends RelativeLayout {


    public static int TAP_BOTTOM_BAR_ONE = 1; //底部第一个按钮
    public static int TAP_BOTTOM_BAR_TWO = 2;
    public static int TAP_BOTTOM_BAR_THREE = 3;
    public static int TAP_BOTTOM_BAR_FOUR = 4;
    public static int TAP_BOTTOM_BAR_FIVE = 5;

    private PopShortcutClickListener listener;

    public PopShortcut(Context context) {
        super(context);
        initView();
    }

    public PopShortcut(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PopShortcut(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_shortcut, this, false);

        TextView one = view.findViewById(R.id.pop_shortcut_one);
        TextView two = view.findViewById(R.id.pop_shortcut_two);
        TextView three = view.findViewById(R.id.pop_shortcut_three);
        TextView four = view.findViewById(R.id.pop_shortcut_four);
        TextView five = view.findViewById(R.id.pop_shortcut_five);
        RelativeLayout layout = view.findViewById(R.id.shape_popshort_body);

        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.tabBottomBarDidClick(TAP_BOTTOM_BAR_FIVE);
            }
        });

        one.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tabBottomBarDidClick(view.getId());
            }
        });

        two.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tabBottomBarDidClick(view.getId());
            }
        });

        three.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tabBottomBarDidClick(view.getId());
            }
        });

        four.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tabBottomBarDidClick(view.getId());
            }
        });

        five.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tabBottomBarDidClick(view.getId());
            }
        });

        TextView fastone = view.findViewById(R.id.pop_shortcut_fast_one);
        TextView fasttwo = view.findViewById(R.id.pop_shortcut_fast_two);
        TextView fastthree = view.findViewById(R.id.pop_shortcut_fast_three);

        fastone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.fastButtonDidClick(1);
            }
        });

        fasttwo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.fastButtonDidClick(2);
            }
        });

        fastthree.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.fastButtonDidClick(3);
            }
        });


        this.addView(view);
    }

    public void setTabBottomBarClickListener(@NonNull PopShortcutClickListener listener) {
        this.listener = listener;
    }

    private void tabBottomBarDidClick(int id) {
        switch (id) {
            case R.id.pop_shortcut_one:
                listener.tabBottomBarDidClick(TAP_BOTTOM_BAR_ONE);
                break;
            case R.id.pop_shortcut_two:
                listener.tabBottomBarDidClick(TAP_BOTTOM_BAR_TWO);
                break;
            case R.id.pop_shortcut_three:
                listener.tabBottomBarDidClick(TAP_BOTTOM_BAR_THREE);
                break;
            case R.id.pop_shortcut_four:
                listener.tabBottomBarDidClick(TAP_BOTTOM_BAR_FOUR);
                break;
            case R.id.pop_shortcut_five:
                listener.tabBottomBarDidClick(TAP_BOTTOM_BAR_FIVE);
                break;
        }
    }


    public interface PopShortcutClickListener {
        void tabBottomBarDidClick(int tab);

        void fastButtonDidClick(int button);
    }


}
