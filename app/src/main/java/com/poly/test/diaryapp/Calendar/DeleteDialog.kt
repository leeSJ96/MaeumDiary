package com.poly.test.diaryapp.Calendar

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.friendCustumDialog
import com.poly.test.diaryapp.utils.Constants.LIST
import kotlinx.android.synthetic.main.delete_dialog.*

class DeleteDialog(context: Context) : Dialog(context) {

    lateinit var completeResult: (deleteSet: Boolean) -> Unit


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_dialog)

    }

    fun deleteSetting(pathData: String, completeResult: (deleteSet: Boolean) -> Unit) {

        this.completeResult = completeResult


        yes_btn.setOnClickListener {

            Log.d("로그", "yesClick")
            val fireStore =
                FirebaseFirestore.getInstance().collection("user_calendar").document(pathData)
            val friendStore =
                FirebaseFirestore.getInstance().collection("user_friends").document(pathData)

            when (LIST) {
                //게시글 리스트 게시글 삭제
                0 -> fireStore.delete().addOnSuccessListener {
                    Toast.makeText(context, "삭제 완료", Toast.LENGTH_SHORT).show()
                    this.completeResult.invoke(true)
                }.addOnFailureListener {
                    Toast.makeText(context, "삭제 에러", Toast.LENGTH_SHORT).show()
                    this.completeResult.invoke(false)
                }


                //친구리스트 친구삭제
                1 -> friendStore.delete().addOnSuccessListener {
                    Toast.makeText(context, "삭제 완료", Toast.LENGTH_SHORT).show()
                    this.completeResult.invoke(true)
                }.addOnFailureListener {
                    Toast.makeText(context, "삭제 에러", Toast.LENGTH_SHORT).show()
                    this.completeResult.invoke(false)
                }
            }
            dismiss()
        }

        no_btn.setOnClickListener {
            Log.d("로그", "noClick")
            this.completeResult.invoke(false)
            dismiss()
        }


    }

    override fun dismiss() {
        super.dismiss()
    }
}