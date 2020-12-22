package com.poly.test.diaryapp.Intro

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.poly.test.diaryapp.MainActivity
import com.poly.test.diaryapp.R
import kotlinx.coroutines.*

class IntroActivity : AppCompatActivity() {


    private var coroutineJob = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val pref: SharedPreferences = getSharedPreferences("ref", Context.MODE_PRIVATE)
        val uid = pref.getString("userToken", null)


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
                        intent.putExtra("email", emailValue)
                        intent.putExtra("uid", uid)
                        startActivity(intent)
                        this@IntroActivity.finish()
                        overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)
                        coroutineJob.cancel()

                    }

                    coroutineJob.join()
                }


            }

        }


    }


    override fun onDestroy() {
        super.onDestroy()
        coroutineJob.cancel()
    }
}