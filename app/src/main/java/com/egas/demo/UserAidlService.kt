package com.egas.demo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

import com.egas.demo.bean.User

const val UserAidlService_TAG = "服务端"

class UserAidlService:Service() {

    private val users = mutableListOf<User>()

    override fun onBind(intent: Intent?): IBinder {
        return userBinder
    }

    private val userBinder = object :IUserAidlInterface.Stub() {
        override fun addUser(user: User):Boolean {
            Log.e(UserAidlService_TAG, "addUser user = $user")
             return users.add(user)
        }
    }
}
