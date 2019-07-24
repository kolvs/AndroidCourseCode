package com.assess15.arch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.assess15.arch.aop.login.LoginActivity
import com.assess15.arch.test.MainLoginActivity
import kotlinx.android.synthetic.main.activity_arch.*
import org.jetbrains.anko.startActivity

class ArchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arch)
        initView()
    }

    private fun initView() {
        btnLoginDemo.setOnClickListener {
            startActivity<LoginActivity>()
//            startActivity<MainLoginActivity>()
        }
    }
}