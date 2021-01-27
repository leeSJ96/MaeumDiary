package com.poly.test.diaryapp.Calendar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.poly.test.diaryapp.R
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


        val view = inflater.inflate(R.layout.fragment_calendar_list, container, false)
        val calendarDate : Date = Date()

        val todayFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        val todayText = todayFormat.format(calendarDate).toString()
        Log.d("로그","todayText $todayText")


        view.add_schedule.setOnClickListener {

            val intent = Intent(activity, CalendarWriteActivity::class.java)
            intent.putExtra("today",todayText)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out)

        }

        return view
    }


    private fun getData() {



    }



}