package com.wd.winddots.desktop.view.PinnedHeaderRecyclerView;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: PinnedHeaderAdapter
 * Author: 郑
 * Date: 2020/6/18 2:53 PM
 * Description:
 */
public abstract class PinnedHeaderAdapter <VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{

    /**
     * 判断该position对应的位置是要固定
     *
     * @param position adapter position
     * @return true or false
     */
    public abstract boolean isPinnedPosition(int position);


    public RecyclerView.ViewHolder onCreatePinnedViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder(parent, viewType);
    }

    public void onBindPinnedViewHolder(VH holder, int position) {
        onBindViewHolder(holder, position);
    }



}
