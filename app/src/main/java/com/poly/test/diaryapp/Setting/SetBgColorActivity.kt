package com.poly.test.diaryapp.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.utils.Constants
import com.poly.test.diaryapp.utils.Constants.COLOR_BG
import com.poly.test.diaryapp.utils.Constants.COLOR_FRIENDLIST
import com.poly.test.diaryapp.utils.Constants.COLOR_HOME_ITEM
import com.poly.test.diaryapp.utils.Constants.COLOR_ICON_BACKGROUND
import com.poly.test.diaryapp.utils.Constants.COLOR_ICON_BACKSEARCH
import com.poly.test.diaryapp.utils.Constants.COLOR_ICON_CANCEL
import com.poly.test.diaryapp.utils.Constants.COLOR_ICON_FRALARM
import com.poly.test.diaryapp.utils.Constants.COLOR_ICON_MYALARM
import com.poly.test.diaryapp.utils.Constants.COLOR_ICON_PROFILE_IMAGE
import com.poly.test.diaryapp.utils.Constants.COLOR_SEARCH_BACK
import com.poly.test.diaryapp.utils.Constants.COLOR_TEXT_W
import com.poly.test.diaryapp.utils.SharedPreferenceFactory
import kotlinx.android.synthetic.main.activity_set_bg_color.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class SetBgColorActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_bg_color)
        this.background_color_page.setBackgroundResource(Constants.COLOR_BG)
        textWhiteColor()
        iconColor()
//        this.background_color_page.setBackgroundResource(Constants.COLOR_G_BG)




        backColor()
        close()
    }

    fun textWhiteColor(){

        //메인
        textView2.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        textView4.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        textView3.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        //단색
        textred.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        textyellow.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        textblue.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        textpuple.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        textmint.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        textwhite.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        //그라디언트
        textred_g.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        textyellow_g.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        textblue_g.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        textpuple_g.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
        textmint_g.setTextColor(resources.getColor(Constants.COLOR_TEXT_W))
    }


    //백그라운드 아이콘 컬러
    fun iconColor(){
        bg_cancel.setImageResource(Constants.COLOR_ICON_CANCEL)
    }

    //취소
    fun close(){
        bg_cancel.setOnClickListener {
            finish()
        }
    }

    //백그라운드 컬러
    fun backColor(){

         btn_red.setOnClickListener {
             COLOR_BG = R.color.red
             COLOR_SEARCH_BACK = R.color.red
             COLOR_TEXT_W = R.color.white
             COLOR_ICON_CANCEL = R.drawable.icon_cancel
             COLOR_ICON_BACKGROUND = R.drawable.icon_main_back
             COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back
             COLOR_ICON_MYALARM = R.drawable.icon_my_alarm
             COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm
             COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2
             COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
             COLOR_HOME_ITEM = R.drawable.home_corner_drawable
             SharedPreferenceFactory.putStrValue("colorText","1")
             SharedPreferenceFactory.putStrValue("color","1")

//             SharedPreferenceFactory.putStrValue(this,"color" ,"1")
             finish()
         }

         btn_Yellow.setOnClickListener {
             COLOR_BG = R.color.yellow
             COLOR_SEARCH_BACK = R.color.yellow
             COLOR_TEXT_W = R.color.textblack
             COLOR_ICON_CANCEL = R.drawable.icon_cancel_n
             COLOR_ICON_BACKGROUND = R.drawable.icon_main_back_n
             COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back_n
             COLOR_ICON_MYALARM = R.drawable.icon_my_alarm_n
             COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm_n
             COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2_n
             COLOR_FRIENDLIST = R.drawable.icon_friendprofile2_n
             COLOR_HOME_ITEM = R.drawable.home_corner_drawable_n
             SharedPreferenceFactory.putStrValue("colorText","2")
             SharedPreferenceFactory.putStrValue("color","2")
//
//             SharedPreferenceFactory.putStrValue(this,"color" ,"2")
             finish()
         }

         btn_blue.setOnClickListener {
             COLOR_BG = R.color.blue
             COLOR_SEARCH_BACK = R.color.blue
             COLOR_TEXT_W = R.color.white
             COLOR_ICON_CANCEL = R.drawable.icon_cancel
             COLOR_ICON_BACKGROUND = R.drawable.icon_main_back
             COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back
             COLOR_ICON_MYALARM = R.drawable.icon_my_alarm
             COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm
             COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2
             COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
             COLOR_HOME_ITEM = R.drawable.home_corner_drawable
//             MyApplication.prefs.setString("color","3")
             SharedPreferenceFactory.putStrValue("colorText","3")
             SharedPreferenceFactory.putStrValue("color" ,"3")
             finish()
         }

         btn_mint.setOnClickListener {
             COLOR_BG = R.color.mint
             COLOR_SEARCH_BACK = R.color.mint
             COLOR_TEXT_W = R.color.textblack
             COLOR_ICON_CANCEL = R.drawable.icon_cancel_n
             COLOR_ICON_BACKGROUND = R.drawable.icon_main_back_n
             COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back_n
             COLOR_ICON_MYALARM = R.drawable.icon_my_alarm_n
             COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm_n
             COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2_n
             COLOR_FRIENDLIST = R.drawable.icon_friendprofile2_n
             COLOR_HOME_ITEM = R.drawable.home_corner_drawable_n
//             MyApplication.prefs.setString("color","4")
             SharedPreferenceFactory.putStrValue("colorText","4")
             SharedPreferenceFactory.putStrValue("color" ,"4")
             finish()
         }

         btn_purple.setOnClickListener {
             COLOR_BG = R.color.purple
             COLOR_SEARCH_BACK = R.color.purple
             COLOR_TEXT_W = R.color.white
             COLOR_HOME_ITEM = R.drawable.home_corner_drawable
//             MyApplication.prefs.setString("color","5")
             COLOR_ICON_CANCEL = R.drawable.icon_cancel
             COLOR_ICON_BACKGROUND = R.drawable.icon_main_back
             COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back
             COLOR_ICON_MYALARM = R.drawable.icon_my_alarm
             COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm
             COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2
             COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
             SharedPreferenceFactory.putStrValue("colorText","5")
             SharedPreferenceFactory.putStrValue("color" ,"5")
             finish()
         }

         btn_white.setOnClickListener {
             COLOR_BG = R.color.white
             COLOR_TEXT_W = R.color.textblack
             COLOR_SEARCH_BACK = R.color.white
//             MyApplication.prefs.setString("color","6")
             COLOR_ICON_CANCEL = R.drawable.icon_cancel_n
             COLOR_ICON_BACKGROUND = R.drawable.icon_main_back_n
             COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back_n
             COLOR_ICON_MYALARM = R.drawable.icon_my_alarm_n
             COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm_n
             COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2_n
             COLOR_FRIENDLIST = R.drawable.icon_friendprofile2_n
             COLOR_HOME_ITEM = R.drawable.home_corner_drawable_n
             SharedPreferenceFactory.putStrValue("colorText","6")
             SharedPreferenceFactory.putStrValue("color" ,"6")
             SharedPreferenceFactory.putStrValue("icon" ,"6")
             finish()
         }

        //그라디언트 부분

        btn_red_g.setOnClickListener {
            COLOR_BG = R.drawable.red_back_g
            COLOR_SEARCH_BACK = R.drawable.red_g
            COLOR_TEXT_W = R.color.white
            COLOR_ICON_CANCEL = R.drawable.icon_cancel
            COLOR_ICON_BACKGROUND = R.drawable.icon_main_back
            COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back
            COLOR_ICON_MYALARM = R.drawable.icon_my_alarm
            COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm
            COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2
            COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
            COLOR_HOME_ITEM = R.drawable.home_corner_drawable
            SharedPreferenceFactory.putStrValue("colorText","7")
            SharedPreferenceFactory.putStrValue("color","7")
            SharedPreferenceFactory.putStrValue("icon" ,"7")

//             SharedPreferenceFactory.putStrValue(this,"color" ,"1")
            finish()
        }

        btn_yellow_g.setOnClickListener {
            COLOR_BG = R.drawable.yellow_back_g
            COLOR_SEARCH_BACK = R.drawable.yellow_g
            COLOR_TEXT_W = R.color.textblack
            COLOR_ICON_CANCEL = R.drawable.icon_cancel_n
            COLOR_ICON_BACKGROUND = R.drawable.icon_main_back_n
            COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back_n
            COLOR_ICON_MYALARM = R.drawable.icon_my_alarm_n
            COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm_n
            COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2_n
            COLOR_FRIENDLIST = R.drawable.icon_friendprofile2_n
            COLOR_HOME_ITEM = R.drawable.home_corner_drawable_n
            SharedPreferenceFactory.putStrValue("colorText","8")
            SharedPreferenceFactory.putStrValue("color","8")
            SharedPreferenceFactory.putStrValue("icon" ,"8")

//             SharedPreferenceFactory.putStrValue(this,"color" ,"1")
            finish()
        }

        btn_blue_g.setOnClickListener {
            COLOR_BG = R.drawable.blue_back_g
            COLOR_SEARCH_BACK = R.drawable.blue_g
            COLOR_TEXT_W = R.color.white
            COLOR_ICON_CANCEL = R.drawable.icon_cancel
            COLOR_ICON_BACKGROUND = R.drawable.icon_main_back
            COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back
            COLOR_ICON_MYALARM = R.drawable.icon_my_alarm
            COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm
            COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2
            COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
            COLOR_HOME_ITEM = R.drawable.home_corner_drawable
            SharedPreferenceFactory.putStrValue("colorText","9")
            SharedPreferenceFactory.putStrValue("color","9")
            SharedPreferenceFactory.putStrValue("icon" ,"9")

//             SharedPreferenceFactory.putStrValue(this,"color" ,"1")
            finish()
        }

        btn_purple_g.setOnClickListener {
            COLOR_BG = R.drawable.puple_back_g
            COLOR_SEARCH_BACK = R.drawable.puple_g
            COLOR_TEXT_W = R.color.white
            COLOR_ICON_CANCEL = R.drawable.icon_cancel
            COLOR_ICON_BACKGROUND = R.drawable.icon_main_back
            COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back
            COLOR_ICON_MYALARM = R.drawable.icon_my_alarm
            COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm
            COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera
            COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
            COLOR_HOME_ITEM = R.drawable.home_corner_drawable
            SharedPreferenceFactory.putStrValue("colorText","10")
            SharedPreferenceFactory.putStrValue("color","10")
            SharedPreferenceFactory.putStrValue("icon" ,"10")

//             SharedPreferenceFactory.putStrValue(this,"color" ,"1")
            finish()
        }

        btn_mint_g.setOnClickListener {
            COLOR_BG = R.drawable.back_mint_g
            COLOR_SEARCH_BACK = R.drawable.mint_g
            COLOR_TEXT_W = R.color.white
            COLOR_ICON_CANCEL = R.drawable.icon_cancel
            COLOR_ICON_BACKGROUND = R.drawable.icon_main_back
            COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back
            COLOR_ICON_MYALARM = R.drawable.icon_my_alarm
            COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm
            COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2
            COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
            COLOR_HOME_ITEM = R.drawable.home_corner_drawable
            SharedPreferenceFactory.putStrValue("colorText","11")
            SharedPreferenceFactory.putStrValue("color","11")
            SharedPreferenceFactory.putStrValue("icon" ,"11")

//             SharedPreferenceFactory.putStrValue(this,"color" ,"1")
            finish()
        }


     }
}