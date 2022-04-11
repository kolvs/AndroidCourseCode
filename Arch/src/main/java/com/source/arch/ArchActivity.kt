package com.source.arch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.source.arch.aop.login.LoginActivity
import com.source.arch.databinding.ActivityArchBinding

class ArchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityArchBinding.inflate(layoutInflater)
        setContentView(inflate.root)
        inflate.btnLoginDemo.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
//            startActivity<MainLoginActivity>()
        }
    }
}