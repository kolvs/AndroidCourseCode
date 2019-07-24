package com.assess15.ui.canvas.demo2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import com.assess15.ui.R;

/**
 * 分析：
 * 1. 旋转
 * 2. 扩散聚合 通过差值器，反向实现效果
 * 3. 水波纹
 * <p>
 * 属性动画，差值器
 */
public class SplashView2 extends View {

    // 背景色
    private int bgColor = Color.WHITE;
    // 属性动画
    private ValueAnimator valueAnimator;
    // 旋转动画时长
    private int rotateDuration = 1200;
    // 表示斜对角线长度的一半，扩散圆最大半径
    private float distance;
    // 3种状态
    private SplashState splashState;

    // 6个圆画笔
    private Paint paint;
    // 6个圆的颜色
    private int[] circleColor;
    // 6个圆半径
    private float circleRadius = 18;

    // 旋转圆中心坐标
    private float centerX, centerY;
    // 旋转大圆的半径
    private float rotateRadius = 90;

    // 扩散圆的画笔
    private Paint holePaint;
    // 当前大圆的半径
    private float currentRotateRadius = rotateRadius;
    // 当前大圆的旋转角度
    private float currentRotateAngle = 0f;
    // 扩散圆的半径
    private float currentHoleRadius = 0f;

    public SplashView2(Context context) {
        this(context, null);
    }

    public SplashView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplashView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // 6个小圆
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 6个小圆颜色数组
        circleColor = context.getResources().getIntArray(R.array.splash_circle_colors);

        // 扩散圆画笔
        holePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        holePaint.setStyle(Paint.Style.STROKE);
        holePaint.setColor(bgColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w * 1f / 2;
        centerY = h * 1f / 2;
        distance = (float) (Math.hypot(w, h) / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (splashState == null) {
            splashState = new RotateStatus();
        }
        splashState.drawState(canvas);
    }

    /**
     * 存放3种状态
     */
    private abstract class SplashState {
        abstract void drawState(Canvas canvas);
    }

    /**
     * 1. 旋转
     */
    private class RotateStatus extends SplashState {
        // 构造方法种初始化属性动画
        RotateStatus() {
            valueAnimator = ValueAnimator.ofFloat(0, (float) (Math.PI * 2)); // 参数代表 旋转一周
            valueAnimator.setInterpolator(new LinearInterpolator()); // 安卓动画默认：先加速，再减速
            valueAnimator.setDuration(rotateDuration);
            valueAnimator.setRepeatCount(2);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // 获取旋转角度
                    currentRotateAngle = (float) animation.getAnimatedValue();
                    // onDraw方法重新执行
                    invalidate();
                }
            });
            // 旋转完，切换动画；执行第二种动画
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    splashState = new MerginStatus();
                }
            });
            valueAnimator.start();
        }

        @Override
        void drawState(Canvas canvas) {
            // 绘制背景色
            drawBackground(canvas);
            // 绘制6个圆
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        // 获取2个圆之间的角度 360 / 6
        float rotateAngle = (float) (Math.PI * 2 / circleColor.length);
        // 根据6种颜色，获取6个球
        for (int i = 0; i < circleColor.length; i++) {
            // 获取第i个圆的角度 ；如果旋转的话需要加上角度
            float angle = i * rotateAngle + currentRotateAngle;
            // x = r * cos(a) + centerX
            // y = r * sin(a) + centerY
            float cx = (float) (currentRotateRadius * Math.cos(angle) + centerX);
            float cy = (float) (currentRotateRadius * Math.sin(angle) + centerY);
            paint.setColor(circleColor[i]);
            canvas.drawCircle(cx, cy, circleRadius, paint);
        }
    }

    private void drawBackground(Canvas canvas) {
        if (currentHoleRadius > 0) { // >0 表示进行到水波纹这步
            float strokeWidth = distance - currentHoleRadius;
            float radius = strokeWidth / 2 + currentHoleRadius;
            holePaint.setStrokeWidth(strokeWidth);
            canvas.drawCircle(centerX,centerY,radius,holePaint);
        } else {
            canvas.drawColor(bgColor);
        }
    }


    /**
     * 2. 扩散聚合
     */
    private class MerginStatus extends SplashState {
        MerginStatus() {
            // 核心改变rotateRadius大小实现缩放效果
            valueAnimator = ValueAnimator.ofFloat(circleRadius, rotateRadius); // 6个小圆到大圆缩放
            valueAnimator.setDuration(rotateDuration);
            valueAnimator.setInterpolator(new OvershootInterpolator(10f));
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentRotateRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            // 缩放完成，继续水波纹动画
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    splashState = new ExpandState();
                }
            });
            valueAnimator.reverse();
        }

        @Override
        void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawCircle(canvas);
        }
    }


    /**
     * 3. 水波纹
     */
    private class ExpandState extends SplashState {
        ExpandState() {
            valueAnimator = ValueAnimator.ofFloat(circleRadius, distance);
            valueAnimator.setDuration(rotateDuration);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentHoleRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.start();
        }

        @Override
        void drawState(Canvas canvas) {
            drawBackground(canvas);
        }
    }

}
