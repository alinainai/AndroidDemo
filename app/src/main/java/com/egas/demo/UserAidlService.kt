package com.egas.demo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import android.os.RemoteException
import android.util.Log
import com.egas.demo.bean.User
import java.util.concurrent.CopyOnWriteArrayList


class UserAidlService : Service() {

    private val users = CopyOnWriteArrayList<User>() // 防止多线程引起的数据错乱问题

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    //存储注册监听客户端集合
    private val mListeners =  RemoteCallbackList<OnUserAddListener>()

    override fun onBind(intent: Intent?): IBinder {
        return userBinder
    }

    private val userBinder = object : IUserAidlInterface.Stub() {
        override fun addUser(user: User): Boolean {
            Log.e("服务端", "addUser: user = $user")
            user.name = "被addPersonIn修改"
            if(user.name=="xxx"){
               val a = 3/0
            }
            addUserProcess(user)
            return users.add(user)
        }

        override fun addUserOut(user: User): Boolean {
            Log.e("服务端", "addUserOut: user = $user")
            user.name = "被addPersonOut修改"
            addUserProcess(user)
            return users.add(user)
        }

        override fun addUserInout(user: User): Boolean {
            Log.e("服务端", "addUserInout: user = $user")
            user.name = "被addPersonInout修改"
            addUserProcess(user)
            return users.add(user)
        }

        override fun addPersonOneway(user: User) {
            Thread.sleep(2000)
            users.add(user)
            addUserProcess(user)
            Log.e("服务端", "addPersonOneway: user = $user")
        }

        override fun addListener(listener: OnUserAddListener?) {
            mListeners.register(listener)
        }

        override fun removeListener(listener: OnUserAddListener?) {
            mListeners.unregister(listener)
        }
    }

    private fun addUserProcess(user:User){
        synchronized(mListeners) {
            val n = mListeners.beginBroadcast()
            try {
                for (i in 0 until n) {
                    val listener: OnUserAddListener = mListeners.getBroadcastItem(i)
                    listener.onUserAdded(user)
                }
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            mListeners.finishBroadcast()
        }
    }
}
