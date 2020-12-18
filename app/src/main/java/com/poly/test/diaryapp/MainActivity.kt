package com.poly.test.diaryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.poly.test.diaryapp.Calendar.CalendarFragment
import com.poly.test.diaryapp.Home.HomeFragment
import com.poly.test.diaryapp.Setting.SettingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().add(R.id.frag_layout, homeFragment).commit()

        menu_bar.selectedItemId = R.id.home_tab

        menu_bar.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.home_tab -> {
                val homeFragment = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frag_layout, homeFragment)
                    .commit()
                return true
            }

            R.id.calendar_tab -> {
                val calendarFragment = CalendarFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frag_layout, calendarFragment)
                    .commit()
                return true
            }

            R.id.setting_tab -> {
                val settingFragment = SettingFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frag_layout, settingFragment)
                    .commit()
                return true
            }

        }

        return true
    }



}