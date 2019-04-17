package com.assess15.code.canvas;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class TransformView extends View {

    private Paint paint;
    private Canvas canvas;

    public TransformView(Context context) {
        this(context, null);
    }

    public TransformView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransformView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        canvas = new Canvas();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("dd", "走了onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("dd", "走了onLayout");
    }

    // onResume 时候会调用
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("dd", "走了onDraw");

//        initTranslate(canvas);

//        initRotate(canvas);

//        initScale(canvas);

//        initSkew(canvas);

        initClip(canvas);

        initMatrix(canvas);

    }

    private void initMatrix(Canvas canvas) {
        canvas.drawRect(0,0,700,700,paint);
        Matrix matrix = new Matrix();
        matrix.setTranslate(50,50);
        matrix.setRotate(45);
        matrix.setScale(0.5f,0.5f);
        canvas.setMatrix(matrix);
        canvas.drawRect(0,0,700,700,paint);
    }

    /**
     * 切割
     * @param canvas
     */
    private void initClip(Canvas canvas) {
        canvas.drawRect(200,200,700,700,paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(200,800,700,1300,paint);
        canvas.clipRect(200,200,400,400);// 画布范围内被裁剪
//        canvas.clipOutRect(200,200,400,400);// 画布范围外被裁剪
        canvas.drawCircle(100,100,100,paint);
        canvas.drawCircle(300,300,100,paint);
    }

    /**
     * 斜切
     * @param canvas
     */
    private void initSkew(Canvas canvas) {
        canvas.drawRect(0,0,400,400,paint);
        canvas.skew(1,0);
//        canvas.skew(0,1);
        paint.setColor(Color.GREEN);
        canvas.drawRect(0,0,400,400,paint);

    }

    /**
     * 缩放
     *
     * @param canvas
     */
    private void initScale(Canvas canvas) {
        canvas.drawRect(200, 200, 700, 700, paint);
        // 第一个构造:
        // 第二个构造: 先translate(px,py)，再scale(sx,sy),再反向translate(-px,-py)
        //   canvas.scale(0.5f, 0.5f,200,200);
        //   ==
        //   canvas.translate(200,200);
        //   canvas.scale(0.5f,0.5f);
        //   canvas.translate(-200,-200);

        canvas.scale(0.5f, 0.5f,200,200);

//        canvas.translate(200, 200);
//        canvas.scale(0.5f, 0.5f);
//        canvas.translate(-200, -200);

        paint.setColor(Color.GREEN);
        canvas.drawRect(200, 200, 700, 700, paint);
    }

    /**
     * 旋转
     *
     * @param canvas
     */
    private void initRotate(Canvas canvas) {
        canvas.drawRect(100, 100, 700, 700, paint);
        //第二个构造方法：
        // canvas.rotate(degrees,px,py) // px,py代表旋转中心的坐标
        canvas.rotate(30 );
        paint.setColor(Color.GREEN);
        canvas.drawRect(0, 0, 400, 400, paint);
    }

    /**
     * 平移
     *
     * @param canvas
     */
    private void initTranslate(Canvas canvas) {
//        paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStrokeWidth(4);
//        paint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(0, 0, 400, 400, paint);
        canvas.translate(50, 50);
        paint.setColor(Color.GRAY);// 小米 4A 6.0 走2次 / 坚果 7.1.1 走1次
        canvas.drawRect(0, 0, 400, 400, paint);
        canvas.drawLine(0, 0, 600, 600, paint);

    }


}
