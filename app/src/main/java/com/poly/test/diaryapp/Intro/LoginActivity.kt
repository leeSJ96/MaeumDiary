package com.poly.test.diaryapp.Intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.poly.test.diaryapp.MainActivity
import com.poly.test.diaryapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        start_tv.setOnClickListener {
            setAuth()
        }
    }

    // 텍스트 클릭 시 메인으로 이동
    private fun setAuth() {

        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val intent = Intent(this, MainActivity::class.java)
                    Log.d("로그", "user $user")
                    startActivity(intent)

                    overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)

                } else {
                    Log.d("로그", "fail ${task.exception}")
                }


            }
    }

}