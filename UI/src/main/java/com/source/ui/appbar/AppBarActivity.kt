package com.source.ui.appbar

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.source.ui.R;
import kotlinx.android.synthetic.main.activity_appbar.*

class AppBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appbar)
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)
//        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_back) // Java可以设置toolbar返回图标，xml也可以
        toolbar.setNavigationOnClickListener {
            // toolbar返回点击事件
            finish()
        }
    }

}