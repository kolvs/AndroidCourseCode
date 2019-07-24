package com.assess15.ui.paint.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.assess15.ui.R
import kotlinx.android.synthetic.main.activity_pwd_edit_text.*
import org.jetbrains.anko.toast

/**
 * 自定义密码框
 */
class PwdEditTextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pwd_edit_text)

        // 密码完成回调
        pwd.setOnPasswordFullListener {
            toast("密码完成：$it")
        }
    }
}