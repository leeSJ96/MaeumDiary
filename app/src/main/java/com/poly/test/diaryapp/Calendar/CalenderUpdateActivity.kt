package com.poly.test.diaryapp.Calendar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.models.CalendarModel
import com.poly.test.diaryapp.utils.SharedPreferenceFactory
import kotlinx.android.synthetic.main.activity_calendar_update.*
import kotlinx.android.synthetic.main.activity_calendar_update.add_photo_image
import kotlinx.android.synthetic.main.activity_calendar_update.back_btn
import kotlinx.android.synthetic.main.activity_calendar_update.btn_img_input
import kotlinx.android.synthetic.main.activity_calendar_update.btn_save
import kotlinx.android.synthetic.main.activity_calendar_update.date_title
import kotlinx.android.synthetic.main.activity_calendar_update.edit_content
import kotlinx.android.synthetic.main.activity_calendar_update.edit_title
import kotlinx.android.synthetic.main.activity_calendar_write.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class CalendarUpdateActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var currentDate: Triple<Int, Int, Int>
    private val PICK_IMAGE_FROM_ALBUM = 0

    private var dateModel: CalendarModel? = CalendarModel()
    private var dialogDatePick: DatePickDialog? = null
    private var photoUri: Uri? = null
    private var photoUse: Boolean = false
    private var storage: FirebaseStorage? = null
    private var fireStore: FirebaseFirestore? = null
    private val date = Calendar.getInstance()
    private val monData = date.get(Calendar.MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_update)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        storage = FirebaseStorage.getInstance()
        fireStore = FirebaseFirestore.getInstance()

        currentDate = Triple(2021, 1, 1)


        dateModel = intent.getParcelableExtra<CalendarModel>("dateModel")

        dateModel!!.let {

            date_title.text = it.date
            edit_title.setText(it.title)
            edit_content.setText(it.content)

            if (it.photoUri != "NullPhotoLink") {

                photoUse = true
                add_photo_image.visibility = View.VISIBLE

                Glide.with(this)
                    .load(it.photoUri)
                    .placeholder(R.drawable.ic_baseline_calendar_today_24)
                    .into(add_photo_image)
            } else {
                photoUse = false
                add_photo_image.visibility = View.INVISIBLE
            }

        }

        add_photo_image.setOnLongClickListener {

            add_photo_image.visibility = View.INVISIBLE
            photoUri = null
            photoUse = false

            return@setOnLongClickListener true
        }

    }


    override fun onClick(v: View?) {

        when (v) {

            date_title -> {
                updateDateTitle()
            }

            btn_img_input -> {
                getGalleryImage()
            }

            btn_save -> {
                updateCalendarData()
            }

            back_btn -> {
                onBackPressed()
            }

        }

    }

    // day 변경 시
    @SuppressLint("SetTextI18n")
    private fun updateDateTitle() {


        Log.d("로그", "updateDateTitle - call")

        if (dialogDatePick == null) dialogDatePick = DatePickDialog(this)

        dialogDatePick?.show()

        dialogDatePick?.defaultSetting(currentDate, "년월일") { tripleDate ->
            currentDate = tripleDate
            date_title.text = "${currentDate.first}년 ${currentDate.second + 1}월 ${currentDate.third}일"

        }


    }


    // 이미지 추가 버튼 클릭 시
    private fun getGalleryImage() {

        Log.d("로그", "getGalleryImage - call")

        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)


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

    // 일정 저장 버튼 클릭 시
    private fun updateCalendarData() {

        if (!edit_title.text.isNullOrBlank()) {

            hideAndShowUi(true)

            when (photoUse) {

                true -> {
                    uploadPhotoUse()
                }

                false -> {
                    uploadPhotoNotUse()
                }

            }
        } else {
            Snackbar.make(calendar_update_layout, "제목을 적어주세요", Snackbar.LENGTH_SHORT).show()
        }

    }


    private fun uploadPhotoUse() {

        var uri: String? = null
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_.png"
        val storageRef = storage?.reference?.child("images")?.child(imageFileName)

        when (photoUri) {

            null -> {  // 기존 사진을 변경을 안 했을 시

                dateModel?.let { it ->
                    it.content = edit_content.text.toString()
                    it.title = edit_title.text.toString()
                    it.date = date_title.text.toString()
                    it.uploadTime = System.currentTimeMillis()
                    it.mon = monData.toString()
                }

                fireStore?.collection("user_calendar")?.document(dateModel?.pathData!!)?.set(dateModel!!)?.addOnSuccessListener {
                    hideAndShowUi(false)
                    setResult(Activity.RESULT_OK)
                    finish()
                }?.addOnFailureListener {
                    uploadErrorMessage(it)
                }

            }
            else -> { // 사진 수정 했을 시

                storageRef?.putFile(photoUri!!)?.addOnSuccessListener {

                    storageRef.downloadUrl.addOnSuccessListener {



                        uri = it.toString()
                        dateModel?.let { it ->
                            it.content = edit_content.text.toString()
                            it.title = edit_title.text.toString()
                            it.date = date_title.text.toString()
                            it.photoUri = uri
                            it.uploadTime = System.currentTimeMillis()
                            it.mon = monData.toString()
                        }

                        fireStore?.collection("user_calendar")?.document(dateModel?.pathData!!)?.set(dateModel!!)?.addOnSuccessListener {
                            hideAndShowUi(false)
                            setResult(Activity.RESULT_OK)
                            finish()
                        }?.addOnFailureListener {
                            uploadErrorMessage(it)
                        }


                    }.addOnFailureListener {
                        uploadErrorMessage(it)

                    }

                }?.addOnFailureListener {
                    uploadErrorMessage(it)

                }

            }

        }

    }

    private fun uploadPhotoNotUse() {

        val date = Calendar.getInstance()
        val monData = date.get(Calendar.MONTH)
        Log.d("로그", "not photo use - call")

        dateModel?.let {


            it.content = edit_content.text.toString()
            it.title = edit_title.text.toString()
            it.date = date_title.text.toString()
            it.photoUri = "NullPhotoLink"
            it.uploadTime = System.currentTimeMillis()
            it.mon =  monData.toString()
        }

        Log.d("로그", "링크 ${dateModel?.pathData}")


        fireStore?.collection("user_calendar")!!.document(dateModel?.pathData!!).set(dateModel!!).addOnSuccessListener {
            hideAndShowUi(false)
            setResult(Activity.RESULT_OK)
            finish()
        }.addOnFailureListener {
            hideAndShowUi(false)
            uploadErrorMessage(it)
        }

    }


    private fun hideAndShowUi(hiddenCheck: Boolean) {

        when (hiddenCheck) {

            true -> {

                date_title.isEnabled = false
                edit_title.isEnabled = false
                edit_content.isEnabled = false
                add_photo_image.isEnabled = false
                btn_img_input.isEnabled = false
                btn_save.isEnabled = false
                back_btn.isEnabled = false

                loading_progress_update.visibility = View.VISIBLE

            }

            false -> {
                date_title.isEnabled = true
                edit_title.isEnabled = true
                edit_content.isEnabled = true
                add_photo_image.isEnabled = true
                btn_img_input.isEnabled = true
                btn_save.isEnabled = true
                back_btn.isEnabled = true

                loading_progress_update.visibility = View.INVISIBLE

            }

        }

    }

    private fun uploadErrorMessage(errorMessage: Exception) {

        hideAndShowUi(false)
        Log.d("로그", "error $errorMessage")
        Toast.makeText(this, "업로드 실패/ 서버 에러", Toast.LENGTH_SHORT).show()
        finish()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.page_left_in, R.anim.page_right_out)
    }


}