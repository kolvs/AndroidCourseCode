package com.assess15.ui.path;

import android.content.Context;
import android.graphics.*;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class PathView extends View {


    private Paint paint;
    private Path path;

    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 一级贝塞尔，一条直线，无控制线
         */
//        initOne();

        /**
         * 添加子图形
         */
//        initAdd();

        /**
         * 追加图形
         */
//        initArc();

        /**
         * 添加一个路径
         */
//        initTo();

        /**
         * 添加圆角矩形，CW顺时针，CCW逆时针
         */
//        initRect();

        /**
         * 二阶贝塞尔，一个控制点
         */
//        initTwo();

        /**
         * 三阶贝塞尔，2个控制点
         */
        initThree();


        canvas.drawPath(path, paint);
    }

    private void initThree() {
        path.moveTo(300, 500);
//        path.cubicTo(500,100,600,1200,800,500);
        path.rCubicTo(200, -400, 300, 700, 500, 0);
    }

    private void initTwo() {
        path.moveTo(0, 500);
        path.quadTo(100, 100, 800, 500);
        path.rQuadTo(200, -400, 500, 0);
    }

    private void initRect() {
        RectF rectf = new RectF(200, 500, 700, 1000);
        path.addRoundRect(rectf, 20, 20, Path.Direction.CCW);
    }

    private void initTo() {
        path.moveTo(100, 70);
        path.lineTo(140, 180);
        path.lineTo(250, 250);
        path.lineTo(400, 630);
        path.lineTo(100, 830);

        Path pt = new Path();
        pt.moveTo(100, 1000);
        pt.lineTo(600, 1300);
        pt.lineTo(400, 1700);
        path.addPath(pt);
    }

    private void initArc() {
        //forceMoveTo，true，绘制时移动起点，false，绘制时连接最后一个点与圆弧起点
        path.arcTo(400, 200, 600, 400, -180, 225, false);
        path.moveTo(0, 0);
        path.lineTo(100, 100);
        path.arcTo(400, 200, 600, 400, 0, 270, false);
    }

    private void initAdd() {
        // 添加弧形
        path.addArc(200, 200, 400, 400, -225, 225);
        // 添加矩形 Path.Direction.CW表示顺时针方向绘制，CCW表示逆时针方向
        path.addRect(500, 500, 900, 900, Path.Direction.CCW);
        path.addCircle(200, 200, 200, Path.Direction.CCW);
        path.addOval(0, 0, 500, 300, Path.Direction.CW);
    }

    private void initOne() {
        paint.setStyle(Paint.Style.FILL);
        path.moveTo(100, 70);
        path.lineTo(140, 800);
        path.rLineTo(40, 730);
        path.lineTo(250, 600);
        path.close(); // 设置是否闭合
    }
}
