package com.poly.test.diaryapp.Setting

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.poly.test.diaryapp.MyApplication


import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.utils.Constants
import com.poly.test.diaryapp.utils.Constants.COLOR_BG
import kotlinx.android.synthetic.main.activity_set_bg_color.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class SetBgColorActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_bg_color)
        this.background_color_page.setBackgroundResource(Constants.COLOR_BG)
        val settingFragment = SettingFragment()

        backColor()

    }

        var color_red_string : String? = null

    fun backColor(){


         btn_red.setOnClickListener {
             COLOR_BG = R.color.red
             color_red_string
            MyApplication.prefs.setString("color","1")

//             SharedPreferenceFactory.putStrValue(this,"color" ,"1")
             finish()
         }

         btn_Yellow.setOnClickListener {
             COLOR_BG = R.color.yellow
             MyApplication.prefs.setString("color","2")
//
//             SharedPreferenceFactory.putStrValue(this,"color" ,"2")
             finish()
         }

         btn_blue.setOnClickListener {
             COLOR_BG = R.color.blue
             MyApplication.prefs.setString("color","3")
//             SharedPreferenceFactory.putStrValue(this,"color" ,"3")
             finish()
         }

         btn_mint.setOnClickListener {
             COLOR_BG = R.color.mint
             MyApplication.prefs.setString("color","4")
//             SharedPreferenceFactory.putStrValue(this,"color" ,"4")
             finish()
         }

         btn_purple.setOnClickListener {
             COLOR_BG = R.color.purple
             MyApplication.prefs.setString("color","5")
//             SharedPreferenceFactory.putStrValue(this,"color" ,"5")
             finish()
         }

         btn_white.setOnClickListener {
             COLOR_BG = R.color.white
             MyApplication.prefs.setString("color","6")
//             SharedPreferenceFactory.putStrValue(this,"color" ,"6")
             finish()
         }

     }
}