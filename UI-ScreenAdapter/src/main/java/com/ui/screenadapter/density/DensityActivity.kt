package com.ui.screenadapter.density

import android.os.Bundle
import android.util.Log
import com.ui.screenadapter.databinding.ActivityDensityBinding

class DensityActivity : BaseActivity() {

    val tag = "屏幕信息====="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityDensityBinding.inflate(layoutInflater)
        setContentView(inflate.root)

        // 获取当前设备宽高及density
        val displayMetrics = resources.displayMetrics
        val density = displayMetrics.density
        val widthPixels = displayMetrics.widthPixels
        val heightPixels = displayMetrics.heightPixels
        val densityDpi = displayMetrics.densityDpi
        val scaledDensity = displayMetrics.scaledDensity
        Log.d(tag, "Density: ${density}")
        Log.d(tag, "宽度: ${widthPixels}px")
        Log.d(tag, "高度: ${heightPixels}px")
        Log.d(tag, "dpi: ${densityDpi}dpi")
    }
}