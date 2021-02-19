package com.wd.winddots.base;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wd.winddots.R;

import java.io.File;

/**
 * FileName: CommonEmptyActivity
 * Author: 郑
 * Date: 2020/10/20 9:35 AM
 * Description:
 */
public abstract class CommonEmptyActivity <V, T extends BasePresenter<V>> extends BaseActivity implements View.OnClickListener {

    private RelativeLayout mBodyLayout;
    private LinearLayout mRootLl;


    public T presenter;

    @Override
    public int getContentView() {
        return R.layout.activity_common_empty;
    }

    @Override
    public void initView() {
        mRootLl = findViewById(R.id.ll_root);
        mBodyLayout = findViewById(R.id.activity_common_body_layout);
        presenter = initPresenter();
        presenter.attach((V) this);
    }

    // 实例化presenter
    public abstract T initPresenter();

    public void addBadyView(int id) {
        View bodyView = LayoutInflater.from(this).inflate(id, mRootLl, false);
        mBodyLayout.addView(bodyView);
    }


    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public File uri2File(Uri uri) {
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = managedQuery(uri, proj, null,
                null, null);
        if (actualimagecursor == null) {
            img_path = uri.getPath();
        } else {
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            img_path = actualimagecursor
                    .getString(actual_image_column_index);
        }
        File file = new File(img_path);
        return file;
    }


    //dp转px
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //px转dp
    public int px2dip(Context context, int pxValue) {
        return ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pxValue, context.getResources().getDisplayMetrics()));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ButterKnife.unbind(this);

        if (presenter != null) {
            presenter.dettach();
            presenter = null;
            System.gc();
        }
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    /*
     * 获取屏幕宽度
     * */
    public int getScreenWidth() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int widthPixel = outMetrics.widthPixels;
        return widthPixel;
        //int mItemS = widthPixel/3;
    }

//    public void showLoading() {
//        if (mLoading != null) {
//            return;
//        }
//        mLoading = LoadingDialog.getInstance(mContext);
//        mLoading.show();
//    }
//
//    public void hideLoading() {
//        if (mLoading != null) {
//            mLoading.hide();
//            mLoading = null;
//        }
//    }
}
