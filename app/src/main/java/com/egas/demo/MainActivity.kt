package com.egas.demo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.DeadObjectException
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.view.View
import com.egas.demo.base.BaseActivity
import com.egas.demo.bean.User

class MainActivity : BaseActivity() {

    companion object {
        const val TAG = "客户端"
    }

    private var mRemoteServer: IUserAidlInterface? = null
    private var mService: IBinder? = null
    private var mConnected: Boolean = false

    private val aintent = Intent().apply {

        //action 和 package(app的包名)
        action = "com.egas.aidl.Server.Start"
        setPackage("com.egas.demo")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.button1).setOnClickListener {//开启服务
            connectService()
        }
        findViewById<View>(R.id.button3).setOnClickListener {//添加 user
            addPerson()
        }
        findViewById<View>(R.id.button5).setOnClickListener {
            addPersonOut()
        }
        findViewById<View>(R.id.button6).setOnClickListener {
            addPersonInout()
        }
        findViewById<View>(R.id.button7).setOnClickListener {
            addPersonOneway()
        }
        findViewById<View>(R.id.button2).setOnClickListener {//添加 listener
            addListener()
        }
        findViewById<View>(R.id.button4).setOnClickListener {//移除 listener
            removeListener()
        }
        findViewById<View>(R.id.button8).setOnClickListener {//移除 listener
            stopService()
        }
    }

    private fun connectService() {
        val isBind = bindService(aintent, serviceConnection, Context.BIND_AUTO_CREATE)
        Log.e(TAG, "bindService $isBind")
    }

    private fun addPerson() {
        //客户端调服务端方法时,需要捕获以下几个异常:
        try {
            val user = User("张花花")
            Log.e(TAG, "addPersonIn() 调用之前 user = $user")
            val addPersonResult = mRemoteServer?.addUser(user)
            Log.e(TAG, "addPersonIn() 调用之后 user = $user")
        } catch (e: RemoteException) {
            e.printStackTrace()
        } catch (e: DeadObjectException) { // 连接中断时会抛出异常；
            e.printStackTrace()
        } catch (e: SecurityException) {// 客户端和服务端中定义的 AIDL 发生冲突时会抛出异常；
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    private fun addPersonOut() {
        val user = User("张草草")
        Log.e(TAG, "addPersonOut() 调用之前 user = $user")
        val addPersonResult = mRemoteServer?.addUserOut(user)
        Log.e(TAG, "addPersonOut() 调用之前 user = $user")
    }

    private fun addPersonInout() {
        val user = User("张大树")
        Log.e(TAG, "addPersonInout() 调用之前 user = $user")
        val addPersonResult = mRemoteServer?.addUserInout(user)
        Log.e(TAG, "addPersonInout() 调用之前 user = $user")
    }

    private fun addPersonOneway() {
        Log.e(TAG, "oneway开始时间: ${System.currentTimeMillis()}")
        mRemoteServer?.addPersonOneway(User("oneway"))
        Log.e(TAG, "oneway结束时间: ${System.currentTimeMillis()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        removeListener()
        if (mConnected) {
            unbindService(serviceConnection)
        }
    }

    private fun addListener() {
        mRemoteServer?.addListener(listener)
    }

    private fun removeListener() {
        mRemoteServer?.asBinder()?.isBinderAlive?.let {
            mRemoteServer?.removeListener(listener)
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mConnected = true
            mService = service
            Log.e(TAG, "onServiceConnected, thread = ${Thread.currentThread().name}")
            service?.linkToDeath(mDeathRecipient, 0) //给binder设置一个死亡代理
            mRemoteServer = IUserAidlInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mConnected = false
            Log.e(TAG, "onServiceDisconnected, thread = ${Thread.currentThread().name}") //主线程
        }
    }

    private val mDeathRecipient = object : IBinder.DeathRecipient {
        override fun binderDied() {
            //子线程
            Log.e(TAG, "binder died, thread = ${Thread.currentThread().name}")//监听 binder died
            mService?.unlinkToDeath(this, 0) //移除死亡通知
            mService = null
            connectService() //重新连接
        }
    }

    private val listener = object : OnUserAddListener.Stub() {
        override fun onUserAdded(user: User) {
            Log.e(TAG, "onUserAdded, thread = ${Thread.currentThread().name} user = $user") //主线程
        }
    }

    private fun stopService() {
        unbindService(serviceConnection)
        stopService(aintent)
    }

}
