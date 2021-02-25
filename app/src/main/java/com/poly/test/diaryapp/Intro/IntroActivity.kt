package com.poly.test.diaryapp.Intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.poly.test.diaryapp.MainActivity
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.utils.Constants
import com.poly.test.diaryapp.utils.Constants.COLOR_BG
import com.poly.test.diaryapp.utils.SharedPreferenceFactory

class IntroActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // 사용자가 로그인을 전에 했을 경우 자동 로그인을 하기 위해 디바이스에 저장된 데이터값을 조회한다
        val uid = SharedPreferenceFactory.getStrValue("userToken", null)
        val name = SharedPreferenceFactory.getStrValue("userName", null)
        val email = SharedPreferenceFactory.getStrValue("userEmail", null)

        Log.d("로그", "uid $uid")
        Log.d("로그", "name $name")
        Log.d("로그", "email $email")


        if (uid != null && name != null && email != null) {

            moveNext(uid, email, name)

        } else {

            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)
                finish()
            }, 1500)


        }
        BgColorShard()

    }

    private fun moveNext(uid: String, email: String?, name: String?) {

        val authStore = FirebaseFirestore.getInstance().collection("user_auth")


        Handler(Looper.getMainLooper()).postDelayed({

            authStore.whereEqualTo("uid", uid).get().addOnSuccessListener { querySnapshot ->

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)
                finish()

            }.addOnFailureListener { error ->
                Log.d("로그", "error $error")
            }

        }, 1500)
    }

    fun BgColorShard() {

        when (SharedPreferenceFactory.getStrValue("color", "0")) {

            "1" -> COLOR_BG = R.color.red
            "2" -> COLOR_BG = R.color.yellow
            "3" -> COLOR_BG = R.color.blue
            "4" -> COLOR_BG = R.color.mint
            "5" -> COLOR_BG = R.color.purple
            "6" -> COLOR_BG = R.color.white
            "7" -> COLOR_BG = R.drawable.back_red_g
            "8" -> COLOR_BG = R.drawable.back_yellow_g
            "9" -> COLOR_BG = R.drawable.back_blue_g
            "10" -> COLOR_BG = R.drawable.back_purple_g
            "11" -> COLOR_BG = R.drawable.back_mint_g
            else  -> COLOR_BG = R.color.white

        }

        when (SharedPreferenceFactory.getStrValue("color", "0")) {
            "1" -> Constants.COLOR_TEXT_W = R.color.white
            "2" -> Constants.COLOR_TEXT_W = R.color.textblack
            "3" -> Constants.COLOR_TEXT_W = R.color.white
            "4" -> Constants.COLOR_TEXT_W = R.color.textblack
            "5" -> Constants.COLOR_TEXT_W = R.color.white
            "6" -> Constants.COLOR_TEXT_W = R.color.textblack
            "7" -> Constants.COLOR_TEXT_W = R.color.white
            "8" -> Constants.COLOR_TEXT_W = R.color.textblack
            "9" -> Constants.COLOR_TEXT_W = R.color.white
            "10" -> Constants.COLOR_TEXT_W = R.color.white
            "11" -> Constants.COLOR_TEXT_W = R.color.white
            else -> Constants.COLOR_TEXT_W = R.color.textblack

        }

        when (SharedPreferenceFactory.getStrValue("color", "0")) {
            "1" -> Constants.COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back
            "2" -> Constants.COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back_n
            "3" -> Constants.COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back
            "4" -> Constants.COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back_n
            "5" -> Constants.COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back
            "6" -> Constants.COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back_n
            "7" -> Constants.COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back
            "8" -> Constants.COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back_n
            "9" -> Constants.COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back
            "10" -> Constants.COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back
            "11" -> Constants.COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back
            else-> Constants.COLOR_ICON_BACKSEARCH = R.drawable.icon_search_back_n

        }

        when (SharedPreferenceFactory.getStrValue("color", "0")) {
            "1" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel
            "2" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel_n
            "3" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel
            "4" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel_n
            "5" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel
            "6" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel_n
            "7" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel
            "8" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel_n
            "9" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel
            "10" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel
            "11" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel
            else -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel_n
        }

        when (SharedPreferenceFactory.getStrValue("color", "0")) {
            "1" -> Constants.COLOR_ICON_BACKGROUND = R.drawable.icon_main_back
            "2" -> Constants.COLOR_ICON_BACKGROUND = R.drawable.icon_main_back_n
            "3" -> Constants.COLOR_ICON_BACKGROUND = R.drawable.icon_main_back
            "4" -> Constants.COLOR_ICON_BACKGROUND = R.drawable.icon_main_back_n
            "5" -> Constants.COLOR_ICON_BACKGROUND = R.drawable.icon_main_back
            "6" -> Constants.COLOR_ICON_BACKGROUND = R.drawable.icon_main_back_n
            "7" -> Constants.COLOR_ICON_BACKGROUND = R.drawable.icon_main_back
            "8" -> Constants.COLOR_ICON_BACKGROUND = R.drawable.icon_main_back_n
            "9" -> Constants.COLOR_ICON_BACKGROUND = R.drawable.icon_main_back
            "10" -> Constants.COLOR_ICON_BACKGROUND = R.drawable.icon_main_back
            "11" -> Constants.COLOR_ICON_BACKGROUND = R.drawable.icon_main_back
            else-> Constants.COLOR_ICON_BACKGROUND = R.drawable.icon_main_back_n

        }

        when (SharedPreferenceFactory.getStrValue("color", "0")) {
            "1" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel
            "2" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel_n
            "3" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel
            "4" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel_n
            "5" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel
            "6" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel_n
            "7" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel
            "8" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel_n
            "9" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel
            "10" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel
            "11" -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel
            else -> Constants.COLOR_ICON_CANCEL = R.drawable.icon_cancel_n
        }
        //my
        when (SharedPreferenceFactory.getStrValue("color", "0")) {
            "1" -> Constants.COLOR_ICON_MYALARM = R.drawable.icon_my_alarm
            "2" -> Constants.COLOR_ICON_MYALARM = R.drawable.icon_my_alarm_n
            "3" -> Constants.COLOR_ICON_MYALARM = R.drawable.icon_my_alarm
            "4" -> Constants.COLOR_ICON_MYALARM = R.drawable.icon_my_alarm_n
            "5" -> Constants.COLOR_ICON_MYALARM = R.drawable.icon_my_alarm
            "6" -> Constants.COLOR_ICON_MYALARM = R.drawable.icon_my_alarm_n
            "7" -> Constants.COLOR_ICON_MYALARM = R.drawable.icon_my_alarm
            "8" -> Constants.COLOR_ICON_MYALARM = R.drawable.icon_my_alarm_n
            "9" -> Constants.COLOR_ICON_MYALARM = R.drawable.icon_my_alarm
            "10" -> Constants.COLOR_ICON_MYALARM = R.drawable.icon_my_alarm
            "11" -> Constants.COLOR_ICON_MYALARM = R.drawable.icon_my_alarm
            else -> Constants.COLOR_ICON_MYALARM = R.drawable.icon_my_alarm_n
        }

        when (SharedPreferenceFactory.getStrValue("color", "0")) {
            "1" -> Constants.COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm
            "2" -> Constants.COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm_n
            "3" -> Constants.COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm
            "4" -> Constants.COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm_n
            "5" -> Constants.COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm
            "6" -> Constants.COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm_n
            "7" -> Constants.COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm
            "8" -> Constants.COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm_n
            "9" -> Constants.COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm
            "10" -> Constants.COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm
            "11" -> Constants.COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm
            else -> Constants.COLOR_ICON_FRALARM = R.drawable.icon_fr_alarm_n

        }




        when (SharedPreferenceFactory.getStrValue("color", "0")) {
            "1" -> Constants.COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2
            "2" -> Constants.COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2_n
            "3" -> Constants.COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2
            "4" -> Constants.COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2_n
            "5" -> Constants.COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2
            "6" -> Constants.COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2_n
            "7" -> Constants.COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2
            "8" -> Constants.COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2_n
            "9" -> Constants.COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2
            "10" -> Constants.COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2
              "11" -> Constants.COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2
            else -> Constants.COLOR_ICON_PROFILE_IMAGE = R.drawable.icon_camera2_n

        }

        when (SharedPreferenceFactory.getStrValue("color", "0")) {
            "1" -> Constants.COLOR_SEARCH_BACK = R.color.red
            "2" -> Constants.COLOR_SEARCH_BACK = R.color.yellow
            "3" -> Constants.COLOR_SEARCH_BACK = R.color.blue
            "4" -> Constants.COLOR_SEARCH_BACK = R.color.mint
            "5" -> Constants.COLOR_SEARCH_BACK = R.color.purple
            "6" -> Constants.COLOR_SEARCH_BACK = R.color.white
            "7" -> Constants.COLOR_SEARCH_BACK = R.drawable.red_g
            "8" -> Constants.COLOR_SEARCH_BACK = R.drawable.yellow_g
            "9" -> Constants.COLOR_SEARCH_BACK = R.drawable.blue_g
            "10" -> Constants.COLOR_SEARCH_BACK = R.drawable.puple_g
            "11" -> Constants.COLOR_SEARCH_BACK = R.drawable.mint_g
            else -> Constants.COLOR_SEARCH_BACK = R.color.white

        }


        when (SharedPreferenceFactory.getStrValue("color", "0")) {

            "1" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
            "2" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2_n
            "3" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
            "4" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2_n
            "5" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
            "6" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2_n
            "7" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
            "8" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2_n
            "9" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
            "10" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
            "11" -> Constants.COLOR_FRIENDLIST =  R.drawable.icon_friendprofile2
            else -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2_n
        }


        when (SharedPreferenceFactory.getStrValue("color", "0")) {

            "1" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
            "2" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2_n
            "3" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
            "4" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2_n
            "5" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
            "6" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2_n
            "7" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
            "8" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2_n
            "9" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
            "10" -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2
            "11" -> Constants.COLOR_FRIENDLIST =  R.drawable.icon_friendprofile2
            else -> Constants.COLOR_FRIENDLIST = R.drawable.icon_friendprofile2_n
        }


        when (SharedPreferenceFactory.getStrValue("color", "0")) {

            "1" -> Constants.COLOR_HOME_ITEM = R.drawable.home_corner_drawable
            "2" -> Constants.COLOR_HOME_ITEM = R.drawable.home_corner_drawable_n
            "3" -> Constants.COLOR_HOME_ITEM = R.drawable.home_corner_drawable
            "4" -> Constants.COLOR_HOME_ITEM = R.drawable.home_corner_drawable_n
            "5" -> Constants.COLOR_HOME_ITEM = R.drawable.home_corner_drawable
            "6" -> Constants.COLOR_HOME_ITEM = R.drawable.home_corner_drawable_n
            "7" -> Constants.COLOR_HOME_ITEM = R.drawable.home_corner_drawable
            "8" -> Constants.COLOR_HOME_ITEM = R.drawable.home_corner_drawable_n
            "9" -> Constants.COLOR_HOME_ITEM = R.drawable.home_corner_drawable
            "10" -> Constants.COLOR_HOME_ITEM = R.drawable.home_corner_drawable
            "11" -> Constants.COLOR_FRIENDLIST =  R.drawable.home_corner_drawable
            else -> Constants.COLOR_FRIENDLIST = R.drawable.home_corner_drawable_n
        }
    }


}