package com.poly.test.diaryapp.Calendar

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.poly.test.diaryapp.R
import kotlinx.android.synthetic.main.date_dialog.*

class DatePickDialog(context : Context) : Dialog(context) {

    lateinit var confirmHandler: (tripleDate: Triple<Int, Int, Int>) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.date_dialog)

    }



    fun defaultSetting(date: Triple<Int,Int,Int>, strSub:String, conFirmHandler: (tripleDate: Triple<Int,Int,Int>) -> Unit){

        this.confirmHandler = conFirmHandler

        // 쓰는 액티비티에서 값 넘겨주기
        date_picker.updateDate(date.first, date.second, date.third)

        choice_btn.setOnClickListener {
            dismiss()
            val tripleDate = Triple(date_picker.year, date_picker.month, date_picker.dayOfMonth)
            this.confirmHandler.invoke(tripleDate)
        }

        close_btn.setOnClickListener {
            dismiss()
        }

    }


    override fun dismiss() {
        super.dismiss()
    }
}