package com.egas.demo

import android.app.Service
import android.content.Intent
import android.os.IBinder

import com.egas.demo.bean.User

class UserAidlService:Service() {

    private val users = mutableListOf<User>()

    override fun onBind(intent: Intent?): IBinder {
        return userBinder
    }

    private val userBinder = object :IUserAidlInterface.Stub() {
        override fun addUser(user: User):Boolean {
             return users.add(user)
        }
    }
}