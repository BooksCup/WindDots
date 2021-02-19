package com.wd.winddots.zxing.client.android;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.wd.winddots.R;
import com.wd.winddots.utils.Utils;

import androidx.annotation.Nullable;

/**
 * FileName: MaskView
 * Author: 郑
 * Date: 2020/7/6 5:00 PM
 * Description: 扫码遮罩
 */
public class MaskView extends View  {

    //相机遮罩框外面的线，阴影区域，滚动线
    private Paint border, area, line;
    //相机遮罩框中间透明区域
    private Rect center;
    //屏幕大小
    private int screenHeight, screenWidth;
    //滚动线的起始点
    private int startX, startY, endX, endY;
    //滚动线向下滚动标识
    private boolean isDown = true;
    //滚动线速度
    private static final int SPEED = 2;
    //中间区域宽高（dp），
    public static final int centerHeight = 300;
    public static final int centerWidth = 300;

    public MaskView(Context context) {
        super(context);
    }

    public MaskView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Resources resources = getResources();
        //setAlpha一定要在setStyle后面，否则不起作用
        border = new Paint(Paint.ANTI_ALIAS_FLAG);
        border.setColor(Color.BLUE);
        border.setStyle(Paint.Style.STROKE);
        border.setStrokeWidth(5f);
        border.setAlpha(10);

        area = new Paint(Paint.ANTI_ALIAS_FLAG);
        area.setStyle(Paint.Style.FILL);
        area.setColor(resources.getColor(R.color.viewfinder_mask));
        area.setAlpha(180);


        screenHeight = Utils.getScreenHeight(getContext());//DimenUtil.getScreenSize(context).heightPixels;
        screenWidth = Utils.getScreenWidth(getContext());//DimenUtil.getScreenSize(context).widthPixels;
        center = getCenterRect(context, 2000, 2000);

        line = new Paint(Paint.ANTI_ALIAS_FLAG);
        line.setStyle(Paint.Style.STROKE);
        line.setColor(Color.GREEN);
        //设置滚动线的起始点
        startX = center.left;
        startY = center.top;
        endX = center.right;
        endY = center.top;
    }

    public MaskView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 根据尺寸获取中间区大小
     *
     * @param context
     * @param height
     * @param width
     * @return
     */
    private Rect getCenterRect(Context context, int height, int width) {
        height = Utils.px2dip(context, height);
        width = Utils.px2dip(context, width);
        Rect rect = new Rect();
        int left = (this.screenWidth - width) / 2;
        int top = (this.screenHeight - height) / 2;
        rect.set(left, top, left + width, top + height);
        return rect;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制四周阴影区域（上下左右），注意+1和-1，不设置不显示边框
        canvas.drawRect(0, 0, screenWidth, center.top - 1, area);
        canvas.drawRect(0, center.bottom + 1, screenWidth, screenHeight, area);
        canvas.drawRect(0, center.top - 1, center.left - 1, center.bottom + 1, area);
        canvas.drawRect(center.right + 1, center.top - 1, screenWidth, center.bottom + 1, area);

        canvas.drawRect(center, border);

        //滚动线
        if (isDown) {
            startY = endY += SPEED;
            if (startY >= center.bottom)
                isDown = false;
        } else {
            startY = endY -= SPEED;
            if (startY <= center.top)
                isDown = true;
        }
        canvas.drawLine(startX, startY, endX, endY, line);
        postInvalidate();
    }


}
