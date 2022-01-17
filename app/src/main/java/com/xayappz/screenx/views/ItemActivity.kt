package com.xayappz.screenx.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
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