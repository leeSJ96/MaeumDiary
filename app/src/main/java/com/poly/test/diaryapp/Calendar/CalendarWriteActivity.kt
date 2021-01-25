package com.poly.test.diaryapp.Calendar

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.models.CalendarModel
import kotlinx.android.synthetic.main.activity_calendar_write.*


class CalendarWriteActivity : AppCompatActivity() {


    private var dialogDatePick: DatePickDialog? = null
    private lateinit var currentDate: Triple<Int, Int, Int>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_write)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        val date = intent.getStringExtra("selectDate")
        val todayDate = intent.getStringExtra("today")
        date_title.text = todayDate

        val year = 2021
        val month = 1
        val day = 1

        currentDate = Triple(year, month, day)

        date_title.setOnClickListener {

            if (dialogDatePick == null)
                dialogDatePick = DatePickDialog(this)

            dialogDatePick?.show()

            dialogDatePick?.defaultSetting(currentDate, "년월일") { tripleDate ->
                currentDate = tripleDate
                date_title.text = "${currentDate.first}년 ${currentDate.second + 1}월 ${currentDate.third}일"
            }


        }

//        btn_save.setOnClickListener {
//
//            when {
//
//                edit_title.text.count() < 1 -> {
//                    Snackbar.make(calendar_write_layout, "제목을 적어주세요.", Snackbar.LENGTH_SHORT).show()
//                }
//
//                else -> {
//                    dataInput(date!!)
//                }
//            }
//
//        }

        back_btn.setOnClickListener {
            onBackPressed()
        }

    }

    private fun dataInput(selectDate: String) {

        val saveData = CalendarModel()
        val pref: SharedPreferences = getSharedPreferences("ref", Context.MODE_PRIVATE)
        val userName = pref.getString("userName", "")
        val userUid = pref.getString("userToken", "")
        val contentTitle = edit_title.text.toString()
        val contentDetail: String = when (edit_content.text.count()) {

            0 -> "no content"

            else -> edit_content.text.toString()

        }

        saveData.apply {
            name = userName
            uid = userUid
            content = contentDetail
            title = contentTitle
            date = selectDate
        }


        FirebaseFirestore.getInstance().collection("user_calendar")
            .add(saveData).addOnSuccessListener {
                Log.d("로그", "성공")
            }
            .addOnFailureListener {
                Log.d("로그", "실패 $it")
            }

        finish()

    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.page_left_in, R.anim.page_right_out)
    }
}