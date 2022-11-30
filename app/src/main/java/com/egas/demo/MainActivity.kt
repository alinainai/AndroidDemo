package com.egas.demo

import android.os.Bundle
import android.util.Log
import com.egas.demo.base.BaseActivity
import com.egas.demo.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sampleText.text = "main page"
    }

    private fun okhttpUtils(){
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(10, TimeUnit.SECONDS)

        builder.addInterceptor(Interceptor { chain ->
            Log.d("okhttp", "Interceptor url:" + chain.request().url.toString())
            chain.proceed(chain.request())
        })
        builder.addNetworkInterceptor(Interceptor { chain ->
            Log.d("okhttp", "NetworkInterceptor url:" + chain.request().url.toString())
            chain.proceed(chain.request())
        })

        val client: OkHttpClient = builder.build()

        val request: Request = Request.Builder()
            .url("https://www.baidu.com")
            .build()
        val call: Call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("okhttp", "onFailure: " + e.message)
            }
            override fun onResponse(call: Call, response: Response) {
                Log.d("okhttp", "response:" + response.body?.string())
            }
        })

        val response = call.execute()
    }
}
