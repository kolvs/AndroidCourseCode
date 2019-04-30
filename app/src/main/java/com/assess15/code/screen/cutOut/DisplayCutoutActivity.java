package com.assess15.code.screen.cutOut;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.RelativeLayout;
import com.assess15.code.R;

/**
 * 刘海屏适配
 */
public class DisplayCutoutActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.P)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //1.设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //华为， 小米，oppo
        //1.判断手机厂商， 2，判断手机是否刘海， 3，设置是否让内容区域延伸进刘海 4，设置控件是否避开刘海区域  5， 获取刘海的高度

        //判断手机是否是刘海屏
        boolean hasDisplayCutout = hasDisplayCutout(window);
        if (hasDisplayCutout){
            //2.让内容区域延伸进刘海
            WindowManager.LayoutParams params = window.getAttributes();
            /**
             *  * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT 全屏模式，内容下移，非全屏不受影响
             *  * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES 允许内容去延伸进刘海区
             *  * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER 不允许内容延伸进刘海区
             */
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            window.setAttributes(params);

            //3.设置成沉浸式
            int flags = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            int visibility = window.getDecorView().getSystemUiVisibility();
            visibility |= flags; //追加沉浸式设置
            window.getDecorView().setSystemUiVisibility(visibility);
        }

        setContentView(R.layout.activity_cut_out);

//        Button button = findViewById(R.id.button);
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) button.getLayoutParams();
//        layoutParams.topMargin = heightForDisplayCutout();
//        button.setLayoutParams(layoutParams);

        RelativeLayout layout = findViewById(R.id.container);
        layout.setPadding(layout.getPaddingLeft(), heightForDisplayCutout(), layout.getPaddingRight(), layout.getPaddingBottom());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean hasDisplayCutout(Window window) {

        DisplayCutout displayCutout;
        View rootView = window.getDecorView();
        WindowInsets insets = rootView.getRootWindowInsets();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && insets != null){
            displayCutout = insets.getDisplayCutout();
            if (displayCutout != null){
                if (displayCutout.getBoundingRects() != null && displayCutout.getBoundingRects().size() > 0 && displayCutout.getSafeInsetTop() > 0){
                    return true;
                }
            }
        }
        return false; //因为模拟器原因，这里设置成true
    }

    //通常情况下，刘海的高就是状态栏的高
    public int heightForDisplayCutout(){
        int resID = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resID > 0){
            return getResources().getDimensionPixelSize(resID);
        }
        return 96;
    }
}
