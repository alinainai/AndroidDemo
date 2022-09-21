package com.egas.demo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.DeadObjectException
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.view.View
import com.egas.demo.base.BaseActivity
import com.egas.demo.bean.User
import kotlin.random.Random

class MainActivity : BaseActivity() {
    companion object{
        const val TAG  = "MainActivity"
    }

    private var mRemoteServer: IUserAidlInterface? = null
    private var mService: IBinder? = null
    private var mHasBind:Boolean = false
    private var num = 3


    private val mDeathRecipient = object : IBinder.DeathRecipient {
        override fun binderDied() {
            //子线程
            Log.e(TAG, "binder died, thread = ${Thread.currentThread().name}")//监听 binder died
            mService?.unlinkToDeath(this, 0) //移除死亡通知
            mService = null
            connectService() //重新连接
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mService = service
            Log.e(TAG, "onServiceConnected, thread = ${Thread.currentThread().name}")
            service?.linkToDeath(mDeathRecipient, 0) //给binder设置一个死亡代理
            //在 onServiceConnected 调用 IPersonManager.Stub.asInterface 获取接口类型的实例，通过这个实例调用服务端的服务
            mRemoteServer = IUserAidlInterface.Stub.asInterface(service)
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e(TAG, "onServiceDisconnected, thread = ${Thread.currentThread().name}") //主线程
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.button1).setOnClickListener {//开启服务
            connectService()
        }
        findViewById<View>(R.id.button2).setOnClickListener {//关闭服务

        }
        findViewById<View>(R.id.button3).setOnClickListener {//添加 user
            addPerson()
        }
        findViewById<View>(R.id.button4).setOnClickListener {//获取Users
            getPersons()
        }
    }

    private fun connectService() {
        val intent = Intent()
        //action 和 package(app的包名)
        intent.action = "com.egas.aidl.Server.Start"
        intent.setPackage("com.egas.demo")
        mHasBind = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        Log.e(TAG, "bindService $mHasBind")
    }

    private fun addPerson() {
        //客户端调服务端方法时,需要捕获以下几个异常:
        //RemoteException 异常：
        //DeadObjectException 异常：连接中断时会抛出异常；
        //SecurityException 异常：客户端和服务端中定义的 AIDL 发生冲突时会抛出异常；
        try {
            val x = Random(100).nextInt()
            val addPersonResult = mRemoteServer?.addUser(User(num++,"小${x}","我是${x}"))
            Log.e(TAG, "addPerson result = $addPersonResult")
        } catch (e: RemoteException) {
            e.printStackTrace()
        } catch (e: DeadObjectException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    private fun getPersons() {
        val personList = mRemoteServer?.users
        personList?.forEach { user ->
            Log.e(TAG, "user ${user}")
        }
        //log(TAG, "person 列表 $personList")
    }

    override fun onDestroy() {
        super.onDestroy()
        //最后记得unbindService
//        unregisterListener()
        if (mHasBind) {
            unbindService(serviceConnection)
        }
    }
}