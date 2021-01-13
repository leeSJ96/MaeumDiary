package com.poly.test.diaryapp.adapters

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.poly.test.diaryapp.Calendar.CalendarFragment
import com.poly.test.diaryapp.Home.HomeFragment
import com.poly.test.diaryapp.R
import kotlinx.android.synthetic.main.layout_rv_test.view.*

class TestAdapter(val context: Activity, private val testArray: ArrayList<String>, val transaction: FragmentTransaction)
    : RecyclerView.Adapter<TestAdapter.TestHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.layout_rv_test, parent, false)

        return TestHolder(view)
    }

    override fun onBindViewHolder(holder: TestHolder, position: Int) {

        holder.testView(testArray[position])

    }

    override fun getItemCount(): Int {

        return testArray.size
    }

    inner class TestHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val calendarFrag = CalendarFragment()

        fun testView(tvTest: String) {

            view.tv_rv_test.text = tvTest
            val bundle = Bundle()
            bundle.putString("test", tvTest)

            view.tv_rv_test.setOnClickListener {
                Log.d("로그","보내는 값 $tvTest")

                calendarFrag.arguments = bundle
                transaction.replace(R.id.frag_layout, calendarFrag)
                transaction.commit()
            }

        }
    }


}