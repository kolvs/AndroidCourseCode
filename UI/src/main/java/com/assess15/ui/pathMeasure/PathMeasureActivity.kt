package com.assess15.ui.pathMeasure

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.assess15.ui.R;
import kotlinx.android.synthetic.main.activity_path_measure.*

class PathMeasureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_path_measure)
//        setContentView(PathMeasureView2(this))
//        setContentView(GetSegmentView(this))

        init()
    }

    private fun init() {
        apv.setLoading()
        btnSuccess.setOnClickListener {
            apv.setLoadingSuccess()
        }
        btnFail.setOnClickListener {
            apv.setLoadingFail()
        }
    }
}