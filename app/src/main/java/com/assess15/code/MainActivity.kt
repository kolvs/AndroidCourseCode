package com.assess15.code

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.assess15.code.appbar.AppBarActivity
import com.assess15.code.canvas.CanvasActivity
import com.assess15.code.event.EventActivity
import com.assess15.code.materialDesign.MaterialDesignActivity
import com.assess15.code.paint.PaintActivity
import com.assess15.code.path.PathActivity
import com.assess15.code.pathMeasure.PathMeasureActivity
import com.assess15.code.recyclerView.RecyclerViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        toolbarMain.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbarMain)

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

        appbar.setOnClickListener {
            startActivity(Intent(this, AppBarActivity::class.java))
        }

        materialDesign.setOnClickListener {
            startActivity(Intent(this, MaterialDesignActivity::class.java))
        }

        recyclerView.setOnClickListener {
            startActivity(Intent(this,RecyclerViewActivity::class.java))
        }
    }
}
