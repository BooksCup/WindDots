package com.wd.winddots.view.imageCollection;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.wd.winddots.R;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: ImageCollection
 * Author: 郑
 * Date: 2020/5/20 2:33 PM
 * Description: 展示图片
 */
public class ImageCollection extends RecyclerView {



    public ImageCollection(Context context) {
        super(context);
        initView();
    }

    public ImageCollection(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ImageCollection(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
            View view= LayoutInflater.from(getContext()).inflate(R.layout.view_image_collection, this, false);
            this.addView(view);
    }

}
