package com.assess15.code.paint.gradient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class GradientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(GradientLayout(this))
    }
}