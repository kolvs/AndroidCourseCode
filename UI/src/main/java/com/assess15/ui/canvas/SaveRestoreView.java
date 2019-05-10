package com.assess15.ui.canvas;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class SaveRestoreView extends View {

    private Paint paint;

    public SaveRestoreView(Context context) {
        this(context, null);
    }

    public SaveRestoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SaveRestoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        initSaveRestore(canvas);

        initSaveLayer(canvas);

    }

    /**
     * saveLayer
     *
     * @param canvas
     */
    private void initSaveLayer(Canvas canvas) {
        canvas.drawRect(200, 200, 700, 700, paint);

        int layer = canvas.saveLayer(0, 0, 700, 700, paint);
        paint.setColor(Color.RED);
        Matrix matrix = new Matrix();
        matrix.setTranslate(100, 100);
        canvas.setMatrix(matrix);
        canvas.drawRect(0, 0, 700, 700, paint);// 平移，导致绘制的矩形超出图层大小，所以显示不全的
        canvas.restoreToCount(layer);

        paint.setColor(Color.GREEN);
        canvas.drawRect(0, 0, 100, 100, paint);

    }

    /**
     * save / restore
     *
     * @param canvas
     */
    private void initSaveRestore(Canvas canvas) {
//        canvas.drawColor(Color.RED);
//        canvas.save(); // 保存当前画布大小,即整屏
//        canvas.clipRect(new Rect(100, 100, 800, 800));
//        canvas.drawColor(Color.GREEN);
//        canvas.restore();// 恢复整屏画布
//        canvas.drawColor(Color.BLUE);
        Log.d("ta", canvas.getSaveCount() + "count");

        int save = canvas.save();

        canvas.drawColor(Color.RED);
        //保存的画布大小为全屏幕大小
        canvas.save();
        Log.d("ta", canvas.getSaveCount() + "count");

        canvas.clipRect(new Rect(100, 100, 800, 800));
        canvas.drawColor(Color.GREEN);
        //保存画布大小为Rect(100, 100, 800, 800)
        canvas.save();
        Log.d("ta", canvas.getSaveCount() + "count");

        canvas.clipRect(new Rect(200, 200, 700, 700));
        canvas.drawColor(Color.BLUE);
        //保存画布大小为Rect(200, 200, 700, 700)
        canvas.save();
        Log.d("ta", canvas.getSaveCount() + "count");

        canvas.clipRect(new Rect(300, 300, 600, 600));
        canvas.drawColor(Color.BLACK);
        //保存画布大小为Rect(300, 300, 600, 600)
        canvas.save();
        Log.d("ta", canvas.getSaveCount() + "count");

        canvas.clipRect(new Rect(400, 400, 500, 500));
        canvas.drawColor(Color.WHITE);

        canvas.restore();
        canvas.restore();
        canvas.restore();
        canvas.restore();
        Log.d("ta", canvas.getSaveCount() + "count");

        canvas.restoreToCount(save);
        Log.d("ta", canvas.getSaveCount() + "count");


    }

}
