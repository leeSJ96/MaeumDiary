package com.poly.test.diaryapp.Intro

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.poly.test.diaryapp.MainActivity
import com.poly.test.diaryapp.MyApplication
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.utils.Constants.COLOR_BG
import kotlinx.coroutines.*
import java.lang.Runnable

class IntroActivity : AppCompatActivity() {


    private var coroutineJob = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        BgColorShard()
//        val pref: SharedPreferences = getSharedPreferences("ref", Context.MODE_PRIVATE)
//        val uid = pref.getString("userToken", null)
//        val name = pref.getString("userName","")
//        val email = pref.getString("userEmail","")
//        Log.d("로그","uid $uid")
//        Log.d("로그","name $name")
//        Log.d("로그","email $email")

//        val uid = SharedPreferenceFactory.getStrValue(this,"userToken" ,null)
//        val name = SharedPreferenceFactory.getStrValue(this,"userName" ,"")
//        val email = SharedPreferenceFactory.getStrValue(this,"userEmail" ,"")

        val uid = MyApplication.prefs.getString("userToken", "")
        val name = MyApplication.prefs.getString("userName", "")
        val email =  MyApplication.prefs.getString("userEmail", "")

        if (uid != null) {

            moveNext(uid)

        } else {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun moveNext(uid: String) {

        val store = FirebaseFirestore.getInstance().collection("user_uid")
        val checkKey = ArrayList<String>()
        val checkValue = ArrayList<String>()
        var uidValue = ""
        var emailValue = ""
        var uidCheck = false

        Handler(Looper.getMainLooper()).postDelayed({



        CoroutineScope(Dispatchers.Main).launch {

            store.get().addOnSuccessListener {

                coroutineJob = CoroutineScope(Dispatchers.IO).launch {

                    withContext(Dispatchers.IO) {

                        for (document in it) {
                            checkKey.add(document.data.keys.toString())
                            checkValue.add(document.data.values.toString())
                        }

                        for (i in checkValue.indices) {

                            uidValue = checkValue[i].replace("[", "").replace("]", "")
                            if (uidValue == uid) {
                                uidCheck = true
                                emailValue = checkKey[i].replace("[", "").replace("]", "")
                            }

                        }

                    }

                    withContext(Dispatchers.Main) {
                        val intent = when (uidCheck) {
                            true -> Intent(this@IntroActivity, MainActivity::class.java)
                            false -> Intent(this@IntroActivity, LoginActivity::class.java)

                        }
                        startActivity(intent)
                        this@IntroActivity.finish()
                        overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)
                        coroutineJob.cancel()

                    }

                    coroutineJob.join()
                }


            }

        }

        },1500)
    }

    fun BgColorShard(){

        when( MyApplication.prefs.getString("color","0")){
            "1" -> COLOR_BG = R.color.red
            "2" -> COLOR_BG = R.color.yellow
            "3" -> COLOR_BG = R.color.blue
            "4" -> COLOR_BG = R.color.mint
            "5" -> COLOR_BG = R.color.purple
            else -> COLOR_BG = R.color.white
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        coroutineJob.cancel()
    }
}