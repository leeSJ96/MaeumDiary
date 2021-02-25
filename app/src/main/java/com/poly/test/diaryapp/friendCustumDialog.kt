package com.poly.test.diaryapp

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.poly.test.diaryapp.Calendar.DeleteDialog
import com.poly.test.diaryapp.Calendar.IDeletePosition
import com.poly.test.diaryapp.adapters.FriendListAdapter
import com.poly.test.diaryapp.models.CalendarModel
import com.poly.test.diaryapp.models.FriendAddModel
import com.poly.test.diaryapp.utils.Constants
import com.poly.test.diaryapp.utils.SharedPreferenceFactory
import kotlinx.android.synthetic.main.fragment_calendar_list.view.*
import kotlinx.android.synthetic.main.friend_dialog.view.*


class friendCustumDialog(v: View) : DialogFragment() , IDeletePosition {


    private var deleteDialog: DeleteDialog? = null
    private val v = v

    @RequiresApi(Build.VERSION_CODES.N)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {





        val maindlgBuilder: androidx.appcompat.app.AlertDialog.Builder =
            androidx.appcompat.app.AlertDialog.Builder(    // 메인 다이얼로그
                requireContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth
            )

        maindlgBuilder.setView(v)
        val dlg = maindlgBuilder.create()
        dlg.window?.setGravity(Gravity.CENTER)
        getData(v)
        return dlg

    }

    private fun getData(view: View) {

        val FriendAddArray = ArrayList<FriendAddModel>()
        val store = FirebaseFirestore.getInstance().collection("user_friends")
        val myuid = FirebaseAuth.getInstance().currentUser!!.email

        store.whereEqualTo("myEmail", myuid).addSnapshotListener { querySnapShot, error ->

            if (querySnapShot == null) return@addSnapshotListener
            FriendAddArray.clear()
            for (snapshot in querySnapShot.documents) {
                FriendAddArray.add(snapshot.toObject(FriendAddModel::class.java)!!)
            }
            Log.d("로그", "calendar size ${FriendAddArray.size}")
            if (FriendAddArray.size > 0) {
                view.friend_RecycleView.adapter = FriendListAdapter(FriendAddArray,this)
                view.friend_RecycleView.layoutManager = LinearLayoutManager(App.instance)
            } else {

            }
        }

        view.friend_RecycleView.adapter?.notifyDataSetChanged()

    }

    override fun onItemClicked(position: Int, dateList: FriendAddModel) {

        var path = dateList.myPath
        Log.d(Constants.TAG, "path: ${path} ")

        if (deleteDialog == null) {
            deleteDialog = activity?.let { DeleteDialog(it) }
        }

        deleteDialog?.show()
        if (path != null) {
            deleteDialog?.deleteSetting(path) { result ->

                if (result) {
                    deleteDialog!!.dismiss()
                }

            }
        }
    }

}