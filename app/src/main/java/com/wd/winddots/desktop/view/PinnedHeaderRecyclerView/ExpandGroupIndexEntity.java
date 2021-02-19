package com.wd.winddots.desktop.view.PinnedHeaderRecyclerView;

/**
 * FileName: ExpandGroupIndexEntity
 * Author: 郑
 * Date: 2020/6/18 3:58 PM
 * Description:
 */
public class ExpandGroupIndexEntity {
    private int mGroupIndex;
    private int mChildIndex;
    private int mChildCount;

    public ExpandGroupIndexEntity(int groupIndex, int childIndex, int childCount) {
        mGroupIndex = groupIndex;
        mChildIndex = childIndex;
        mChildCount = childCount;
    }

    public int getGroupIndex() {
        return mGroupIndex;
    }

    public void setGroupIndex(int groupIndex) {
        mGroupIndex = groupIndex;
    }

    public int getChildIndex() {
        return mChildIndex;
    }

    public void setChildIndex(int childIndex) {
        mChildIndex = childIndex;
    }

    public int getChildCount() {
        return mChildCount;
    }

    public void setChildCount(int childCount) {
        mChildCount = childCount;
    }
}
