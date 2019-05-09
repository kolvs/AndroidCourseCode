package com.assess15.code.paint

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.assess15.code.R
import com.assess15.code.paint.colorFilter.CFActivity
import com.assess15.code.paint.colorFilter.ColorFilterActivity
import com.assess15.code.paint.gradient.GradientActivity
import com.assess15.code.paint.xfermode.XfermodeActivity
import kotlinx.android.synthetic.main.activity_paint.*

class PaintActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paint)

        /**
         * 渐变色
         */
        paintGradient.setOnClickListener {
            startActivity(Intent(this, GradientActivity::class.java))
        }

        /**
         * xfermode
         */
        paintXfermode.setOnClickListener {
            startActivity(Intent(this, XfermodeActivity::class.java))
        }

        /**
         * 滤镜
         */
        paintColorFilter.setOnClickListener {
            startActivity(Intent(this, ColorFilterActivity::class.java))
//            startActivity(Intent(this, CFActivity::class.java))
        }

    }
}
