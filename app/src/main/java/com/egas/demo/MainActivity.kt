package com.egas.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.egas.demo.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.btn1).setOnClickListener {
            startActivity(Intent(this,ObjectorAnimActivity::class.java))
        }
        findViewById<View>(R.id.btn2).setOnClickListener {
            startActivity(Intent(this,LayoutAnimActivity::class.java))
        }
        findViewById<View>(R.id.btn3).setOnClickListener {

        }
        findViewById<View>(R.id.btn4).setOnClickListener {
            startActivity(Intent(this,StateListAnimActivity::class.java))
        }
    }
}