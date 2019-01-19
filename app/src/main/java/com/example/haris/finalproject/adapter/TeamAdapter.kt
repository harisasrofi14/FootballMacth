package com.example.haris.finalproject.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.haris.finalproject.fragment.*

class TeamAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){


    private val pages = listOf(
        TeamDescFragment(),
        PlayerFragment()

    )

    override fun getItem(position: Int): Fragment? {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Description"
            else -> { "Player"
            }
        }
    }

}