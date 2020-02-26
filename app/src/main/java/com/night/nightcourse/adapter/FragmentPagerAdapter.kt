package com.night.nightcourse.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.night.nightcourse.ui.fragment.AnimalFragment
import com.night.nightcourse.ui.fragment.LikeFragment

@Suppress("DEPRECATION")
class FragmentPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager){


    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> AnimalFragment()
            1 -> LikeFragment()
            else -> LikeFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0-> "Home"
            1-> "Likes"
            else -> null
        }
    }

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
    }
}