package com.source.arch

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initAOP()
    }

    private fun initAOP(){
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                Log.d("f", activity?.componentName?.className)
            }
        })
    }
}