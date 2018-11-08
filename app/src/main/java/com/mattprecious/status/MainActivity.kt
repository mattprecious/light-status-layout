package com.mattprecious.status

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener

class MainActivity : Activity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)

    val pagerView = findViewById<ViewPager>(R.id.pager)

    pagerView.adapter = object : PagerAdapter() {
      override fun instantiateItem(
        container: ViewGroup,
        position: Int
      ): Any {
        val pageView = layoutInflater
            .inflate(R.layout.page, container, false)
        container.addView(pageView)
        return pageView
      }

      override fun destroyItem(
        container: ViewGroup,
        position: Int,
        theObject: Any
      ) {
        container.removeView(theObject as View)
      }

      override fun isViewFromObject(
        view: View,
        theObject: Any
      ) = view == theObject

      override fun getCount() = 2
    }

    pagerView.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
      override fun onPageSelected(position: Int) {
        updateFlags(lightStatus = position == 1)
      }
    })
  }

  private fun updateFlags(lightStatus: Boolean = false) {
    // Forcing dispatchWindowAttributesChanged to be invoked.
    window.setGravity(window.attributes.gravity)

    val decorView = window.decorView
    if (lightStatus) {
      decorView.systemUiVisibility = decorView.systemUiVisibility or
          View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
      decorView.systemUiVisibility = decorView.systemUiVisibility and
          View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    }
  }
}
