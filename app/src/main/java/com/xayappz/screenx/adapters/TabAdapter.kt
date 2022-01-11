package com.xayappz.screenx.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.xayappz.screenx.views.fragments.AvailableFragment
import com.xayappz.screenx.views.fragments.DisabledFragmet
import com.xayappz.screenx.views.fragments.LoadedFragment

class TabAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return LoadedFragment()
            1 -> return AvailableFragment()
            1 -> return DisabledFragmet()

        }
        return LoadedFragment()
    }
}