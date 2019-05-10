package com.assess15.ui.pathMeasure;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.assess15.ui.R;

public class PathMeasureView2 extends View {

    private Paint paint;
    private Paint linePaint;
    private Bitmap bitmap;
    private Path path;
    private Matrix matrix;
    private float aFloat;
    private float[] pos = new float[2];
    private float[] tan = new float[2];

    public PathMeasureView2(Context context) {
        this(context, null);
    }

    public PathMeasureView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMeasureView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(4);

        // 坐标系
        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(6);
        linePaint.setPathEffect(new DashPathEffect(new float[]{8,8},0));// 绘制虚线

        path = new Path();
        matrix = new Matrix();

        // 缩小图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 6;// 数值越大，图片越小
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow, options);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // x 轴直线
        canvas.drawLine(0, getHeight() / 2f, getWidth(), getHeight() / 2f, linePaint);
        // y 轴直线
        canvas.drawLine(getWidth() / 2f, 0, getWidth() / 2f, getHeight(), linePaint);
        canvas.translate(getWidth() / 2f, getHeight() / 2f);

//        initPathMeasure();
//        initPathMeasureRect(canvas);
//        initPathMeasureOval(canvas);
//        initArrow(canvas);
        initMatrix(canvas);
    }

    private void initMatrix(Canvas canvas) {
        path.reset();
        path.addCircle(0, 0, 300, Path.Direction.CW);
        canvas.drawPath(path, paint);

        aFloat += 0.01;
        if (aFloat >= 1) {
            aFloat = 0;
        }

        PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(path, false);
        pathMeasure.getMatrix(pathMeasure.getLength() * aFloat, matrix, PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);
        matrix.preTranslate(-bitmap.getWidth() / 2f, -bitmap.getHeight() / 2f);
        canvas.drawBitmap(bitmap, matrix, paint);
        invalidate();// 不断重绘，看似在旋转
    }

    private void initArrow(Canvas canvas) {
        path.reset();
        path.addCircle(0, 0, 200, Path.Direction.CW);
        canvas.drawPath(path, paint);

        aFloat += 0.01;
        if (aFloat > 0) {
            aFloat = 0;
        }

        PathMeasure pathMeasure = new PathMeasure(path, false);
        pathMeasure.getPosTan(pathMeasure.getLength() * aFloat, pos, tan);

        // 计算出当前切线与x轴夹角的度数
        double degrees = Math.atan2(tan[1], tan[0] * 180.0 / Math.PI);
        Log.d("tas", "onDraw : degrees = " + degrees);

        matrix.reset();
        // 进行角度旋转
        matrix.postRotate((float) degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        // 将图片的绘制点中心与当前点重合
        matrix.postTranslate(pos[0] - bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    private void initPathMeasureOval(Canvas canvas) {
        Path path = new Path();
        path.addRect(-100, -100, 100, 100, Path.Direction.CW);
        path.addOval(-200, -200, 200, 200, Path.Direction.CW);
        canvas.drawPath(path, paint);

        PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(path, false);
        pathMeasure.nextContour(); //跳转到下一条曲线
        Log.d("ta", "forceClosed : true " + pathMeasure.getLength());
    }

    private void initPathMeasureRect(Canvas canvas) {
        Path path = new Path();
        path.addRect(-200, -200, 200, 200, Path.Direction.CW); // 画矩形

        Path dst = new Path();
        dst.lineTo(-300, -300);

        PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.getSegment(200, 100, dst, true);

        canvas.drawPath(path, paint);
        canvas.drawPath(dst, linePaint);
    }

    private void initPathMeasure() {
        Path path = new Path();
        path.lineTo(0, 200);
        path.lineTo(300, 300);
        path.lineTo(200, 0);

        PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(path, true);

        Log.d("ta", "forceClosed : true " + pathMeasure.getLength());

        path.lineTo(200, -200);
        pathMeasure.setPath(path, true); //如果Path进行了调整，需要重新调用setPath方法进行关联，好像是废话
        Log.d("ta", "forceClosed : true " + pathMeasure.getLength());
    }
}
