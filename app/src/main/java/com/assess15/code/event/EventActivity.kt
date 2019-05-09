package com.assess15.code.event

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.assess15.code.R
import com.assess15.code.event.demo.MoveBollActivity
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        /**
         * OnTouchListener处于点击事件最顶端，权重最高
         * @return false true
         */
        btn.setOnTouchListener(object : View.OnTouchListener {
            private val result = false

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
            startActivity(Intent(this,MoveBollActivity::class.java))
        }
    }

    /**
     * Activity中分发事件，是事件分发的开始
     * @return 默认调用父类dispatchTouchEvent方法
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val event = super.dispatchTouchEvent(ev)
        if (event) {
            Log.d("tr", "dispatchTouchEvent，返回----$event")
        } else {
            Log.d("tr", "dispatchTouchEvent，返回----$event")
        }
        return event
    }

    /**
     * Activity中的点击事件
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        val b = super.onTouchEvent(event)
        val b = false
        if (b) {
            Log.d("tr", "onTouchEvent，返回0000000$b")
        } else {
            Log.d("tr", "onTouchEvent，返回1111111$b")
        }
        return b
    }

}