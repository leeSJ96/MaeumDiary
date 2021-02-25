package com.poly.test.diaryapp.Calendar

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.adapters.SharedUserListAdapter
import com.poly.test.diaryapp.models.CalendarModel
import com.poly.test.diaryapp.models.CalendarReceiveModel
import com.poly.test.diaryapp.models.CalendarSendModel
import com.poly.test.diaryapp.models.FriendAddModel
import com.poly.test.diaryapp.utils.SharedPreferenceFactory
import kotlinx.android.synthetic.main.dialog_shared.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SharedDialog(context: Context, private val calendarModel: CalendarModel) :
    Dialog(context, R.style.dialog_fullscreen), ISharedPosition {


    private var itemCheck = false
    private var friendAddModel = FriendAddModel()
    private var calendarSendModel = CalendarSendModel()
    private var calendarReceiveModel = CalendarReceiveModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_shared)


        val friendArray = ArrayList<FriendAddModel>()
        val friendStore = FirebaseFirestore.getInstance().collection("user_friends")
        val myName = SharedPreferenceFactory.getStrValue("userName")

        friendStore.whereEqualTo("myName", myName).get().addOnSuccessListener { querySnapshot ->


            for (i in querySnapshot) {
                friendArray.add(i.toObject(FriendAddModel::class.java))
            }

            friends_rv.adapter = SharedUserListAdapter(friendArray, this)
            friends_rv.layoutManager = LinearLayoutManager(context)

        }.addOnFailureListener { error ->

            Log.d("로그", "에러 $error")

        }



        shared_btn.setOnClickListener {

            when (itemCheck) {

                true -> {
                    shareData()
                }
                false -> {
                    Toast.makeText(context, "공유할 친구를 선택해주세요.", Toast.LENGTH_SHORT).show()
                }

            }

        }

    }


    @SuppressLint("SimpleDateFormat")
    private fun shareData() {


        val sendStore = FirebaseFirestore.getInstance().collection("user_send")
        val receiveStore = FirebaseFirestore.getInstance().collection("user_receive")

        val uid = SharedPreferenceFactory.getStrValue("userToken", "")
        val timeStamp = System.currentTimeMillis().toString()
        val myPath = "${uid}_${timeStamp}"
        val sendDate = SimpleDateFormat("yyyy년 MM월 dd일").format(Date())

        calendarSendModel = CalendarSendModel(
            title = calendarModel.title,
            content = calendarModel.content,
            photoUrl = calendarModel.photoUri,
            friendEmail = friendAddModel.friendEmail,
            friendName = friendAddModel.friendName,
            myEmail =  friendAddModel.friendEmail,
            myName =  friendAddModel.friendName,
            myPath = myPath,
            sendDate = sendDate
        )

        calendarReceiveModel = CalendarReceiveModel(
            title = calendarModel.title,
            content = calendarModel.content,
            photoUrl = calendarModel.photoUri,
            sendUserEmail = friendAddModel.myEmail,
            sendUserName = friendAddModel.myName,
            receiveUserEmail = friendAddModel.friendEmail,
            receiveUserName = friendAddModel.friendName,
            myPath = myPath,
            receiveDate = sendDate
        )


        sendStore.document(myPath).set(calendarSendModel).addOnSuccessListener {


            receiveStore.document(myPath).set(calendarReceiveModel).addOnSuccessListener {

                Toast.makeText(context, "일정이 공유되었습니다.", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener { error ->

                errorMessage(error)

            }

        }.addOnFailureListener { error ->

            errorMessage(error)

        }


        dismiss()
    }

    override fun onClickListener(userModel: FriendAddModel) {

        friendAddModel = userModel
        itemCheck = true

    }

    private fun errorMessage(error : Exception) {

        Log.d("로그","error : $error")

        Toast.makeText(context, "일정 공유에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()


    }



}