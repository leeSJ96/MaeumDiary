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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.models.CalendarModel
import com.poly.test.diaryapp.utils.SharedPreferenceFactory
import kotlinx.android.synthetic.main.activity_calendar_write.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class CalendarWriteActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var currentDate: Triple<Int, Int, Int>

    private var dialogDatePick: DatePickDialog? = null
    private val PICK_IMAGE_FROM_ALBUM = 0
    private var photoUri: Uri? = null
    private var photoUse: Boolean = false
    private var storage: FirebaseStorage? = null
    private var fireStore: FirebaseFirestore? = null

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


        // 사진 삭제하기
        add_photo_image.setOnLongClickListener {

            add_photo_image.visibility = View.INVISIBLE
            photoUri = null
            photoUse = false

            return@setOnLongClickListener true
        }

    }


    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        Log.d("로그", "확인용0")
        when (v) {

            date_title -> {

                if (dialogDatePick == null) dialogDatePick = DatePickDialog(this)

                dialogDatePick?.show()

                dialogDatePick?.defaultSetting(currentDate, "년월일") { tripleDate ->
                    currentDate = tripleDate
                    date_title.text = "${currentDate.first}년 ${currentDate.second + 1}월 ${currentDate.third}일"

          
                }

            }

            btn_img_input -> {

                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)

            }

            btn_save -> {
                Log.d("로그", "확인용1")
                if(!edit_content.text.isNullOrBlank()) {
                    Log.d("로그", "확인용2")
                    hideAndShowUi(true)

                    when(photoUse) {

                        true -> contentUploadPhoto()

                        false -> contentUploadNoPhoto()

                    }

                } else {
                    Snackbar.make(calendar_write_layout, "제목을 입력해주세요", Snackbar.LENGTH_SHORT).show()

                }

            }

            back_btn -> {
                onBackPressed()
            }


        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_ALBUM) {

            if (resultCode == Activity.RESULT_OK) {
                add_photo_image.visibility = View.VISIBLE
                photoUri = data?.data
                photoUse = true
                add_photo_image.setImageURI(data?.data)
                Log.d("로그", "photoUri $photoUri")
            } else {
                finish()
            }

        }

    }


    @SuppressLint("SimpleDateFormat")
    private fun contentUploadPhoto() {

        var uri: String? = null
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_.png"
        val storageRef = storage?.reference?.child("images")?.child(imageFileName)
        val uidValue = SharedPreferenceFactory.getStrValue("userToken", null)
        val nameValue = SharedPreferenceFactory.getStrValue("userName", null)
        val path = "${uidValue}_${System.currentTimeMillis()}"

        val day = Calendar.getInstance()
        var mondata = day.get(Calendar.MONTH)
        storageRef?.putFile(photoUri!!)?.addOnSuccessListener {


            storageRef.downloadUrl.addOnSuccessListener {
                uri = it.toString()

                val calendarModel = CalendarModel()

                calendarModel.apply {
                    uid = uidValue
                    name = nameValue
                    content = edit_content.text.toString()
                    title = edit_title.text.toString()
                    date = date_title.text.toString()
                    photoUri = uri
                    uploadTime = System.currentTimeMillis()
                    pathData = path
                    mon = mondata.toString()
                }
                fireStore?.collection("user_calendar")?.document(path)?.set(calendarModel)?.addOnSuccessListener {

                    hideAndShowUi(false)
                    setResult(Activity.RESULT_OK)
                    finish()


                }?.addOnFailureListener {
                    errorMessageShow(it)
                }

            }.addOnFailureListener {
                errorMessageShow(it)
            }
        }?.addOnFailureListener {
            errorMessageShow(it)
        }

    }



    private fun contentUploadNoPhoto() {


        val calendarModel = CalendarModel()
        val uidValue = SharedPreferenceFactory.getStrValue("userToken", null)
        val nameValue = SharedPreferenceFactory.getStrValue("userName", null)
        val path = "${uidValue}_${System.currentTimeMillis()}"


        val day = Calendar.getInstance()
        var mondata = day.get(Calendar.MONTH)

        calendarModel.apply {
            uid = uidValue
            name = nameValue
            content = edit_content.text.toString()
            title = edit_title.text.toString()
            date = date_title.text.toString()
            photoUri = "NullPhotoLink"
            uploadTime = System.currentTimeMillis()
            pathData = path
            mon = mondata.toString()

        }

        fireStore?.collection("user_calendar")?.document(path)?.set(calendarModel)?.addOnSuccessListener {

            hideAndShowUi(false)
            setResult(Activity.RESULT_OK)
            finish()

        }?.addOnFailureListener {
            errorMessageShow(it)
        }

    }


    private fun hideAndShowUi(hideCheck: Boolean) {

        when (hideCheck) {

            true -> {

                loading_progress.visibility = View.VISIBLE
                btn_save.isEnabled = false
                btn_img_input.isEnabled = false
                back_btn.isEnabled = false
                edit_title.isEnabled = false
                edit_content.isEnabled = false
                date_title.isEnabled = false

            }

            false -> {

                loading_progress.visibility = View.INVISIBLE
                btn_save.isEnabled = true
                btn_img_input.isEnabled = true
                back_btn.isEnabled = true
                edit_title.isEnabled = true
                edit_content.isEnabled = true
                date_title.isEnabled = true

            }

        }

    }

    private fun errorMessageShow(error: Exception) {

        Log.d("로그", "error $error")

        Toast.makeText(this, "업로드 실패/ 서버 에러", Toast.LENGTH_SHORT).show()
        hideAndShowUi(false)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.page_left_in, R.anim.page_right_out)
    }


}