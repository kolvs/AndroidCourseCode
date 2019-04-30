package com.assess15.code.screen.density

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * 适配代码放在这里
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                Density.setDensity(this@App,activity)
            }

            override fun onActivityPaused(activity: Activity?) {}

            override fun onActivityResumed(activity: Activity?) {}

            override fun onActivityStarted(activity: Activity?) {}

            override fun onActivityDestroyed(activity: Activity?) {}

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

            override fun onActivityStopped(activity: Activity?) {}
        })
    }
}