package com.zerodev.kasremaja.ui.slide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.zerodev.kasremaja.R

class SlideActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    lateinit var adapter : SlideAdapter
    lateinit var dotsLayout: LinearLayout
    lateinit var dots : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide)

    }
}