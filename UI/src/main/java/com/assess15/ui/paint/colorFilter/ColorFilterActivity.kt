package com.assess15.ui.paint.colorFilter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ColorFilterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ColorFilterView(this))
    }
}