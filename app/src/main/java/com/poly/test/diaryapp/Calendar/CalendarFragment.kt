package com.poly.test.diaryapp.Calendar

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.poly.test.diaryapp.R
import kotlinx.android.synthetic.main.fragment_calendar.view.*


class CalendarFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        val saturday = SaturdayDecorator()
        val sunDay = SundayDecorator()
        val today = OneDayDecorator()
        view.calendar_view.addDecorator(today)
        view.calendar_view.addDecorator(saturday)
        view.calendar_view.addDecorator(sunDay)
        val pref: SharedPreferences = activity!!.getSharedPreferences("ref", Context.MODE_PRIVATE)


        val email = pref.getString("userEmail", "")
        val name = pref.getString("userName", "")

        Log.d("로그", "frag email $email")
        Log.d("로그", "frag name $name")

        view.today_btn.setOnClickListener {

        }

        return view
    }


}