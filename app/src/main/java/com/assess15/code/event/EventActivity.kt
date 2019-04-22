package com.assess15.code.event

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.assess15.code.R
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        /**
         * OnTouchListener处于点击事件最顶端，权重最高
         */
        btn.setOnTouchListener(object : View.OnTouchListener {
            private val result = true

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (result) {
                    Log.d("tr", "返回true，不继续分发")
                } else {
                    Log.d("tr", "返回false，继续分发")
                }
                return result
            }
        })

        /**
         * onClickListener处于点击事件最末端，权重最低
         */
        btn.setOnClickListener {
            Log.d("tr", "OnClickListener")
            Toast.makeText(this, "拿不到点击事件", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Activity中分发事件
     * @return false  true
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val result = true
        if (result) {
            Toast.makeText(this,"dispatchTouchEvent，返回true",Toast.LENGTH_SHORT).show()
            Log.d("tr", "dispatchTouchEvent，返回true")
        } else {
            Toast.makeText(this,"dispatchTouchEvent，返回false",Toast.LENGTH_SHORT).show()
            Log.d("tr", "dispatchTouchEvent，返回false")
        }
        return result
    }

    /**
     * Activity中的点击事件
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Toast.makeText(this, "拿到Activity中，onTouchEvent点击事件", Toast.LENGTH_SHORT).show()
        Log.d("tr", "onTouchEvent，返回true")
        return true
    }

}