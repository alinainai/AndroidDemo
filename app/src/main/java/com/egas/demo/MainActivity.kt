package com.egas.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.egas.demo.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}