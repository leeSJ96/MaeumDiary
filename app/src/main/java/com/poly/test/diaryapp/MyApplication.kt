package com.poly.test.diaryapp

import android.app.Application
import com.poly.test.diaryapp.sharedPreferences.PreferenceUtil

class MyApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}
