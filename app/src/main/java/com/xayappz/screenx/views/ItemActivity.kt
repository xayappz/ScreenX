package com.xayappz.screenx.views

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.xayappz.screenx.R
import com.xayappz.screenx.adapters.TabAdapter


class ItemActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager2? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)


        val itemTabs = arrayOf(
            getString(
                R.string.loaned_items_fragment
            ),
            getString(
                R.string.available_items_fragment
            ), getString(
                R.string.disables_items_fragment
            )
        )
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById<ViewPager2>(R.id.viewPager)

        viewPager!!.adapter = TabAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(
            tabLayout!!,
            viewPager!!
        ) { tab, position ->
            tab.text = itemTabs[position]
        }.attach()

        viewPager?.currentItem = 1
        tabLayout?.setSelectedTabIndicatorColor(Color.parseColor("#000000"));
        tabLayout?.setSelectedTabIndicatorHeight (10);
        tabLayout?.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#000000"));
        viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })


        tabLayout!!.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                for (index in 0 until (tab.view as ViewGroup).childCount) {
                    val nextChild = (tab.view as ViewGroup).getChildAt(index)
                    if (nextChild is TextView) {
                        nextChild.setTypeface(null, Typeface.BOLD)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
            override fun onTabUnselected(tab: TabLayout.Tab) {

                for (index in 0 until (tab.view as ViewGroup).childCount) {
                    val nextChild = (tab.view as ViewGroup).getChildAt(index)
                    if (nextChild is TextView) {
                        nextChild.setTypeface(null, Typeface.NORMAL)
                    }
                }

            }
        })
    }
//
//    override fun changedState(boolean: Boolean) {
//        Log.d("Postiion","position")
//        //     ChangeFragment?.changedState(boolean)
//    }
//
//    fun setUpListener(Listener: ChangeFragment) {
//        ChangeFragment = Listener
//    }


}