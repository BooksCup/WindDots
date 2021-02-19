package com.wd.winddots.mvp.listener;

import android.view.MotionEvent;

/**
 * FileName: MainActivityDispatchEventListener
 * Author: 郑
 * Date: 2020/9/9 11:06 AM
 * Description: MainActivity 屏幕触摸事件监听
 */
public interface MainActivityDispatchEventListener {

     void dispatchTouchEvent(MotionEvent ev);

}
