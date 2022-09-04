package com.egas.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.egas.demo.base.BaseActivity

class Remote1Activity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.hello).setOnClickListener {
            startActivity(Intent(this,Remote2Activity::class.java))
        }
    }
}