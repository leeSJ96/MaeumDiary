package com.poly.test.diaryapp.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poly.test.diaryapp.Calendar.CalendarFragment
import com.poly.test.diaryapp.CustomDialog
import com.poly.test.diaryapp.MainActivity
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.adapters.TestAdapter
import com.poly.test.diaryapp.utils.Constants
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {


    //private val ActivityMain = MainActivity()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val customDialog = CustomDialog()

        val bundle: Bundle = Bundle()
        val diaryFrag = CalendarFragment()
        val testList = ArrayList<String>()

        view.home_layout.setBackgroundResource(Constants.COLOR_BG)


        view.btn_Test.setOnClickListener {
            customDialog.show(childFragmentManager, "")
        }

        bundle.putString("name", "test")

        view.btn_bundle.setOnClickListener {
            val transaction: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
            diaryFrag.arguments = bundle
            transaction.replace(R.id.frag_layout, diaryFrag)
            transaction.commit()
        }

        for (i in 0..30) {

            testList.apply {
                add("$i")
            }

        }

        val rvTransaction: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        view.rv_home.adapter = TestAdapter(activity!!, testList, rvTransaction)
        view.rv_home.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}