package com.egas.demo

import android.content.Context
import android.os.Bundle
import android.view.View
import com.egas.demo.base.BaseActivity
import com.egas.demo.databinding.ActivityMainBinding
import java.lang.reflect.InvocationTargetException

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.sampleText.text = "main page"
        //打印 ClassLoader
        logClassLoader()
        // 反射执行相关类
        reflectInvokeMethod()
        // 加载 text 资源流程
        resources.getText(R.string.app_name)
    }

    /**
     * 打印 ClassLoader 信息
     */
    private fun logClassLoader(){
        println("MainActivity.class=${this.classLoader}")
        println("Context.class=${Context::class.java.classLoader}")
        println("View.class=${View::class.java.classLoader}")
        println("SystemClassLoader.class=${ClassLoader.getSystemClassLoader()}")
    }

    /**
     * 通过反射加载方法
     * 打印信息 ：I/System.out: Hello World
     */
    private fun reflectInvokeMethod(){
        try {
            val clazz = Class.forName("com.egas.demo.dynamicloader.ReflectMethod")
            val constructor = clazz.declaredConstructors[0]
            constructor.isAccessible = true
            val method = clazz.getDeclaredMethod("logHelloWorld")
            method.isAccessible = true
            val reflectMethod = constructor.newInstance()
            method.invoke(reflectMethod)
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
    }

}
