package com.source.ui.recyclerView;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import org.jetbrains.annotations.NotNull;

/**
 * 基于 RecyclerView 实现的点击事件, 不建议使用，有局限性
 * 通常在Adapter中设置点击事件
 */
public abstract class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    protected abstract void onItemClick(View view, int position);

    // 手势监听器
    private GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context) {
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NotNull RecyclerView view, @NotNull MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mGestureDetector.onTouchEvent(e)) {
            onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NotNull RecyclerView view, @NotNull MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}