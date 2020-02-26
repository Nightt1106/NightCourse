package com.night.nightcourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.night.nightcourse.adapter.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_stray_house.*

class StrayHouseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stray_house)
        viewPager.adapter = FragmentPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

}
