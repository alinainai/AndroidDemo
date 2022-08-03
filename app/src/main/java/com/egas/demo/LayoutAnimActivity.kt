package com.egas.demo

import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout

class LayoutAnimActivity : AppCompatActivity() {
    private lateinit var view1: View
    private lateinit var view2: View
    private lateinit var view3: View
    private lateinit var view4: View
    private lateinit var container: ViewGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_anim)
        view1 = findViewById(R.id.view1)
        view2 = findViewById(R.id.view2)
        view3 = findViewById(R.id.view3)
        view4 = findViewById(R.id.view4)
        container = findViewById(R.id.ll1)

        val transition = LayoutTransition()

//        APPEARING：view被添加（可见）到ViewGroup会触发的动画。
//        DISAPPEARING ：view被移除（不可见）ViewGroup会触发的动画。
//        CHANGE_APPEARING ：view被添加（可见）到ViewGroup，会影响其他View，此时其它View会触发的动画。
//        CHANGE_DISAPPEARING：view被移除（不可见）ViewGroup，会影响其他View，此时其它View会触发的动画。
//        API16 添加了CHANGING 类型，View在ViewGroup中位置改变时的过渡动画，不涉及删除或者添加操作。

        // 1、替换 APPEARING 和 DISAPPEARING 的动画

        //使用缩放进入的动画代替默认动画
        val appearAnim = ObjectAnimator.ofFloat(null, View.SCALE_X, .5F, 1F)
            .setDuration(transition.getDuration(LayoutTransition.APPEARING));
        transition.setAnimator(LayoutTransition.APPEARING, appearAnim);
        //使用平移消失的动画代替默认动画
        val disappearAnim = ObjectAnimator.ofFloat(null, View.TRANSLATION_X, 0F, 100f)
            .setDuration(transition.getDuration(LayoutTransition.DISAPPEARING))
        transition.setAnimator(LayoutTransition.DISAPPEARING, disappearAnim)

        // 2、替换 CHANGE_APPEARING 和 CHANGE_DISAPPEARING 的动画

//        CHANGE_APPEARING和CHANGE_DISAPPEARING布局动画设置必须使用PropertyValuesHolder所构造的动画才会有效果，否则不起作用。
//        PropertyValuesHolder生成动画时，”left”、”top”属性必须有，如果没有则没有动画效果。如果不需要改变这两个值，可以写成写为：
//        PropertyValuesHolder left = PropertyValuesHolder.ofInt(“left”,0,0);
//        PropertyValuesHolder top = PropertyValuesHolder.ofInt(“top”,0,0);
//        PropertyValuesHolder中所使用的ofInt,ofFloat中的参数值，第一个值和最后一个值必须相同，不然此属性所对应的的动画将被放弃，在此属性值上将不会有效果；同时不能所有的属性值都相同，否则也将无效（不能写成100,100,100）。
//        PropertyValuesHolder pvhTop = PropertyValuesHolder.ofInt(“top”, 0, 100,0);
//        设置动画duration等没有效果

        val changeLeft = PropertyValuesHolder.ofInt("left", 0, 0);
        val changeTop = PropertyValuesHolder.ofInt("top", 0, 0);
        val changeRight = PropertyValuesHolder.ofInt("right", 0, 0);
        val changeBottom = PropertyValuesHolder.ofInt("bottom", 0, 0);

        val aniChanApp = PropertyValuesHolder.ofFloat("rotation", 0F, 50F, 0F);
        val changeApp = ObjectAnimator.ofPropertyValuesHolder(this, changeLeft, changeTop, aniChanApp);
        transition.setAnimator(LayoutTransition.CHANGE_APPEARING, changeApp);

        val aniChangeDis = PropertyValuesHolder.ofFloat("rotation", 0F, 50F, 0F)
        val changeDis = ObjectAnimator.ofPropertyValuesHolder(this, changeLeft, changeTop, aniChangeDis);
        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeApp)

        container.layoutTransition = transition

        view1.setOnClickListener {
            val button = Button(this@LayoutAnimActivity)
            button.setPadding(20, 20, 20, 20)
            button.text = "tempBtn"
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            container.addView(button, 0, params)
        }

        view2.setOnClickListener {
            val count: Int = container.getChildCount()
            if (count >= 2) {
                container.removeViewAt(0)
            }
        }

        view3.setOnClickListener {
            val count: Int = container.getChildCount()
            if (count >= 2) {
                container.getChildAt(0).visibility=View.VISIBLE
            }
        }

        view4.setOnClickListener {
            val count: Int = container.getChildCount()
            if (count >= 2) {
                container.getChildAt(0).visibility=View.GONE
            }
        }
    }
}