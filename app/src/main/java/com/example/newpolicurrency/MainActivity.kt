package com.example.newpolicurrency

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionbar = supportActionBar
        val colordrawable= ColorDrawable(Color.parseColor("#ADD8E6"))
        actionbar?.setBackgroundDrawable(colordrawable)
     //   actionbar.colo
        //  val intent = Intent(this, Reportactivity::class.java)
        //startActivity(intent)

        // val viewPager = findViewById<ViewPager2>(R.id.pager)
        //var j = ScreenSlidePagerActivity()
//        viewPager.adapter = PageAdapter(supportFragmentManager)\
        val viewPager2= findViewById<ViewPager2>(R.id.pager)
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val myviewPagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager2.setAdapter(myviewPagerAdapter)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
               tab?.let { viewPager2.setCurrentItem(it.getPosition())}

            }



            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position:Int){
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.select()
            }
        })
    }
}