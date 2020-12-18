package com.poly.test.diaryapp.Intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.poly.test.diaryapp.MainActivity
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.utils.Constants.USER

class IntroActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)


        auth = Firebase.auth

        val currentUser = auth.currentUser

        moveNext(currentUser)

    }


    // 로그인 유무에 따라 페이지 이동이 갈림
    private fun moveNext(account: FirebaseUser?) {

        var intent = Intent(this, MainActivity::class.java)

        if (account != null) {
            Log.d("로그"," 익명 로그인 $account")
            USER = account.toString()
            startActivity(intent)
            overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)
        } else {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)
        }


    }
}