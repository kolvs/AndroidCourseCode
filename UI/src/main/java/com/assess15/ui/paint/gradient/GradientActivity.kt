package com.assess15.ui.paint.gradient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GradientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(GradientLayout(this))
    }
}