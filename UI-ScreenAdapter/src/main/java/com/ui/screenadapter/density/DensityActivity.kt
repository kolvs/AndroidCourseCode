package com.ui.screenadapter.density

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ui.screenadapter.databinding.ActivityDensityBinding

class DensityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityDensityBinding.inflate(layoutInflater)
        setContentView(inflate.root)

    }
}