package com.poly.test.diaryapp.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.utils.Constants.COLOR_BG
import kotlinx.android.synthetic.main.activity_set_bg_color.*

class SetBgColorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_bg_color)
        val settingFragment = SettingFragment()


        colorBtn.setOnClickListener {
            COLOR_BG = R.color.gray_sky_blue
            finish()
        }

    }
}