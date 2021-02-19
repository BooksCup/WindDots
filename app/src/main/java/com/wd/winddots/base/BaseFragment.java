package com.wd.winddots.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public abstract class BaseFragment<V,T extends BasePresenter<V>> extends Fragment {
    public T presenter;

    public Context mContext;
    public View mView;
    protected boolean isVisible;
    private boolean isViewCreated;

    private boolean isFirst = true;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregisterEvent();
        if (presenter != null){
            presenter.dettach();
            presenter = null;
            System.gc();
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        registerEvent();
        mContext = getActivity();
        if (mView == null){
            mView = inflater.inflate(getContentView(), container, false);
        }
        presenter = initPresenter();
        initView();

        presenter.attach((V)this);
        initListener();
        return mView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isOnInvisibleState(true);
            isVisible = true;
            lazyLoad();
        } else {
            isOnInvisibleState(false);
            isVisible = false;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    /**
     * 懒加载
     */
    protected void lazyLoad() {
        if (isViewCreated && isVisible){
            initData();
            isViewCreated = false;
            isVisible = false;
        }
    }

    // 实例化presenter
    public abstract T initPresenter();
    public abstract int getContentView();
    public abstract void initView();
    public abstract void initListener();
    public abstract void initData();

    protected void isOnInvisibleState(boolean isVisible) {

    }

    protected void registerEvent(){

    }

    protected void unregisterEvent(){

    }


    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 显示toast
     * @param msg
     */
    public void showToast(String msg){
        Toast.makeText(mContext.getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
