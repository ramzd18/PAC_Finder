package com.example.newpolicurrency

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


public class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    //  constructor(fragmentActivity: FragmentActivity) : super(fragmentActiivty)

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0   -> {
                return News()
            }
            1 -> {
                return Committees()
            }
            2 -> {
                return Report()
            }
            else -> {
                return News()
            }
        }

    }
}