package com.poly.test.diaryapp.Calendar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.models.CalendarModel
import com.poly.test.diaryapp.utils.Constants.COLOR_BG
import com.prolificinteractive.materialcalendarview.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import kotlinx.android.synthetic.main.fragment_setting.view.*
import kotlinx.coroutines.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CalendarFragment : Fragment(), OnDateSelectedListener {

    private var calendarView: View? = null

    private var name: String? = null
    private var email: String? = null
    private var uid: String? = null

    private val calendarArray = ArrayList<CalendarModel>()

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        calendarView = view

        val pref: SharedPreferences = activity!!.getSharedPreferences("ref", Context.MODE_PRIVATE)

        val saturday = SaturdayDecorator()
        val sunDay = SundayDecorator()
        val today = OneDayDecorator()


        val todayFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        val date = Date()

        view.calendar_layout.setBackgroundResource(COLOR_BG)

        view.today_date.text = todayFormat.format(date.time).toString()
        view.calendar_view.apply {
            addDecorator(today)
            addDecorator(saturday)
            addDecorator(sunDay)
            setOnDateChangedListener(this@CalendarFragment)
        }

        email = pref.getString("userEmail", "")
        name = pref.getString("userName", "")
        uid = pref.getString("userToken", "")

        // 데이터 가져오기
        getData()

        return view
    }


    private fun getData() {


        FirebaseFirestore.getInstance()
                .collection("user_calendar")
                .whereEqualTo("uid", uid)
                .addSnapshotListener { querySnapshot, firebaseFireStoreException ->

                    calendarArray.clear()
                    if (querySnapshot == null) return@addSnapshotListener
                    for (snapshot in querySnapshot.documents) {
                        calendarArray.add(snapshot.toObject(CalendarModel::class.java)!!)
                        Log.d("로그", "array ${calendarArray.size}")
                        Log.d("로그", "array ${calendarArray[0].uid}")
                        Log.d("로그", "array ${calendarArray[0].name}")
                    }

                    Log.d("로그", "error $firebaseFireStoreException")

                }
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDateSelected(
            widget: MaterialCalendarView,
            date: CalendarDay,
            selected: Boolean
    ) {


        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val todayFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        var dateString = ""
        var todayString = ""
        if (selected) {

            try {
                Log.d("로그","day 선택 $date")

                dateString = dateFormat.format(date.date)
                todayString = todayFormat.format(date.date)
                setDetailDateView(dateString, todayString, date.date)
            } catch (e: Exception) {
                Log.d("로그", "dateFormat error $e")
            }
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun setDetailDateView(selectDate: String, todayString: String, date: Date) {

        val dayFormat = SimpleDateFormat("dd")
        val dayText = dayFormat.format(date)

        calendarView!!.date_layout.visibility = View.VISIBLE
        calendarView!!.check_date.text = dayText + "일"


        calendarView!!.check_title.setOnClickListener {
            val intent = Intent(activity, CalendarWriteActivity::class.java)
            intent.putExtra("selectDate", selectDate)
            intent.putExtra("todayString", todayString)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)
        }


    }


    private fun setData() {



    }


}


//        val testRvText: String? = arguments!!.getString("test")
//        val testFragBundle: String? = arguments!!.getString("name")