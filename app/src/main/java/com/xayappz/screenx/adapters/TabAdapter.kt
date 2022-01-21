package com.xayappz.screenx.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.xayappz.screenx.views.fragments.AvailableFrag
import com.xayappz.screenx.views.fragments.DisabledFragment
import com.xayappz.screenx.views.fragments.LoadedFragment

class TabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {


            0 -> return LoadedFragment()
            1 -> return AvailableFrag()
            2 -> return DisabledFragment()

        }


        return LoadedFragment()
    }
}