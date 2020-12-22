package com.poly.test.diaryapp.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.poly.test.diaryapp.CustomDialog
import com.poly.test.diaryapp.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)


        val customDialog = CustomDialog()

        view.btn_Test.setOnClickListener {
            customDialog.show(childFragmentManager, "")
        }

        return view
    }

}