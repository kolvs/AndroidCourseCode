package com.assess15.code.paint.gradient;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.assess15.code.R;

/**
 * 渐变色
 */
public class GradientLayout extends View {

    private Paint mPaint;
    private Shader mShader;
    private Bitmap mBitmap;

    public GradientLayout(Context context) {
        this(context, null);
    }

    public GradientLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        initPaint();

        // bitmap
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.girl);

        /**
         * 线性渲染器
         */
//        initLinearGradient();

        /**
         * 环形渲染器
         */
//        initRadialGradient();

        /**
         * 扫描渲染
         */
//        initSweepGradient();

        /**
         * 位图渲染器
         */
//        initBitmapShader();

        /**
         * 混合渲染器
         */
        initComposeShader();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED); // 设置颜色
        mPaint.setARGB(255, 255, 255, 0);// 设置Paint对象颜色，范围0-255
        mPaint.setAlpha(200);// 设置alpha不透明度，范围0-255
        mPaint.setAntiAlias(true);// 设置抗锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置描边效果  FILL/STROKE/FILL_AND_STROKE
        mPaint.setStrokeWidth(4); // 描边宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND);// 圆角效果 BUTT/ ROUND / SQUARE
        mPaint.setStrokeJoin(Paint.Join.MITER); // 拐角风格 MITER / ROUND / BEVEL
        mPaint.setShader(new SweepGradient(200, 200, Color.BLUE, Color.RED));// 设置环形渲染器  LinearGradient: RadialGradient: SweepGradient:BitmapShaper:ComposeShader:
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));// 设置图层混合模式
        mPaint.setColorFilter(new LightingColorFilter(0x00ffff, 0x00000));// 设置颜色过滤器
        mPaint.setFilterBitmap(true);// 设置双线性过滤
        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));// 设置画笔遮盖罩，传入度数和样式 NORMAL/SOLID/OUTER/INNER
        mPaint.setTextScaleX(2); // 设置文本缩放倍数
        mPaint.setTextSize(38);// 设置字体大小
        mPaint.setTextAlign(Paint.Align.LEFT); // 对齐方式
        mPaint.setUnderlineText(true);// 设置下划线

        String str = "测试";
        Rect rect = new Rect();
        mPaint.getTextBounds(str, 0, str.length(), rect);// 测量文本大小，将文本大小信息存放在rect中
        mPaint.measureText(str);//获取文本的宽度
        mPaint.getFontMetrics();// 获取字体度量对象
    }

    private void initLinearGradient() {
        mShader = new LinearGradient(0, 0, 500, 500, new int[]{Color.RED, Color.BLUE}, new float[]{0.5f, 1}, Shader.TileMode.CLAMP);
    }

    private void initRadialGradient() {
        mShader = new RadialGradient(250, 250, 250, new int[]{Color.RED, Color.BLUE, Color.GREEN}, null, Shader.TileMode.CLAMP);
    }

    private void initSweepGradient() {
        mShader = new SweepGradient(250, 250, Color.RED, Color.GREEN);
    }

    private void initBitmapShader() {
        mShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    private void initComposeShader() {
        BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        SweepGradient sweepGradient = new SweepGradient(250, 250, Color.RED, Color.GREEN);

        mShader = new ComposeShader(bitmapShader, sweepGradient, PorterDuff.Mode.MULTIPLY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setShader(mShader);
//        canvas.drawRect(0,0,500,500,mPaint);
        canvas.drawCircle(250, 250, 250, mPaint);
    }
}
