package com.source.ui.path.demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import com.source.ui.R;

/**
 * QQ消息气泡效果
 */
public class DragBubbleView2 extends View {

    // 气泡半径
    private float bubbleRadius;
    // 气泡颜色
    private int bubbleColor;
    // 气泡中文字
    private String bubbleText;
    // 气泡中文字大小
    private float bubbleTextSize;
    // 气泡中文字颜色
    private int bubbleTextColor;
    // 气泡的画笔
    private Paint bubblePaint;
    // 贝塞尔曲线
    private Path bezierPath;
    // 气泡中文字画笔
    private Paint bubbleTextPaint;
    // 气泡中文字区域
    private Rect bubbleTextRect;

    // 固定气泡的半径
    private float bubbleFixedRadius;
    // 可动气泡的半径
    private float bubbleMovableRadius;
    // 不可动气泡的圆心
    private PointF bubbleFixedCenter;
    // 可动气泡的圆心
    private PointF bubbleMovableCenter;

    // 2气泡圆心距离
    private float dist;
    // 气泡相连状态最大圆心距离
    private float maxDist;
    // 手指触摸偏移量
    private float MOVE_OFFSET;

    // 气泡状态-静止
    private final int BUBBLE_STATE_DEFAULT = 0;
    // 气泡状态-相连
    private final int BUBBLE_STATE_CONNECT = 1;
    // 气泡状态-分离
    private final int BUBBLE_STATE_APART = 2;
    // 气泡状态-消失
    private final int BUBBLE_STATE_DISMISS = 3;
    // 气泡状态标志 默认静止状态
    private int bubbleState = BUBBLE_STATE_DEFAULT;

    // 气泡爆炸画笔
    private Paint bubbleBurstPaint;
    // 气泡爆炸区域
    private Rect bubbleBurstRect;
    // 气泡爆炸的图片id数组
    private int[] burstDrawableArray = {R.mipmap.burst_1, R.mipmap.burst_2, R.mipmap.burst_3, R.mipmap.burst_4, R.mipmap.burst_5};
    // 气泡爆炸bitmap数组
    private Bitmap[] burstBitmapArray;
    // 当前气泡爆炸图片index
    private int curDrawableIndex;

    public DragBubbleView2(Context context) {
        this(context, null);
    }

    public DragBubbleView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragBubbleView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DragBubbleView2, defStyleAttr, 0);
        bubbleRadius = typedArray.getDimension(R.styleable.DragBubbleView2_bubble_radius, bubbleRadius);
        bubbleColor = typedArray.getColor(R.styleable.DragBubbleView2_bubble_color, Color.RED);
        bubbleText = typedArray.getString(R.styleable.DragBubbleView2_bubble_text);
        bubbleTextSize = typedArray.getDimension(R.styleable.DragBubbleView2_bubble_textSize, bubbleTextSize);
        bubbleTextColor = typedArray.getColor(R.styleable.DragBubbleView2_bubble_textColor, bubbleTextColor);
        typedArray.recycle();

        // 2个圆半径大小一致
        bubbleFixedRadius = bubbleRadius;
        bubbleMovableRadius = bubbleFixedRadius;
        maxDist = 8 * bubbleRadius;
        // 偏移量
        MOVE_OFFSET = maxDist / 4;

        // 气泡画笔
        bubblePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bubblePaint.setColor(bubbleColor);
        bubblePaint.setStyle(Paint.Style.FILL);

        bezierPath = new Path();

        // 气泡中文字画笔
        bubbleTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bubbleTextPaint.setColor(bubbleTextColor);
        bubbleTextPaint.setStyle(Paint.Style.FILL);
        bubbleTextPaint.setTextSize(bubbleTextSize);
        bubbleTextRect = new Rect();

        // 爆炸画笔
        bubbleBurstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bubbleBurstPaint.setFilterBitmap(true);
        bubbleBurstRect = new Rect();
        burstBitmapArray = new Bitmap[burstDrawableArray.length];
        for (int i = 0; i < burstDrawableArray.length; i++) {
            // 将气泡爆炸的drawable转为bitmap
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), burstDrawableArray[i]);
            burstBitmapArray[i] = bitmap;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initWidthHeight(w, h);
    }

    private void initWidthHeight(int w, int h) {
        bubbleState = BUBBLE_STATE_DEFAULT;
        float width = (float) w / 2;
        float height = (float) h / 2;

        // 设置可动气泡圆心的初始坐标
        if (bubbleFixedCenter == null) {
            bubbleFixedCenter = new PointF(width, height);
        } else {
            bubbleFixedCenter.set(width, height);
        }
        // 设置可动气泡圆心的初始坐标
        if (bubbleMovableCenter == null) {
            bubbleMovableCenter = new PointF(width, height);
        } else {
            bubbleMovableCenter.set(width, height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 1. 连接情况绘制贝塞尔曲线 2.绘制圆背景及文本 3.另外端点绘制一个圆
        // 1.静止状态 2.连接状态 3.分离状态 4.消失
        if (bubbleState == BUBBLE_STATE_CONNECT) {
            // 绘制静止气泡
            canvas.drawCircle(bubbleFixedCenter.x, bubbleFixedCenter.y, bubbleFixedRadius, bubblePaint);
            // 计算控制点坐标
            int iAnchorX = (int) ((bubbleMovableCenter.x + bubbleFixedCenter.x) / 2);
            int iAnchorY = (int) ((bubbleMovableCenter.y + bubbleFixedCenter.y) / 2);

            float sinTheta = (bubbleMovableCenter.y - bubbleFixedCenter.y) / dist;
            float cosTheta = (bubbleMovableCenter.x - bubbleFixedCenter.x) / dist;

            // D
            float iBubFixedStartX = bubbleFixedCenter.x - bubbleFixedRadius * sinTheta;
            float iBubFixedStartY = bubbleFixedCenter.y + bubbleFixedRadius * cosTheta;
            // C
            float iBubMovableEndX = bubbleMovableCenter.x - bubbleMovableRadius * sinTheta;
            float iBubMovableEndY = bubbleMovableCenter.y + bubbleMovableRadius * cosTheta;
            // A
            float iBubFixedEndX = bubbleFixedCenter.x + bubbleFixedRadius * sinTheta;
            float iBubFixedEndY = bubbleFixedCenter.y - bubbleFixedRadius * cosTheta;
            // B
            float iBubMovableStartX = bubbleMovableCenter.x + bubbleMovableRadius * sinTheta;
            float iBubMovableStartY = bubbleMovableCenter.y - bubbleMovableRadius * cosTheta;

            // 绘制曲线
            bezierPath.reset();
            bezierPath.moveTo(iBubFixedStartX, iBubFixedStartY);
            bezierPath.quadTo(iAnchorX, iAnchorY, iBubMovableEndX, iBubMovableEndY);

            bezierPath.lineTo(iBubMovableStartX, iBubMovableStartY);
            bezierPath.quadTo(iAnchorX, iAnchorY, iBubFixedEndX, iBubFixedEndY);
            bezierPath.close();

            canvas.drawPath(bezierPath, bubblePaint);
        }

        // 静止，连接，分离状态都需要绘制圆背景以及文本
        if (bubbleState != BUBBLE_STATE_DISMISS) {
            canvas.drawCircle(bubbleMovableCenter.x, bubbleMovableCenter.y, bubbleMovableRadius, bubblePaint);
            bubbleTextPaint.getTextBounds(bubbleText, 0, bubbleText.length(), bubbleTextRect);
            canvas.drawText(bubbleText, bubbleMovableCenter.x - (float) (bubbleTextRect.width() / 2), bubbleMovableCenter.y + (float) (bubbleTextRect.width() / 2), bubbleTextPaint);
        }

        // 认为是消失状态，执行爆炸动画
        if (bubbleState == BUBBLE_STATE_DISMISS && curDrawableIndex < burstBitmapArray.length) {
            bubbleBurstRect.set(
                    (int) (bubbleMovableCenter.x - bubbleMovableRadius),
                    (int) (bubbleMovableCenter.y - bubbleMovableRadius),
                    (int) (bubbleMovableCenter.x + bubbleMovableRadius),
                    (int) (bubbleMovableCenter.y + bubbleMovableRadius));
            canvas.drawBitmap(burstBitmapArray[curDrawableIndex], null, bubbleBurstRect, bubblePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (bubbleState != BUBBLE_STATE_DISMISS) {
                    dist = (float) Math.hypot(event.getX() - bubbleFixedCenter.x, event.getY() - bubbleFixedCenter.y);
                    if (dist < bubbleRadius + MOVE_OFFSET) {
                        bubbleState = BUBBLE_STATE_CONNECT;
                    } else {
                        bubbleState = BUBBLE_STATE_DEFAULT;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (bubbleState != BUBBLE_STATE_DEFAULT) {
                    dist = (float) Math.hypot(event.getX() - bubbleFixedCenter.x, event.getY() - bubbleFixedCenter.y);
                    bubbleMovableCenter.x = event.getX();
                    bubbleMovableCenter.y = event.getY();
                    if (bubbleState == BUBBLE_STATE_CONNECT) {
                        if (dist < maxDist - MOVE_OFFSET) {
                            bubbleFixedRadius = bubbleRadius - dist / 8;
                        } else {
                            bubbleState = BUBBLE_STATE_APART;
                        }
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (bubbleState == BUBBLE_STATE_CONNECT) {
                    // 橡皮筋动画
                    startBubbleRestAnim();
                } else if (bubbleState == BUBBLE_STATE_APART) {
                    if (dist < 2 * bubbleRadius) {
                        // 反弹动画
                        startBubbleRestAnim();
                    } else {
                        // 爆炸动画
                        startBubbleBurstAnim();
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 连接状态下松开手指，执行类似橡皮筋动画
     */
    private void startBubbleRestAnim() {
        ValueAnimator animator = ValueAnimator.ofObject(
                new PointFEvaluator(),
                new PointF(bubbleMovableCenter.x, bubbleMovableCenter.y),
                new PointF(bubbleFixedCenter.x, bubbleFixedCenter.y));
        animator.setDuration(200);
        animator.setInterpolator(new OvershootInterpolator(5f));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                bubbleMovableCenter = (PointF) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                bubbleState = BUBBLE_STATE_DEFAULT;
            }
        });

        animator.start();
    }

    /**
     * 爆炸动画
     */
    private void startBubbleBurstAnim() {
        // 气泡消失
        bubbleState = BUBBLE_STATE_DISMISS;
        ValueAnimator animator = ValueAnimator.ofInt(0,burstBitmapArray.length);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curDrawableIndex = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }
}
