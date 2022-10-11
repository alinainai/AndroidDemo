package com.egas.demo

import android.os.Bundle
import android.util.Log
import com.egas.demo.base.BaseActivity
import com.egas.demo.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    val jniBean = JniDemoClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Example of a call to a native method
        binding.sampleText1.text = "实例方法：${jniBean.stringFromJNI(1, 1.0, "default")}"
        binding.sampleText2.text = "静态方法：${JniDemoClass.stringFromStaticJNI(1, 1.0, "static")}"
        binding.button.setOnClickListener {
            val trans = intArrayOf(1, 2, 3)
            jniBean.processIntArray(trans)
            val str1 = StringBuilder(16)
            trans.forEach {
                str1.append(it).append(",")
            }
            Log.e("int数组", "{trans= ${str1}}")
        }
        binding.button2.setOnClickListener {
            val trans = arrayOf("a", "b", "c")
            val back = jniBean.processStringArray(trans)
            Log.e("string数组", "{trans= ${trans}} {back= ${back}}")
        }
        binding.button3.setOnClickListener {
            Log.e("修改前", "{static field= ${JniDemoClass.staticField}} {file= ${jniBean.strField}}")
            jniBean.modifyField()
            Log.e("修改后", "{static field= ${JniDemoClass.staticField}} {file= ${jniBean.strField}}")
        }
    }

}