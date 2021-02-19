package com.wd.winddots.desktop.view.PinnedHeaderRecyclerView;

import android.graphics.Rect;

/**
 * FileName: IPinnedHeaderDecoration
 * Author: 郑
 * Date: 2020/6/18 2:52 PM
 * Description:
 */
public interface IPinnedHeaderDecoration {

    Rect getPinnedHeaderRect();

    int getPinnedHeaderPosition();
}
