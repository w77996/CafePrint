package com.w77996.cafeprint.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import com.w77996.cafeprint.R;

/**
 * Created by w77996
 * on 2017/12/27.
 * Github:https://github.com/w77996
 */
public class MyMatrixImg extends ImageView {

    private Context mContext;
    private Matrix currentMatrix, savedMatrix;// Matrix对象

    private PointF startF= new PointF();
    private PointF midF;// 起点、中点对象

    // 初始的两个手指按下的触摸点的距离
    private float oldDis = 1f;

    private float saveRotate = 0F;// 保存了的角度值

    private static final int MODE_NONE = 0;// 默认的触摸模式
    private static final int MODE_DRAG = 1;// 拖拽模式
    private static final int MODE_ZOOM = 2;// 缩放模式
    private int mode = MODE_NONE;


    public MyMatrixImg(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        // 初始化
        init();
    }
    public MyMatrixImg(Context context) {
        this(context, null);
    }
    private void init() {
        /*
         * 实例化对象
         */
        currentMatrix = new Matrix();
        savedMatrix = new Matrix();

        /*
         * 获取屏幕宽高
         */

        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int Screenwidth = outMetrics.widthPixels;
        int Screenheight = outMetrics.heightPixels;

      /*  *//*
         * 设置图片资源
         *//**/
      /*  Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.k);
        bitmap = Bitmap.createBitmap(bitmap);
        setImageBitmap(bitmap);*/
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()& MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:// 单点接触屏幕时
                savedMatrix.set(currentMatrix);
                startF.set(event.getX(), event.getY());
                mode=MODE_DRAG;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:// 第二个手指按下事件
                oldDis = calDis(event);
                if (oldDis > 10F) {
                    savedMatrix.set(currentMatrix);
                    midF=calMidPoint(event);
                    mode = MODE_ZOOM;
                }
                saveRotate = calRotation(event);//计算初始的角度
                break;
            case MotionEvent.ACTION_MOVE:// 触摸点移动时

                /*
                 * 单点触控拖拽平移
                 */

                if (mode == MODE_DRAG) {
                    currentMatrix.set(savedMatrix);
                    float dx = event.getX() - startF.x;
                    float dy = event.getY() - startF.y;
                    currentMatrix.postTranslate(dx, dy);
                }
                /*
                 * 两点触控拖放
                 */
                else if(mode == MODE_ZOOM && event.getPointerCount() == 2){
                    float newDis = calDis(event);
                    float rotate = calRotation(event);
                    currentMatrix.set(savedMatrix);

                    //指尖移动距离大于10F缩放
                    if (newDis > 10F) {
                        float scale = newDis / oldDis;
                        currentMatrix.postScale(scale, scale, midF.x, midF.y);
                    }

                    System.out.println("degree"+rotate);
                    //当旋转的角度大于5F才进行旋转
                    if(Math.abs(rotate - saveRotate)>5F){
                        currentMatrix.postRotate(rotate - saveRotate, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:// 单点离开屏幕时
                mode=MODE_NONE;
                break;
            case MotionEvent.ACTION_POINTER_UP:// 第二个点离开屏幕时
                System.out.println(event.getActionIndex());
                savedMatrix.set(currentMatrix);
                if(event.getActionIndex()==0)
                    startF.set(event.getX(1), event.getY(1));
                else if(event.getActionIndex()==1)
                    startF.set(event.getX(0), event.getY(0));
                mode=MODE_DRAG;
                break;


        }

        setImageMatrix(currentMatrix);
        return true;
    }

    // 计算两个触摸点之间的距离
    private float calDis(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    // 计算两个触摸点的中点
    private PointF calMidPoint(MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        return new PointF(x / 2, y / 2);
    }

    //计算角度
    private float calRotation(MotionEvent event) {
        double deltaX = (event.getX(0) - event.getX(1));
        double deltaY = (event.getY(0) - event.getY(1));
        double radius = Math.atan2(deltaY, deltaX);
        return (float) Math.toDegrees(radius);
    }

  /*  @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);//设置画布背景方便观察
      //  Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.photo);//夹在要画的图片，这里从资源文件，从其他地方也是可以的，比如sd卡
       // Bitmap out = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
      //  Bitmap bitmap = getR
        Canvas canvas1 = new Canvas(out);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas1.drawCircle(width/2,height/2,radius,paint);//画一个圆，辅助作用，应为这是先画的！
        //canvas1.drawCircle(0,0,radius,paint);//这个注视你可以尝试去掉，去掉你会发现，这是取先画的全部操作的并集，此时辅助区域变为这个并集
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//DST先画，SRC后画，SRC_IN是取并集中属于后画的（有点拗口自己理解）
        canvas1.drawBitmap(bitmap,0,0,paint);//从这可以看出，先画（圆）和后画（bitmap）的交集为圆，而刚好圆属于整合bitmap，此时只画圆部分的bitmap，而不是整个bitmap
        canvas.drawBitmap(out,0,0,null);//最后只是简单的将一个为圆的bitmap画上去而已
        //canvas.drawBitmap(out,0,0,new Paint());//这个可以发现，其实只要不是带有Xfermode的画笔就可以了
    }*/
}