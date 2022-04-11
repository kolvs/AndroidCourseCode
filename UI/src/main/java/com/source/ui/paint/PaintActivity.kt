package com.source.ui.paint

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.source.ui.R;
import com.source.ui.paint.colorFilter.ColorFilterActivity
import com.source.ui.paint.demo.PwdEditTextActivity
import com.source.ui.paint.gradient.GradientActivity
import com.source.ui.paint.xfermode.XfermodeActivity
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

        /**
         * 自定义密码框
         */
        pwdEditText.setOnClickListener {
            startActivity(Intent(this, PwdEditTextActivity::class.java))
        }
    }
}
