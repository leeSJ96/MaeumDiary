package com.poly.test.diaryapp.Calendar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.poly.test.diaryapp.App
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.adapters.CalendarListAdapter
import com.poly.test.diaryapp.models.CalendarModel
import com.poly.test.diaryapp.models.FriendAddModel
import com.poly.test.diaryapp.utils.Constants
import com.poly.test.diaryapp.utils.SharedPreferenceFactory
import kotlinx.android.synthetic.main.fragment_calendar_list.*
import kotlinx.android.synthetic.main.fragment_calendar_list.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


// 할 작업
// 1. 리스트 순차대로 나오게 하기
// 2. 리스트 클릭 시 상세페이지 이동

@Suppress("DEPRECATION")
class CalendarListFragment : Fragment(), IDetailPosition {


    lateinit var listView : View

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        Log.d("리스트 로그","onCreateView - call")


        val uid = SharedPreferenceFactory.getStrValue("userToken", "")

        listView = inflater.inflate(R.layout.fragment_calendar_list, container, false)
        val calendarDate = Date()

        val todayFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        val todayText = todayFormat.format(calendarDate).toString()
        Log.d("로그", "todayText $todayText")
        listView.calendar_layout?.setBackgroundResource(Constants.COLOR_BG)

        //getData(uid, listView)

        listView.add_schedule.setOnClickListener {

            val intent = Intent(activity, CalendarWriteActivity::class.java)
            intent.putExtra("today", todayText)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)

        }

        return listView
    }

    override fun onResume() {
        super.onResume()
        val uid = SharedPreferenceFactory.getStrValue("userToken", "")
        getData(uid,listView)

    }



    override fun onItemClicked(position: Int, dateList: CalendarModel) {

        //Log.d("로그","포지션 $position")

        val intent = Intent(activity, CalendarDetailActivity::class.java)
        intent.putExtra("dateModel", dateList)
        startActivity(intent)
        activity?.overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)

    }

    private fun getData(uid: String?, view: View) {

        Log.d("리스트 로그","getData - call")

        val calendarArray = ArrayList<CalendarModel>()
        val store = FirebaseFirestore.getInstance().collection("user_calendar")

        store.whereEqualTo("uid", uid).addSnapshotListener { querySnapShot, error ->

            if (querySnapShot == null) return@addSnapshotListener
            calendarArray.clear()
            for (snapshot in querySnapShot.documents) {

                calendarArray.add(snapshot.toObject(CalendarModel::class.java)!!)

            }
            Log.d("로그", "calendar size ${calendarArray.size}")

            if (calendarArray.size > 0) {

                view.no_content_layout.visibility = View.INVISIBLE

                calendarArray.sortBy { it.uploadTime }

                view.calendar_recyclerview.adapter = CalendarListAdapter(calendarArray, this)

                val linearLayoutManager = LinearLayoutManager(App.instance, LinearLayoutManager.VERTICAL, true)
                linearLayoutManager.stackFromEnd = true
                view.calendar_recyclerview.layoutManager = linearLayoutManager
            } else {
                view.no_content_layout.visibility = View.VISIBLE
            }
        }

        view.calendar_recyclerview.adapter?.notifyDataSetChanged()

    }



}