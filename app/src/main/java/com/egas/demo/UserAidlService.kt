package com.egas.demo

import android.app.Service
import android.content.Intent
import android.os.IBinder

import com.egas.demo.bean.User

class UserAidlService:Service() {
    override fun onBind(intent: Intent?): IBinder {
        return UserBinder()
    }

    private class UserBinder : IUserAidlInterface.Stub() {
        override fun getUsers():List<User> {
            return UserDataRepo.getUsers()
        }

        override fun addUser(user: User):Boolean {
            user.let {
                return UserDataRepo.addUser(user)
            }
        }
    }
}