package com.poly.test.diaryapp.Calendar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.fragment_calendar_list.view.*
import java.text.SimpleDateFormat
import java.time.Instant.now
import java.util.*
import kotlin.collections.ArrayList


@Suppress("DEPRECATION")
class CalendarListFragment : Fragment() {

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        Log.d("로그","onCreateView - call")

        val pref : SharedPreferences = App.instance.getSharedPreferences("ref", Context.MODE_PRIVATE)

        val uid = pref.getString("userToken","")

        val view = inflater.inflate(R.layout.fragment_calendar_list, container, false)
        val calendarDate : Date = Date()

        val todayFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        val todayText = todayFormat.format(calendarDate).toString()
        Log.d("로그","todayText $todayText")


        getData(uid, view)

        view.add_schedule.setOnClickListener {

            val intent = Intent(activity, CalendarWriteActivity::class.java)
            intent.putExtra("today",todayText)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)

        }

        return view
    }


    private fun getData(uid: String?, view: View) {

        val calendarArray = ArrayList<CalendarModel>()
        val store = FirebaseFirestore.getInstance().collection("user_calendar")

        store.whereEqualTo("uid",uid).addSnapshotListener { querySnapShot, error ->

            if(querySnapShot == null) return@addSnapshotListener
            calendarArray.clear()
            for(snapshot in querySnapShot.documents) {
                calendarArray.add(snapshot.toObject(CalendarModel::class.java)!!)
            }
            Log.d("로그","calendar size ${calendarArray.size}")
            if(calendarArray.size > 0) {
                view.calendar_recyclerview.adapter = CalendarListAdapter(calendarArray)
                view.calendar_recyclerview.layoutManager = LinearLayoutManager(App.instance)
            } else {

            }
        }

        view.calendar_recyclerview.adapter?.notifyDataSetChanged()

    }



}