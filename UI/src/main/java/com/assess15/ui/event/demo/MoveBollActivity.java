package com.assess15.ui.event.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.assess15.ui.R;

public class MoveBollActivity extends AppCompatActivity implements View.OnTouchListener {

    private ImageView imageView;
    private RelativeLayout relativeLayout;

    private int lastX, lastY;    //保存手指点下的点的坐标
    final static int IMAGE_SIZE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_boll);

        // depresed
        imageView = findViewById(R.id.image);
        relativeLayout = findViewById(R.id.layout);
        //初始设置一个layoutParams
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(IMAGE_SIZE, IMAGE_SIZE);
        imageView.setLayoutParams(layoutParams);
        //设置屏幕触摸事件
        imageView.setOnTouchListener(this);

        // 这种方式移动小球可以
        findViewById(R.id.mi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MoveBollActivity.this, "点击了", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                //将点下的点的坐标保存
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();

                isClick(true);
                break;

            case MotionEvent.ACTION_MOVE:
                //计算出需要移动的距离
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                //将移动距离加上，现在本身距离边框的位置
                int left = view.getLeft() + dx;
                int top = view.getTop() + dy;
                //获取到layoutParams然后改变属性，在设置回去
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                layoutParams.height = IMAGE_SIZE;
                layoutParams.width = IMAGE_SIZE;
                layoutParams.leftMargin = left;
                layoutParams.topMargin = top;
                view.setLayoutParams(layoutParams);
                //记录最后一次移动的位置
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();

                isClick(false);
                break;
        }
        //刷新界面
        relativeLayout.invalidate();
        return true;
    }

    private void isClick(boolean b) {
        if (b)
            Toast.makeText(this, "点击了图片", Toast.LENGTH_LONG).show();
    }

}
