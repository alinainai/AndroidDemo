package com.egas.demo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.egas.demo.base.BaseActivity
import com.gas.annotation.BindView
import com.gas.library.BindViewTools

class MainActivity : BaseActivity() {

    @BindView(R.id.textView)
    lateinit var mTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BindViewTools.bind(this)
        mTextView.text = "aptdemo"
    }

}