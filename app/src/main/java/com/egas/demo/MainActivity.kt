package com.egas.demo

import android.content.Context
import android.os.Bundle
import android.view.View
import com.egas.demo.base.BaseActivity
import com.egas.demo.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.sampleText.text = "main page"
    }

    /**
     * 打印ClassLoader
     */
    private fun logClassLoader(){
        println("MainActivity.class=${this.classLoader}")
        println("Context.class=${Context::class.java.classLoader}")
        println("View.class=${View::class.java.classLoader}")
        println("SystemClassLoader.class=${ClassLoader.getSystemClassLoader()}")
    }
}
