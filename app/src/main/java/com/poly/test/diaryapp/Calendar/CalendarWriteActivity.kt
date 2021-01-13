package com.poly.test.diaryapp.Calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.poly.test.diaryapp.R

class CalendarWriteActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_write)


    }


    override fun onBackPressed() {
        super.onBackPressed()

        overridePendingTransition(R.anim.page_left_in, R.anim.page_right_out)
    }
}