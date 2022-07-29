package com.egas.demo

import android.animation.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class ObjectorAnimActivity : AppCompatActivity() {

    private lateinit var rotate: View
    private lateinit var alpha: View
    private lateinit var translateX: View
    private lateinit var translateY: View
    private lateinit var scaleX: View
    private lateinit var animatorSet: View
    private lateinit var property: View
    private lateinit var propertyBy: View
    private lateinit var listener: View
    private lateinit var xmlBtn: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objector_anim)
        rotate = findViewById(R.id.rotate)
        alpha = findViewById(R.id.alpha)
        translateX = findViewById(R.id.translateX)
        translateY = findViewById(R.id.translateY)
        scaleX = findViewById(R.id.scaleX)
        animatorSet = findViewById(R.id.animatorSet)
        property = findViewById(R.id.property)
        propertyBy = findViewById(R.id.propertyBy)
        listener = findViewById(R.id.listener)
        xmlBtn = findViewById(R.id.xmlAnim)

        val translateXAnim =
            ObjectAnimator.ofFloat(translateX, View.TRANSLATION_X, 0F, 100F).apply {
                duration = 1000
//                repeatCount = 1
                repeatMode = ValueAnimator.REVERSE
            }

        val translateYAnim = ObjectAnimator.ofFloat(translateY, View.TRANSLATION_Y, 0F, 100F)
            .apply {
                duration = 1000
                repeatCount = 1
                repeatMode = ValueAnimator.REVERSE
            }

        val alphaAnim = ObjectAnimator.ofFloat(alpha, View.ALPHA, 1F, 0F, 1F)
            .apply {
                duration = 1000
                repeatCount = 2
                repeatMode = ValueAnimator.REVERSE
            }

        val rotateAnim = ObjectAnimator.ofFloat(rotate, View.ROTATION, 0F, 180F)
            .apply {
                duration = 1000
                repeatCount = 1
                repeatMode = ValueAnimator.REVERSE
            }


        val scaleAnim = ObjectAnimator.ofFloat(scaleX, View.SCALE_X, 1F, 2F)
            .apply {
                duration = 1000
                repeatCount = 1
                repeatMode = ValueAnimator.REVERSE
            }

        translateX.setOnClickListener {
            translateXAnim.start()
        }
        scaleX.setOnClickListener {
            scaleAnim.start()
        }
        translateY.setOnClickListener {
            translateYAnim.start()
        }

        alpha.setOnClickListener {
            alphaAnim.start()
        }

        rotate.setOnClickListener {
            rotateAnim.start()
        }
        animatorSet.setOnClickListener {
            val rotateXAnim = ObjectAnimator.ofFloat(animatorSet, View.ROTATION_X, 0F, 180F)
                .apply {
                    duration = 1000
                    repeatCount = 1
                    repeatMode = ValueAnimator.REVERSE
                }
            val rotateYAnim = ObjectAnimator.ofFloat(animatorSet, View.ROTATION_Y, 0F, 180F)
                .apply {
                    duration = 1000
                    repeatCount = 1
                    repeatMode = ValueAnimator.REVERSE
                }
            AnimatorSet().apply {
                playTogether(rotateXAnim, rotateYAnim)
                duration = 2000
                start()
            }
        }
        val animator1 = property.animate()
        animator1.duration = 1000
        property.setOnClickListener {
            animator1.translationX(100f)//点击一次会向右偏移，再点击没效果
                .translationY(100F)
        }
        val animator2 = propertyBy.animate()
        animator2.duration = 1000
        propertyBy.setOnClickListener {
            animator2.translationXBy(100f)//每次点击都会向右偏移
                .translationYBy(100f)
        }

        val anim = ObjectAnimator.ofFloat(listener, "translationX", 0f, 180f, 0f).apply {
            duration = 3000
//            repeatCount = ValueAnimator.INFINITE
            repeatCount = 3
            repeatMode = ValueAnimator.RESTART
            addUpdateListener { //监听值变化
                Log.e("listener", "it.animatedValue=${it.animatedValue}")
//                listener.translationX = it.animatedValue as Float
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    Log.e("listener", "onAnimationStart")
                }

                override fun onAnimationEnd(animation: Animator?) {
                    Log.e("listener", "onAnimationEnd")
                }

                override fun onAnimationCancel(animation: Animator?) {
                    Log.e("listener", "onAnimationCancel")
                }

                override fun onAnimationRepeat(animation: Animator?) {
                    Log.e("listener", "onAnimationRepeat")
                }
            })
        }

        val anim2 = ValueAnimator.ofFloat( 0f, 180f, 0f).apply {
            duration = 3000
//            repeatCount = ValueAnimator.INFINITE
            repeatCount = 3
            repeatMode = ValueAnimator.RESTART
            addUpdateListener { //监听值变化
//                Log.e("listener", "it.animatedValue=${it.animatedValue}")
                listener.translationX = it.animatedValue as Float
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    Log.e("listener", "onAnimationStart")
                }

                override fun onAnimationEnd(animation: Animator?) {
                    Log.e("listener", "onAnimationEnd")
                }

                override fun onAnimationCancel(animation: Animator?) {
                    Log.e("listener", "onAnimationCancel")
                }

                override fun onAnimationRepeat(animation: Animator?) {
                    Log.e("listener", "onAnimationRepeat")
                }
            })
        }

        listener.setOnClickListener {
//            anim.start()
            anim2.start()
        }
        val xmlAnim = AnimatorInflater.loadAnimator(this,R.animator.animator_translation)
        xmlAnim.setTarget(xmlBtn)
        xmlBtn.setOnClickListener {
            xmlAnim.start()
        }


    }
}