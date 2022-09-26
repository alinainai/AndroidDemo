package com.egas.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.egas.demo.base.BaseActivity

class MainActivity : BaseActivity() {
   private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sampleText.text = "main page"
    }
}
