package com.egas.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.egas.demo.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.hello1).setOnClickListener {

        }
        findViewById<View>(R.id.hello2).setOnClickListener {

        }
        findViewById<View>(R.id.hello3).setOnClickListener {

        }
        findViewById<View>(R.id.hello4).setOnClickListener {

        }
    }
}