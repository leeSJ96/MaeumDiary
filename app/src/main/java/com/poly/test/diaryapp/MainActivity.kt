package com.poly.test.diaryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.poly.test.diaryapp.Calendar.CalendarListFragment
import com.poly.test.diaryapp.Home.HomeFragment
import com.poly.test.diaryapp.Setting.SettingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var mBundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().add(R.id.frag_layout, homeFragment).commit()

        menu_bar.selectedItemId = R.id.home_tab

        menu_bar.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val fm = supportFragmentManager
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()


        when (item.itemId) {

            R.id.home_tab -> {
                fm.popBackStack("home_tab", FragmentManager.POP_BACK_STACK_INCLUSIVE)

                val homeFragment = HomeFragment()
                homeFragment.arguments = mBundle
                transaction.replace(R.id.frag_layout, homeFragment, "home_tab")
                transaction.addToBackStack("home_tab")
            }

            R.id.calendar_tab -> {
                fm.popBackStack("calendar_tab", FragmentManager.POP_BACK_STACK_INCLUSIVE)

                val calendarListFragment = CalendarListFragment()
                calendarListFragment.arguments = mBundle
                transaction.replace(R.id.frag_layout, calendarListFragment,"calendar_tab")
                transaction.addToBackStack("calendar_tab")

            }

            R.id.setting_tab -> {
                fm.popBackStack("setting_tab", FragmentManager.POP_BACK_STACK_INCLUSIVE)

                val settingFragment = SettingFragment()
                settingFragment.arguments = mBundle
                transaction.replace(R.id.frag_layout, settingFragment,"setting_tab")
                transaction.addToBackStack("setting_tab")
            }

        }


        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
        transaction.isAddToBackStackAllowed

        return true
    }

    private fun updateBottomMenu(navigation:BottomNavigationView){

        val tag1: Fragment? = supportFragmentManager.findFragmentByTag("home_tab")
        val tag2: Fragment? = supportFragmentManager.findFragmentByTag("calendar_tab")
        val tag3: Fragment? = supportFragmentManager.findFragmentByTag("setting_tab")

        if(tag1 != null && tag1.isVisible) {
            navigation.menu.findItem(R.id.home_tab).isChecked = true
        }
        if(tag2 != null && tag2.isVisible) {
            navigation.menu.findItem(R.id.calendar_tab).isChecked = true
        }
        if(tag3 != null && tag3.isVisible) {
            navigation.menu.findItem(R.id.setting_tab).isChecked = true
        }



    }

    override fun onBackPressed() {

        super.onBackPressed()
        val bnv = findViewById<View>(R.id.menu_bar) as BottomNavigationView
        updateBottomMenu(bnv)

    }
}