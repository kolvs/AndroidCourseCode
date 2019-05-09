package com.assess15.code.recyclerView;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import org.jetbrains.annotations.NotNull;

/**
 * 通过手势监听器，实现点击，长按
 */
public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private GestureDetectorCompat mGestureDetectorCompat;//手势探测器
    private RecyclerView mRecyclerView;

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mGestureDetectorCompat = new GestureDetectorCompat(mRecyclerView.getContext(), new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(@NotNull RecyclerView rv, @NotNull MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(@NotNull RecyclerView rv, @NotNull MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder);

    public abstract void onLongClick(RecyclerView.ViewHolder viewHolder);

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        //一次单独的轻触抬起手指操作，就是普通的点击事件
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
                onItemClick(childViewHolder);
            }
            return true;
        }

        //长按屏幕超过一定时长，就会触发，就是长按事件
        @Override
        public void onLongPress(MotionEvent e) {
            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
                onLongClick(childViewHolder);
            }
        }
    }
}

