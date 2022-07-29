package com.egas.demo

import android.animation.LayoutTransition
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


//        //使用翻转进入的动画代替默认动画
//        val appearAnim = ObjectAnimator
//            .ofFloat(null, View.ROTATION_Y, 90f, 0f)
//            .setDuration(transition.getDuration(LayoutTransition.APPEARING));
//        transition.setAnimator(LayoutTransition.APPEARING, appearAnim);
//
//        //使用翻转消失的动画代替默认动画
//        val disappearAnim = ObjectAnimator.ofFloat(
//            null, View.ROTATION_X, 0F,
//            90f
//        ).setDuration(
//            transition.getDuration(LayoutTransition.DISAPPEARING)
//        )
//        transition.setAnimator(LayoutTransition.DISAPPEARING, disappearAnim);
//
//        //使用滑动动画代替默认布局改变的动画
//        //这个动画会让视图滑动进入并短暂地缩小一半，具有平滑和缩放的效果
//        val pvhSlide = PropertyValuesHolder.ofFloat(View.Y, 0F, 1F);
//        val pvhScaleY = PropertyValuesHolder.ofFloat(
//            View.SCALE_Y,
//            1f, 0.5f, 1f
//        )
//        val pvhScaleX = PropertyValuesHolder.ofFloat(
//            View.SCALE_X,
//            1f, 0.5f, 1f
//        );
//
//        //这里将上面三个动画综合
//        val changingDisappearAnim = ObjectAnimator.ofPropertyValuesHolder(pvhSlide, pvhScaleY, pvhScaleX)
//        changingDisappearAnim.duration = transition.getDuration(LayoutTransition.CHANGE_DISAPPEARING);
//        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changingDisappearAnim);
//        container.layoutTransition = transition

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