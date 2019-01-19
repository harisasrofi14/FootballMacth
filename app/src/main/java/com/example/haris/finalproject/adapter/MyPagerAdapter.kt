package com.example.haris.finalproject.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.haris.finalproject.fragment.*

class MyPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){


    private val pages = listOf(
        LastMatchFragment(),
        NextMatchFragment(),
        TeamFragment(),
        FavoriteMatchFragment(),
        FavoriteTeamFragment()

    )

    override fun getItem(position: Int): Fragment? {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Last Match"
            1 -> "Next Match"
            2 -> "Team"
            3 -> "Favorite Match"
            4  -> "Favorite Team"
            else -> { "Favorites"
            }
        }
    }

}