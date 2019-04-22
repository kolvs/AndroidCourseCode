package com.assess15.code.pathMeasure.demo;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.assess15.code.R;

public class AliPayView extends View {

    // 自定义属性相关
    private float progressWidth;
    private float progressRadius;
    private int progressColor;
    private int loadingSuccessColor;
    private int loadingFailColor;

    private Paint paint;
    private Path pathCircle;
    private PathMeasure pathMeasure;
    private Path successPath;
    private Path failPathLeft;
    private Path failPathRight;
    private Path pathCircleDst;

    private float circleValue;
    private ValueAnimator circleAnimator;

    PayState payState;

    private int startAngle = -90;
    private int minAngle = -90;
    private int sweepAngle = 120;
    private int curAngle = 0;

    private float successValue;
    private float failValueLeft;
    private float failValueRight;

    // 枚举3中状态
    enum PayState {
        Loading, Success, Fail
    }

    public AliPayView(Context context) {
        this(context, null);
    }

    public AliPayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AliPayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AliPayView, defStyleAttr, 0);
        progressWidth = typedArray.getDimension(R.styleable.AliPayView_progress_width, 6);
        progressRadius = typedArray.getDimension(R.styleable.AliPayView_progress_radius, 100);
        progressColor = typedArray.getColor(R.styleable.AliPayView_progress_color, Color.GRAY);
        loadingFailColor = typedArray.getColor(R.styleable.AliPayView_loading_fail_color, Color.RED);
        loadingSuccessColor = typedArray.getColor(R.styleable.AliPayView_loading_success_color, Color.GREEN);
        typedArray.recycle();//  为什么要回收？

        initPaint();
        initPath();
        initAnim();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setDither(true);
        paint.setColor(progressColor);
        paint.setStrokeWidth(progressWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);// 设置画笔为圆角笔触
    }

    private void initPath() {
        pathCircle = new Path();
        pathMeasure = new PathMeasure();
        pathCircleDst = new Path();
        successPath = new Path();
        failPathLeft = new Path();
        failPathRight = new Path();
    }

    private void initAnim() {
        circleAnimator = ValueAnimator.ofFloat(0, 1);
        circleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getPaddingLeft(), getPaddingTop());//将当前画布的点移到getPaddingLeft,getPaddingTop,后面的操作都以该点作为参照点

        switch (payState) {
            case Loading:
                initLoading(canvas);
                break;
            case Success:
                initSuccess(canvas);
                break;
            case Fail:
                initFail(canvas);
                break;
        }
    }

    /**
     * 正在加载
     *
     * @param canvas
     */
    private void initLoading(Canvas canvas) {
        if (startAngle == minAngle) {
            sweepAngle += 6;
        }
        if (sweepAngle >= 300 || startAngle > minAngle) {
            startAngle += 6;
            if (sweepAngle > 20) {
                sweepAngle -= 6;
            }
        }
        if (startAngle > minAngle + 300) {
            startAngle %= 360;
            minAngle = startAngle;
            sweepAngle = 20;
        }
        canvas.rotate(curAngle += 4, progressRadius, progressRadius);//旋转的弧长为4
        canvas.drawArc(new RectF(0, 0, progressRadius * 2, progressRadius * 2), startAngle, sweepAngle, false, paint);
        invalidate();
    }

    /**
     * 加载成功
     *
     * @param canvas
     */
    private void initSuccess(Canvas canvas) {
        paint.setColor(loadingSuccessColor);
        pathCircle.addCircle(getWidth() / 2f, getHeight() / 2f, progressRadius, Path.Direction.CW);
        pathMeasure.setPath(pathCircle, false);
        pathMeasure.getSegment(0, circleValue * pathMeasure.getLength(), pathCircleDst, true);//截取path并保存到mPathCircleDst中
        canvas.drawPath(pathCircleDst, paint);

        if (circleValue == 1) { //表示圆画完了,可以画钩了
            successPath.moveTo(getWidth() / 8f * 3, getHeight() / 2f);
            successPath.lineTo(getWidth() / 2f, getHeight() / 5f * 3);
            successPath.lineTo(getWidth() / 3f * 2, getHeight() / 5f * 2);
            pathMeasure.nextContour();
            pathMeasure.setPath(successPath, false);
            pathMeasure.getSegment(0, successValue * pathMeasure.getLength(), pathCircleDst, true);
            canvas.drawPath(pathCircleDst, paint);
        }
    }

    /**
     * 加载失败
     *
     * @param canvas
     */
    private void initFail(Canvas canvas) {
        paint.setColor(loadingFailColor);
        pathCircle.addCircle(getWidth() / 2f, getHeight() / 2f, progressRadius, Path.Direction.CW);
        pathMeasure.setPath(pathCircle, false);
        pathMeasure.getSegment(0, circleValue * pathMeasure.getLength(), pathCircleDst, true);
        canvas.drawPath(pathCircleDst, paint);

        if (circleValue == 1) {//表示圆画完了,可以画叉叉的右边部分
            failPathRight.moveTo(getWidth() / 3f * 2, getHeight() / 3f);
            failPathRight.lineTo(getWidth() / 3f, getHeight() / 3f * 2);
            pathMeasure.nextContour();
            pathMeasure.setPath(failPathRight, false);
            pathMeasure.getSegment(0, failValueRight * pathMeasure.getLength(), pathCircleDst, true);
            canvas.drawPath(pathCircleDst, paint);
        }
        if (failValueRight == 1) { //表示叉叉的右边部分画完了,可以画叉叉的左边部分
            failPathLeft.moveTo(getWidth() / 3f, getHeight() / 3f);
            failPathLeft.lineTo(getWidth() / 3f * 2, getHeight() / 3f * 2);
            pathMeasure.nextContour();
            pathMeasure.setPath(failPathLeft, false);
            pathMeasure.getSegment(0, failValueLeft * pathMeasure.getLength(), pathCircleDst, true);
            canvas.drawPath(pathCircleDst, paint);
        }
    }

    private void setPayState(PayState state) {
        payState = state;
    }

    public void setLoading() {
        setPayState(PayState.Loading);
    }

    public void setLoadingSuccess() {
        resetPath();
        setPayState(PayState.Success);
        startSuccessAnim();
    }

    public void setLoadingFail() {
        resetPath();
        setPayState(PayState.Fail);
        startFailAnim();
    }

    /**
     * 失败/成功 重置
     */
    private void resetPath() {
        successPath.reset();
        failPathLeft.reset();
        failPathRight.reset();
        pathCircle.reset();
        pathCircleDst.reset();
        successValue = 0;
        circleValue = 0;
        failValueLeft = 0;
        failValueRight = 0;
    }

    /**
     * 失败动画
     */
    private void startFailAnim() {
        ValueAnimator failLeft = ValueAnimator.ofFloat(0, 1);
        failLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                failValueLeft = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator failRight = ValueAnimator.ofFloat(0, 1);
        failRight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                failValueRight = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        //组合动画,一先一后执行
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(failRight).after(circleAnimator).before(failLeft);// 先执行失败左边，后右边
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    /**
     * 成功动画
     */
    private void startSuccessAnim() {
        ValueAnimator success = ValueAnimator.ofFloat(0, 1);
        success.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                successValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(success).after(circleAnimator);
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;

        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else {
            width = (int) (2 * progressRadius + progressWidth + getPaddingLeft() + getPaddingRight());
        }

        mode = MeasureSpec.getMode(heightMeasureSpec);
        size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else {
            height = (int) (2 * progressRadius + progressWidth + getPaddingTop() + getPaddingBottom());
        }

        setMeasuredDimension(width, height);
    }
}
