package com.assess15.code

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.assess15.arch.ArchActivity
import com.assess15.ndk.NDKActivity
import com.assess15.ui.UIActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        toolbarMain.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbarMain)

        ui.setOnClickListener {
            startActivity(Intent(this, UIActivity::class.java))
        }

        ndk.setOnClickListener {
            startActivity(Intent(this, NDKActivity::class.java))
        }

        arch.setOnClickListener {
            startActivity(Intent(this, ArchActivity::class.java))
        }

        BuildConfig.debug // 获取到debug地址
    }
}
