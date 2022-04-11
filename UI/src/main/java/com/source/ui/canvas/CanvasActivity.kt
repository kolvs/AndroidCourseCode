package com.source.ui.canvas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.source.ui.R;

class CanvasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)
//        setContentView(TransformView(this))
//        setContentView(SaveRestoreView(this))
//        setContentView(SplitView(this))
//        setContentView(MyViewGroup(this))
//        setContentView(SplashView(this))
    }
}