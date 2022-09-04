package com.egas.demo.base

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.text.TextUtils
import android.util.Log


class BaseApp:Application() {

    override fun onCreate() {
        super.onCreate()
        val processName = getProcessName(this, android.os.Process.myPid())
        //判断进程名，保证只有主进程运行
        if (!TextUtils.isEmpty(processName) && processName.equals(this.packageName)) {
            Log.e("app","app main")
        }
    }

    private fun getProcessName(cxt: Context, pid: Int): String? {
        val am = cxt.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningApps = am.runningAppProcesses ?: return null
        for (procInfo in runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName
            }
        }
        return null
    }


}