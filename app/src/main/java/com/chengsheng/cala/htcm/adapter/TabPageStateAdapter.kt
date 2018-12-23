package com.chengsheng.cala.htcm.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import java.util.*

class TabPageStateAdapter(fm: FragmentManager, var fragments: ArrayList<Fragment>) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return fragments.size
    }


    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}
