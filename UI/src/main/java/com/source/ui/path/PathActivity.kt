package com.source.ui.path

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.source.ui.R;

class PathActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(PathView(this))
//        setContentView(DragBubbleView(this))
        setContentView(R.layout.activity_path)
    }
}
