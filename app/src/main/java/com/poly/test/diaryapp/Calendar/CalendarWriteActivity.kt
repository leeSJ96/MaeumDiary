package com.poly.test.diaryapp.Calendar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.models.CalendarModel
import kotlinx.android.synthetic.main.activity_calendar_write.*
import java.text.SimpleDateFormat
import java.util.*


class CalendarWriteActivity : AppCompatActivity() {

    private lateinit var currentDate: Triple<Int, Int, Int>

    private var dialogDatePick: DatePickDialog? = null
    private val PICK_IMAGE_FROM_ALBUM = 0
    private var photoUri: Uri? = null
    private var photoUse : Boolean = false
    private var storage: FirebaseStorage? = null
    private var fireStore: FirebaseFirestore? = null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_write)

        val todayDate = intent.getStringExtra("today")
        val year = 2021
        val month = 1
        val day = 1

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        storage = FirebaseStorage.getInstance()
        fireStore = FirebaseFirestore.getInstance()


        date_title.text = todayDate

        currentDate = Triple(year, month, day)

        date_title.setOnClickListener {

            if (dialogDatePick == null)
                dialogDatePick = DatePickDialog(this)

            dialogDatePick?.show()

            dialogDatePick?.defaultSetting(currentDate, "년월일") { tripleDate ->
                currentDate = tripleDate
                date_title.text =
                    "${currentDate.first}년 ${currentDate.second + 1}월 ${currentDate.third}일"
            }

        }

        btn_img_input.setOnClickListener {

            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)

        }

        btn_save.setOnClickListener {

            when {

                edit_title.text.count() < 1 -> {
                    Snackbar.make(calendar_write_layout, "제목을 적어주세요.", Snackbar.LENGTH_SHORT).show()
                }

                else -> {

                    if(photoUse) {
                        contentUploadPhoto()    // 사진을 추가했을 때
                    } else {
                        contentUploadNoPhoto() // 사진을 추가하지 않았을 때
                    }
                }
            }

        }

        back_btn.setOnClickListener {
            onBackPressed()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_ALBUM) {

            if (resultCode == Activity.RESULT_OK) {
                add_photo_image.visibility = View.VISIBLE
                btn_img_input.isEnabled = false
                photoUri = data?.data
                photoUse = true
                add_photo_image.setImageURI(data?.data)
                Log.d("로그", "photoUri $photoUri")
            } else {
                finish()
            }

        }

    }


    private fun contentUploadPhoto() {

        val pref: SharedPreferences = getSharedPreferences("ref", Context.MODE_PRIVATE)
        var uri : String? = null
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_.png"
        val storageRef = storage?.reference?.child("images")?.child(imageFileName)

        storageRef?.putFile(photoUri!!)?.addOnSuccessListener {


            storageRef.downloadUrl.addOnSuccessListener {
                uri = it.toString()
                Log.d("로그", "success $it")
                Log.d("로그","uri $uri")

                val calendarModel = CalendarModel()

                calendarModel.apply {
                    uid = pref.getString("userToken", null)
                    name = pref.getString("userName",null)
                    content = edit_content.text.toString()
                    title = edit_title.text.toString()
                    date = date_title.text.toString()
                    photoUri = uri
                }

                fireStore?.collection("user_calendar")?.document()?.set(calendarModel)
                setResult(Activity.RESULT_OK)
                finish()

            }.addOnFailureListener {
                Log.d("로그", "error $it")
            }
        }

    }

    private fun contentUploadNoPhoto() {
        val calendarModel = CalendarModel()
        val pref: SharedPreferences = getSharedPreferences("ref", Context.MODE_PRIVATE)

        calendarModel.apply {
            uid = pref.getString("userToken", null)
            name = pref.getString("userName",null)
            content = edit_content.text.toString()
            title = edit_title.text.toString()
            date = date_title.text.toString()
            photoUri = null
        }

        fireStore?.collection("user_calendar")?.document()?.set(calendarModel)
        setResult(Activity.RESULT_OK)
        finish()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.page_left_in, R.anim.page_right_out)
    }



}