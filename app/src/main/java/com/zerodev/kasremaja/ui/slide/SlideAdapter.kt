package com.zerodev.kasremaja.ui.slide

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class SlideAdapter(val context : Context, val layouts : Array<Int>) :
    PagerAdapter() {

    lateinit var layoutInflater: LayoutInflater

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view : View = layoutInflater.inflate(layouts[position], container, false)
        container.addView(view)
        return view
    }

    override fun getCount(): Int {
        return layouts.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view : View = `object` as View
        container.removeView(view)
    }
}