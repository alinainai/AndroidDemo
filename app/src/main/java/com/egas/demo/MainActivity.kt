package com.egas.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.egas.demo.base.BaseActivity
import com.egas.demo.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI()
    }

    private external fun stringFromJNI(): String

    companion object {
        init {
            System.loadLibrary("demo")
        }
    }
}
