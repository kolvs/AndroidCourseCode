package com.source.arch.aop.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.source.arch.R
import com.source.arch.aop.annotation.ClickBehavior
import com.source.arch.aop.annotation.LoginCheck

/**
 * 登录demo，aop kt写，不起作用
 */
class LoginActivity : AppCompatActivity() {
    var TAG = "tag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    // 登录点击事件（用户行为统计）
    @ClickBehavior("登录")
    fun login(view: View) {
        Log.e(TAG, "模拟接口请求……验证通过，登录成功！")
    }

    // 用户行为统计（友盟统计？！后台要求自己统计）
    @ClickBehavior("我的专区")
    @LoginCheck
    fun area(view: View) {
        Log.e(TAG, "开始跳转到 -> 我的专区 Activity")
        startActivity(Intent(this, OtherActivity::class.java))
    }

    // 用户行为统计
    @ClickBehavior("我的优惠券")
    @LoginCheck
    fun coupon(view: View) {
        Log.e(TAG, "开始跳转到 -> 我的优惠券 Activity")
        startActivity(Intent(this, OtherActivity::class.java))
    }

    // 用户行为统计
    @ClickBehavior("我的积分")
    @LoginCheck
    fun score(view: View) {
        Log.e(TAG, "开始跳转到 -> 我的积分 Activity")
        startActivity(Intent(this, OtherActivity::class.java))
    }

}