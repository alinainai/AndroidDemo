package com.egas.demo

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class StateListAnimActivity : AppCompatActivity() {
    private var img: ImageView? = null
    private var img2: ImageView? = null
    private var img3: ImageView? = null
    private var tag = false
    private var tag2 = false
    private var tag3 = false

    private val upAnim: ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(img2!!, View.ROTATION_X, 0F, 180F).apply {
            duration = 300
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_list_anim)
        img = findViewById(R.id.img)
        img2 = findViewById(R.id.img2)
        img3 = findViewById(R.id.img3)

        // 通过 StateListAnimator 实现
        img?.setOnClickListener {
            if (tag) {
                tag = false
                img?.isSelected = false
            } else {
                tag = true
                img?.isSelected = true
            }
        }

        // 通过 ObjectAnimator 实现
        img2?.setOnClickListener {
            if (tag2) {
                tag2 = false
                img2?.isSelected = false
                upAnim.reverse()
            } else {
                tag2 = true
                img2?.isSelected = true
                upAnim.start()
            }
        }

        // 通过 ViewPropertyAnimator 实现
        img3?.setOnClickListener {
            if (tag3) {
                tag3 = false
                img3?.animate()?.rotationX(0F)
            } else {
                tag3 = true
                img3?.animate()?.rotationX(180F)
            }
        }
    }

}