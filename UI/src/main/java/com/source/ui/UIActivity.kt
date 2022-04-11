package com.source.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.source.ui.appbar.AppBarActivity
import com.source.ui.canvas.CanvasActivity
import com.source.ui.coordinatorLayout.CoordinatorLayoutActivity
import com.source.ui.event.EventActivity
import com.source.ui.materialDesign.MaterialDesignActivity
import com.source.ui.paint.PaintActivity
import com.source.ui.path.PathActivity
import com.source.ui.pathMeasure.PathMeasureActivity
import com.source.ui.recyclerView.RecyclerViewActivity
import com.source.ui.statusBar.StatusBarActivity
import kotlinx.android.synthetic.main.activity_ui.*

/**
 * UI专题
 */
class UIActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui)
        initView()
    }

    private fun initView() {
        toolbar.setNavigationOnClickListener { finish() }

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
            startActivity(Intent(this, RecyclerViewActivity::class.java))
        }

        coordinator_Layout.setOnClickListener {
            startActivity(Intent(this, CoordinatorLayoutActivity::class.java))
        }

        status_bar.setOnClickListener {
            startActivity(Intent(this, StatusBarActivity::class.java))
        }
    }
}