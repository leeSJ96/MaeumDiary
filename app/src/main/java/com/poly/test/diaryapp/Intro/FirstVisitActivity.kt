package com.poly.test.diaryapp.Intro

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.poly.test.diaryapp.MainActivity
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.utils.SharedPreferenceFactory
import com.poly.test.diaryapp.utils.onMyTextChanged
import kotlinx.android.synthetic.main.activity_firstvisit.*

class FirstVisitActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstvisit)


//        val pref: SharedPreferences = getSharedPreferences("ref", Context.MODE_PRIVATE)
//        val email = pref.getString("userEmail", "")
//        val uid = pref.getString("userToken", "")
        val uid = SharedPreferenceFactory.getStrValue("userToken", "")
        val email = SharedPreferenceFactory.getStrValue("userEmail", "")
        Log.d("로그", "First email : $email")
        Log.d("로그", "First uid : $uid ")

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

//        val pref: SharedPreferences = getSharedPreferences("ref", Context.MODE_PRIVATE)
//        val edit = pref.edit()

        nameMap[email] = name
        uidMap[email] = uid



        nameStore.add(nameMap).addOnSuccessListener {
            SharedPreferenceFactory.putStrValue("userName" ,nameMap[email])
//            edit.putString("userName", nameMap[email])
//            edit.apply()
//

            Log.d("로그","name ${nameMap[email]}")
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