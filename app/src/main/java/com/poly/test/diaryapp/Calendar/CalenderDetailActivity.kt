package com.poly.test.diaryapp.Calendar

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.models.CalendarModel
import com.poly.test.diaryapp.utils.Constants
import kotlinx.android.synthetic.main.activity_calendar_detail.*


class CalendarDetailActivity : AppCompatActivity(), View.OnClickListener {

    val context: Context = this

    private var dateModel: CalendarModel? = null
    private var deleteDialog: DeleteDialog? = null
    private var sharedDialog: SharedDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_detail)

        dateModel = intent.getParcelableExtra<CalendarModel>("dateModel")


        dateModel?.apply {
            day_tv.text = date

            if (this.photoUri != "NullPhotoLink") {
                Glide.with(context).load(photoUri)
                    .placeholder(R.drawable.ic_baseline_calendar_today_24)
                    .error(R.drawable.ic_baseline_calendar_today_24)
                    .transform(CenterCrop(), RoundedCorners(20))
                    .into(detail_img)
            } else {
                detail_img.visibility = View.GONE
            }

            title_tv.text = title
            detail_content.text = content

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.page_left_in, R.anim.page_right_out)
        finish()
    }

    override fun onClick(view: View?) {

        when (view) {
            delete_tv -> {
                Constants.LIST = 0
                contentDelete()
            }

            update_tv -> {
                contentUpdate()
            }


            share_tv -> {
                contentShared()
            }

        }

    }

    private fun contentDelete() {

        val path = dateModel?.pathData

        if (deleteDialog == null) {
            deleteDialog = DeleteDialog(this)
        }

        deleteDialog?.show()
        deleteDialog?.deleteSetting(path!!) { result ->

            if (result) {
                finish()
            }

        }

    }


    /**
    UpdateActivity 생성
    - 데이터 전부 넘기기 (모델 데이터)
    - 성공 시 리스트로 이동
     */

    private fun contentUpdate() {

        val intent = Intent(this, CalendarUpdateActivity::class.java)
        intent.putExtra("dateModel", dateModel)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)

    }

    private fun contentShared() {

        sharedDialog = SharedDialog(this, dateModel!!)
        sharedDialog?.show()




    }


}