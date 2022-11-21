package com.egas.demo.proxy

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity

class ProxyActivity: AppCompatActivity() {


    override fun getResources(): Resources {

//        val resources = Resources()
        return super.getResources()
    }
}