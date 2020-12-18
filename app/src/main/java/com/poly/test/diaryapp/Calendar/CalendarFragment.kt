package com.poly.test.diaryapp.Calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        view.calendar_view.addDecorator(saturday)
        view.calendar_view.addDecorator(sunDay)



        return view
    }


}