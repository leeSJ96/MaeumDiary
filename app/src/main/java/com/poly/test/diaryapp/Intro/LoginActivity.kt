package com.poly.test.diaryapp.Intro

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.poly.test.diaryapp.MainActivity
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.utils.Constants.EmailFormError
import com.poly.test.diaryapp.utils.Constants.IdFail
import com.poly.test.diaryapp.utils.Constants.PasswordFail
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {


    private var firebaseAuth = FirebaseAuth.getInstance()
    private var coroutineJob = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val idValue = intent.getStringExtra("id")
        val pwValue = intent.getStringExtra("pw")
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        if (idValue != null) {
            login_email_input.setText(idValue)
            login_password_input.setText(pwValue)
        }


        login_btn.setOnClickListener {

            imm.hideSoftInputFromWindow(login_password_input.windowToken, 0);

            when {
                login_email_input.text.isNullOrEmpty() -> {
                    Snackbar.make(login_layout, "아이디 값이 비었습니다", Snackbar.LENGTH_SHORT).show()
                }
                login_password_input.text.isNullOrEmpty() -> {
                    Snackbar.make(login_layout, "패스워드 값이 비었습니다", Snackbar.LENGTH_SHORT).show()
                }
                else -> {
                    login_btn.isEnabled = false
                    login_btn.text = "조회 중입니다."
                    loginCheck()
                }
            }

        }

        create_auth_btn.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)
        }

        missing_auth_btn.setOnClickListener {
            // 아이디 찾기 액티비티로 이동 시킬 예정
            Snackbar.make(login_layout, "아직 구현 중입니다", Snackbar.LENGTH_SHORT).show()

        }

    }


    private fun loginCheck() {

        val id = login_email_input.text.toString()
        val pw = login_password_input.text.toString()

        val pref: SharedPreferences = getSharedPreferences("ref", Context.MODE_PRIVATE)
        val editor = pref.edit()

        val store = FirebaseFirestore.getInstance().collection("user_uid")
        val checkKey = ArrayList<String>()
        val checkValue = ArrayList<String>()
        var authFirstCheck = true
        var name = ""
        var uid = ""

        firebaseAuth.signInWithEmailAndPassword(id, pw)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        var intent = Intent(applicationContext, FirstVisitActivity::class.java)
                        uid = firebaseAuth.uid.toString()
                        editor.putString("userToken", uid)
                        editor.apply()

                        CoroutineScope(Dispatchers.Main).launch {

                            store.get().addOnSuccessListener {

                                coroutineJob = CoroutineScope(Dispatchers.IO).launch {

                                    withContext(Dispatchers.IO) {
                                        for (document in it) {
                                            checkKey.add(document.data.keys.toString())
                                            checkValue.add(document.data.values.toString())
                                        }

                                        for (i in checkKey.indices) {

                                            name = checkKey[i].replace("[", "").replace("]", "")
                                            Log.d("로그", "key size ${checkKey.size}")
                                            Log.d("로그", "key name $name")
                                            if (name == id) {
                                                authFirstCheck = false
                                                Log.d("로그", "uid ${checkValue[i]}")
                                            }
                                        }
                                    }

                                    withContext(Dispatchers.Main) {
                                        intent = when (authFirstCheck) {
                                            true -> Intent(this@LoginActivity, FirstVisitActivity::class.java)
                                            false -> Intent(this@LoginActivity, MainActivity::class.java)
                                        }
                                        intent.putExtra("email", id)  // 이메일
                                        intent.putExtra("uid", uid)  // uid
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                        coroutineJob.cancel()
                                        startActivity(intent)
                                        this@LoginActivity.finish()
                                        overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)
                                    }
                                }
                            }

                            coroutineJob.join()

                        } // 코루틴 스코프


                    } // if success

                } // addOnComplete

                .addOnFailureListener {
                    login_btn.isEnabled = true
                    login_btn.text = "로그인하기"

                    when {
                        IdFail in it.toString() -> {
                            Snackbar.make(login_layout, "아이디가 없습니다", Snackbar.LENGTH_SHORT).show()
                        }
                        PasswordFail in it.toString() -> {
                            Snackbar.make(login_layout, "비밀번호가 일치하지 않습니다", Snackbar.LENGTH_SHORT).show()
                        }
                        EmailFormError in it.toString() -> {
                            Snackbar.make(login_layout, "아이디는 이메일 형식으로 작성해주세요", Snackbar.LENGTH_SHORT)
                                    .show()
                        }
                    }
                }

    }


    override fun onDestroy() {
        super.onDestroy()
        coroutineJob.cancel()
    }
}