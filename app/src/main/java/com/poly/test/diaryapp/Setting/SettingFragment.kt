package com.poly.test.diaryapp.Setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.utils.Constants.COLOR_BG
import kotlinx.android.synthetic.main.fragment_setting.view.*

class SettingFragment : Fragment() {


    var setView : View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        setView = view

        view.background_Color.setOnClickListener {
            val intent = Intent(activity, SetBgColorActivity::class.java)
            startActivity(intent)
        }

        view.exit_button.setOnClickListener {
            val intent = Intent(activity, SetBgColorActivity::class.java)
            startActivity(intent)
        }


        return view
    }

    override fun onResume() {
        super.onResume()

        setView!!.setting_layout.setBackgroundResource(COLOR_BG)

    }
}