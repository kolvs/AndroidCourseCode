package com.assess15.code.materialDesign;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;
import com.assess15.code.R;
import com.assess15.code.materialDesign.utils.SystemBarHelper;

/**
 * 大会员界面
 */

public class VipActivity extends BaseActivity {

    Toolbar mToolbar;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    WebView mWebView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_vip;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 允许使用transitions
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        //淡入动画
//        getWindow().setEnterTransition(new Fade());
//        滑动动画
//        getWindow().setEnterTransition(new Slide());
        // 分解动画
        getWindow().setEnterTransition(new Explode());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mToolbar = findViewById(R.id.toolbar);
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        mWebView = findViewById(R.id.webView);
        mWebView.loadUrl("http://vip.bilibili.com/site/vip-faq-h5.html#yv1");
//        mWebView.loadUrl("https://www.jianshu.com/p/b58ef6b0624b");
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        //设置StatusBar透明
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, mToolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
