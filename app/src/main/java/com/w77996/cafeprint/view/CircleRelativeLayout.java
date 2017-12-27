package com.w77996.cafeprint.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.w77996.cafeprint.R;

/**
 * Created by w77996
 * on 2017/12/27.
 * Github:https://github.com/w77996
 */
public class CircleRelativeLayout extends LinearLayout {
    private int color;
    private int[] colors;
    private int alpha;
    public CircleRelativeLayout(Context context) {
        super(context);
    }
    public CircleRelativeLayout(Context context, AttributeSet attrs) {
        super(context,attrs);
        init(context,attrs);
        setWillNotDraw(false);
    }
    private void init(Context context, AttributeSet attrs) {
       /* TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.CircleRelativeLayoutLayout);*/
       // color = array.getColor(R.styleable.CircleRelativeLayoutLayout_background_color,0X0000000);
       // alpha = array.getInteger(R.styleable.CircleRelativeLayoutLayout_background_alpha,100);
       // setColors();
        //array.recycle();
    }
    @Override
    protected void onDraw(Canvas canvas) { //构建圆形
        int width = getMeasuredWidth();
        Paint mPaint = new Paint();
       // mPaint.setARGB(alpha,colors[0],colors[1],colors[2]);
        mPaint.setAntiAlias(true);
        float cirX = width / 2;
        float cirY = width / 2;
        float radius = width / 2;
        canvas.drawCircle(cirX, cirY, radius, mPaint);
        super.onDraw(canvas);
    }

    public void setColor(int color) { //设置背景色
        this.color = color;
        setColors();
        invalidate();
    }

    public void setAlhpa(int alhpa) { //设置透明度
        this.alpha = alhpa;
        invalidate();
    }


    public void setColors() {
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        this.colors = new int[]{red,green,blue};
    }

}