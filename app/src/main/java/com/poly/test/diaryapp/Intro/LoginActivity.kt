package com.poly.test.diaryapp.Intro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.poly.test.diaryapp.MainActivity
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.utils.Constants.EmailFormError
import com.poly.test.diaryapp.utils.Constants.IdFail
import com.poly.test.diaryapp.utils.Constants.PasswordFail
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {


    private var firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val idValue = intent.getStringExtra("id")
        val pwValue = intent.getStringExtra("pw")

        if (idValue != null) {
            login_email_input.setText(idValue)
            login_password_input.setText(pwValue)
        }


        login_btn.setOnClickListener {
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

        firebaseAuth.signInWithEmailAndPassword(id, pw)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        storeSave()

                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)

                        this.finish()

                        login_btn.isEnabled = true
                        login_btn.text = "로그인하기"
                    }

                }
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


    private fun storeSave() {

        // 구현 중...
        
        val mUser = firebaseAuth.currentUser?.uid
        Log.d("로그","mUser $mUser")


    }


}