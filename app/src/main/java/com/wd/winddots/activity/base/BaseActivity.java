package com.wd.winddots.activity.base;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.wd.winddots.view.LoadingDialog;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 隐藏软键盘
     */
    protected void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 显示加载对话框
     */
    public void showLoadingDialog() {
        if (null != mLoadingDialog) {
            return;
        }
        mLoadingDialog = LoadingDialog.getInstance(this);
        mLoadingDialog.show();
    }

    /**
     * 隐藏加载对话框
     */
    public void hideLoadingDialog() {
        if (null != mLoadingDialog) {
            mLoadingDialog.hide();
            mLoadingDialog = null;
        }
    }

    /**
     * 显示toast
     *
     * @param msg toast信息
     */
    protected void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
