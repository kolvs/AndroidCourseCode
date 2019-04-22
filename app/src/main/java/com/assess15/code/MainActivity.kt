package com.assess15.code

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.assess15.code.canvas.CanvasActivity
import com.assess15.code.event.EventActivity
import com.assess15.code.paint.PaintActivity
import com.assess15.code.paint.xfermode.XfermodeActivity
import com.assess15.code.path.PathActivity
import com.assess15.code.pathMeasure.PathMeasureActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        paint.setOnClickListener {
            startActivity(Intent(this, PaintActivity::class.java))
        }

        canvas.setOnClickListener {
            startActivity(Intent(this, CanvasActivity::class.java))
        }

        path.setOnClickListener {
            startActivity(Intent(this, PathActivity::class.java))
        }

        pathMeasure.setOnClickListener {
            startActivity(Intent(this, PathMeasureActivity::class.java))
        }

        event.setOnClickListener {
            startActivity(Intent(this, EventActivity::class.java))
        }
    }
}
