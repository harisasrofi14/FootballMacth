package com.example.haris.finalproject.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import com.example.haris.finalproject.adapter.MyPagerAdapter
import com.example.haris.finalproject.R
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Stetho.initializeWithDefaults(this)
        initview()
      setSupportActionBar(main_toolbar)
    }

    fun initview(){
        viewpager.adapter = MyPagerAdapter(supportFragmentManager)
        tabs_main.setupWithViewPager(viewpager)
        viewpager.currentItem = 0
    }


}
