package com.assess15.ui.path

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.assess15.ui.R;

class PathActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(PathView(this))
//        setContentView(DragBubbleView(this))
        setContentView(R.layout.activity_path)
    }
}
