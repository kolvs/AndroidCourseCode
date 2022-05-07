package com.ui.screenadapter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ui.screenadapter.cutOut.DisplayCutoutActivity
import com.ui.screenadapter.databinding.ActivityMainBinding
import com.ui.screenadapter.density.DensityActivity
import com.ui.screenadapter.pixel.ScreenActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityMainBinding.inflate(layoutInflater)
        setContentView(inflate.root)
        inflate.btn1.setOnClickListener {
            startActivity(Intent(this, ScreenActivity::class.java))
        }
        inflate.btn2.setOnClickListener {
            startActivity(Intent(this, DisplayCutoutActivity::class.java))
        }
        inflate.btn3.setOnClickListener {
            startActivity(Intent(this, DensityActivity::class.java))
        }
    }
}