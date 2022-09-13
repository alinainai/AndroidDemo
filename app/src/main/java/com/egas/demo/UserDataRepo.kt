package com.egas.demo

import android.util.Log
import com.egas.demo.bean.User

object UserDataRepo {

    private val users = mutableListOf<User>().apply {
       add( User(1,"A","我是大A"))
       add( User(2,"B","我是大B"))
       add( User(3,"C","我是大C"))
    }

    fun addUser(user:User){
        Log.e("UserDataRepo","add user = $user")
        users.add(user)
    }

    fun getUsers() = users

}