package com.assess15.code.pathMeasure;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class PathMeasureView extends View {

    public PathMeasureView(Context context) {
        this(context, null);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }
}
