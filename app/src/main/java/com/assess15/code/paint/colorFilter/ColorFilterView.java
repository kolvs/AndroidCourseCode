package com.assess15.code.paint.colorFilter;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.assess15.code.R;

public class ColorFilterView extends View {

    private Paint paint;
    private Bitmap bitmap;

    public ColorFilterView(Context context) {
        this(context, null);
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.girl);

        /**
         * LightingColorFilter滤镜
         */
//        initLightingColorFilter();

        /**
         *  PorterDuffColorFilter滤镜
         */
//        initPorterDuffColorFilter();

        /**
         * ColorMatrix
         */
        initColorMatrix();
    }

    private void initLightingColorFilter() {
        // 0xffffff 原始颜色 / 0x00ffff 红色去除 / 0xffffff，0x003000 绿色更亮
        LightingColorFilter lighting = new LightingColorFilter(0xffffff, 0x000000);
        paint.setColorFilter(lighting);
    }

    private void initPorterDuffColorFilter() {
        PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.DARKEN);
        paint.setColorFilter(filter);
    }

    private void initColorMatrix() {
        ColorMatrix cm = new ColorMatrix();
        //亮度调节
//        cm.setScale(1,2,1,1);
        //饱和度调节0-无色彩， 1- 默认效果， >1饱和度加强
//        cm.setSaturation(2);
        //色调调节
        cm.setRotate(0, 45);

        float[] array = {
                1, 0, 0, 0, 0,// red
                0, 1, 0, 0, 0,// green
                0, 0, 1, 0, 0,// blue
                0, 0, 0, 1, 0 // alpha
        };

//        ColorMatrixColorFilter matrixColorFilter = new ColorMatrixColorFilter(array); // array | matrix
        ColorMatrixColorFilter matrixColorFilter = new ColorMatrixColorFilter(ColorFilter.colormatrix_huajiu); // array | matrix
        paint.setColorFilter(matrixColorFilter);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, 0, 0, paint);
    }
}
