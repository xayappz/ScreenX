package com.xayappz.screenx.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.xayappz.screenx.R
import com.xayappz.screenx.adapters.TabAdapter

class ItemActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText(getString(R.string.loaned_items_fragment)))
        tabLayout!!.addTab(
            tabLayout!!.newTab().setText(getString(R.string.available_items_fragment))
        )
        tabLayout!!.addTab(
            tabLayout!!.newTab().setText(getString(R.string.disables_items_fragment))
        )
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = TabAdapter(this, supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.setScrollPosition(1, 0f, true);
        viewPager!!.currentItem = 1;
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
}