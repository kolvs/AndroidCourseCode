package com.assess15.code.pathMeasure.demo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 实现效果：
 * 一条圆形路径长度为0慢慢增加到整个圆，如此往复
 * demo 出自：《Android自定义控件开发入门与实战》
 */
public class GetSegmentView extends View {

    private Paint paint;
    private Path circlePath;
    private Path dstPath;
    private PathMeasure pathMeasure;
    private float curAnimValue = 0f;

    public GetSegmentView(Context context) {
        this(context, null);
    }

    public GetSegmentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GetSegmentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 禁用硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);

        dstPath = new Path();// 创建用于存储截取后内容的 Path
        circlePath = new Path();
        circlePath.addCircle(100, 100, 50, Path.Direction.CW);

        pathMeasure = new PathMeasure(circlePath, true);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1); // 注意这里，使用的是float还是int
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curAnimValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

//        initWay1(canvas);

        initWay2(canvas);
    }

    private void initWay1(Canvas canvas) {
        float stop = pathMeasure.getLength() * curAnimValue;
        dstPath.reset();// 情况之前生成的路径
        pathMeasure.getSegment(0, stop, dstPath, true);
        canvas.drawPath(dstPath, paint);
    }

    private void initWay2(Canvas canvas) {
        float length = pathMeasure.getLength();
        float stop = length * curAnimValue;
        float start = (float) (stop - (0.5 - Math.abs(curAnimValue - 0.5)) * length);
        dstPath.reset();
        pathMeasure.getSegment(start, stop, dstPath, true);
        canvas.drawPath(dstPath, paint);
    }
}
