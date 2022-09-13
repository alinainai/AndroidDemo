package com.egas.demo

import android.app.Service
import android.content.Intent
import android.os.IBinder

class UserAidlService:Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}