package com.live.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.*

fun Application.initActivityManager() {
    this.registerActivityLifecycleCallbacks(KtxLifeCycleCallBack())
}

class KtxLifeCycleCallBack : Application.ActivityLifecycleCallbacks {


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        "onActivityCreated : ${activity.localClassName}".logE()
        activityList.add(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        "onActivityStarted : ${activity.localClassName}".logE()
    }

    override fun onActivityResumed(activity: Activity) {
        "onActivityResumed : ${activity.localClassName}".logE()
    }

    override fun onActivityPaused(activity: Activity) {
        "onActivityPaused : ${activity.localClassName}".logE()
    }


    override fun onActivityDestroyed(activity: Activity) {
        "onActivityDestroyed : ${activity.localClassName}".logE()
        activityList.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity) {
        "onActivityStopped : ${activity.localClassName}".logE()
    }


}


private val activityList = LinkedList<Activity>()

val topActivity: Activity?
    get() =
        if (activityList.isEmpty()) null
        else activityList.last

fun exitApp() {
    for (activity in activityList)
        activity.finish()
}
