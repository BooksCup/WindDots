package com.wd.winddots.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class BasePresenter<T> {
    public Reference<T> mViewRef;


    public void attach(T view) {
        mViewRef = new WeakReference<>(view);
    }

    public void dettach() {
        if (mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
        System.gc();
    }

    public T getView(){
        if (mViewRef != null) {
            return mViewRef.get();
        }
        return null;
    }
}
