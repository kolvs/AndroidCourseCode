package com.source.ui.paint.colorFilter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ColorFilterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ColorFilterView(this))
    }
}