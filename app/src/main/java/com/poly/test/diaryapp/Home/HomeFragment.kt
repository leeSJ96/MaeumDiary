package com.poly.test.diaryapp.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.utils.Constants
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)


        view.home_layout.setBackgroundResource(Constants.COLOR_BG)


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}