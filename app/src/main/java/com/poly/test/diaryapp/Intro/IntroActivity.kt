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
import com.poly.test.diaryapp.R
import kotlinx.coroutines.*
import java.lang.Runnable

class IntroActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val pref: SharedPreferences = getSharedPreferences("ref", Context.MODE_PRIVATE)
        val uid = pref.getString("userToken", null)
        val name = pref.getString("userName","")
        val email = pref.getString("userEmail","")

        Log.d("로그","uid $uid")
        Log.d("로그","name $name")
        Log.d("로그","email $email")

        if (uid != null) {

            moveNext(uid, email)

        } else {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun moveNext(uid: String, email : String?) {

        val store = FirebaseFirestore.getInstance().collection("user_uid")
        var emailValue = ""
        var uidCheck = false

        if(email != null) emailValue = email


        Handler(Looper.getMainLooper()).postDelayed({

            store.whereEqualTo(emailValue, uid).addSnapshotListener { value, error ->

                if(value != null) {
                    uidCheck = true
                }

                val intent = when(uidCheck) {
                    true -> Intent(this@IntroActivity, MainActivity::class.java)
                    false -> Intent(this@IntroActivity, LoginActivity::class.java)
                }
                startActivity(intent)
                this@IntroActivity.finish()
                overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)

            }

        },1500)
    }



}