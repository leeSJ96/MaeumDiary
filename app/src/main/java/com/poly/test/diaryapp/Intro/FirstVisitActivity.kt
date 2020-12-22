package com.poly.test.diaryapp.Intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.poly.test.diaryapp.MainActivity
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.utils.onMyTextChanged
import kotlinx.android.synthetic.main.activity_firstvisit.*

class FirstVisitActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstvisit)

        val email = intent.getStringExtra("email")
        val uid = intent.getStringExtra("uid")




        Log.d("로그", "email $email")
        Log.d("로그", "uid $uid ")

        name_edit.onMyTextChanged {

            if (it.toString().count() > 0) {
                start_btn.visibility = View.VISIBLE

                scrollView.scrollTo(0, 200)
            } else {
                start_btn.visibility = View.INVISIBLE
            }

            if (it.toString().count() == 15) {
                Snackbar.make(scrollView, "15자까지만 입력 가능합니다", Snackbar.LENGTH_SHORT).show()
            }

        }

        start_btn.setOnClickListener {
            nameSaveDB(email!!, uid!!)
        }


    }


    // 1. 닉네임 넣기
    // 2. uid 저장하기
    private fun nameSaveDB(userEmail: String, userUid: String) {

        Log.d("로그", "nameSaveDB - call")
        val name = name_edit.text.toString()
        val email: String = userEmail
        val uid: String = userUid
        val nameStore = FirebaseFirestore.getInstance().collection("user_name")
        val uidStore = FirebaseFirestore.getInstance().collection("user_uid")

        val nameMap: MutableMap<String, String> = HashMap()
        val uidMap: MutableMap<String, String> = HashMap()

        nameMap[email] = name
        uidMap[email] = uid

        nameStore.add(nameMap).addOnSuccessListener {

            Log.d("로그", "이름 저장 성공")
        }

        uidStore.add(uidMap).addOnSuccessListener {

            Log.d("로그", "uid 저장 성공")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)
            finish()
        }


    }


}