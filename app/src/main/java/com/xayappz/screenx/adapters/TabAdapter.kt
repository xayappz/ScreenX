package com.xayappz.screenx.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.xayappz.screenx.views.fragments.AvailableFragment
import com.xayappz.screenx.views.fragments.DisabledFragmet
import com.xayappz.screenx.views.fragments.LoadedFragment

class TabAdapter(private val myContext: Context, fm: FragmentManager, private var totalTabs: Int) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return LoadedFragment()
            }
            1 -> {
                return AvailableFragment()
            }
            2 -> {
                // val movieFragment = MovieFragment()
                return DisabledFragmet()
            }
            else -> return Fragment()
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}