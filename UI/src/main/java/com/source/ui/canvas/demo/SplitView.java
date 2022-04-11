package com.source.ui.canvas.demo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.source.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 粒子爆炸效果
 */
public class SplitView extends View {

    private Paint paint;
    private Bitmap bitmap;
    private float d = 3;// 粒子直径
    private List<Ball> mBall = new ArrayList<>();
    private ValueAnimator animator;

    public SplitView(Context context) {
        this(context, null);
    }

    public SplitView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        // 初始化画笔
        paint = new Paint();
        // 初始化图片
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher); // 如果图片太大，ANR
        // 遍历宽高,拿到每一个像素点
        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                // 对粒子球赋值
                Ball ball = new Ball();
                ball.color = bitmap.getPixel(i, j);
                ball.x = i * d + d / 2;
                ball.y = j * d + d / 2;
                ball.r = d / 2;

                ball.vX = (float) (Math.pow(-1, Math.ceil(Math.random() * 1000)) * 20 * Math.random());
                ball.vY = rangeInt(-15, 35);
                ball.aX = 0;
                ball.aY = 0.98f;
                mBall.add(ball);
            }
        }

        animator = ValueAnimator.ofFloat(0, 1);
        animator.setRepeatCount(-1);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateBall();
                invalidate();
            }
        });

    }

    // 动画结束，更新Ball
    private void updateBall() {
        for (Ball ball : mBall) {
            ball.x += ball.vX;
            ball.y += ball.vY;
            ball.vX += ball.aX;
            ball.vY += ball.aY;
        }
    }

    private float rangeInt(int i, int j) {
        int max = Math.max(i, j);
        int min = Math.min(i, j) - 1;
        return (int) (min + Math.ceil(Math.random() * (max - min)));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(500, 500);
        for (Ball ball : mBall) {
            paint.setColor(ball.color);
            canvas.drawCircle(ball.x, ball.y, ball.r, paint);
        }
    }

    // 点击球，执行动画
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            animator.start();
        }
        return super.onTouchEvent(event);
    }
}
