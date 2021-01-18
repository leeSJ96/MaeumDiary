//package com.poly.test.diaryapp.utils
//
//import android.content.Context
//import android.content.Context.MODE_PRIVATE
//import android.content.SharedPreferences
//
//object SharedPreferenceFactory {
//
//    private fun getSharedPreferences(context: Context): SharedPreferences {
//        return context.getSharedPreferences("prefs_name", MODE_PRIVATE)
//    }
//
//    fun getStrValue(context: Context?, KEY: String, defaultValue: String? = null): String? {
//
//        return getSharedPreferences(context!!).getString(KEY, defaultValue)
//    }
//
//    fun getIntValue(context: Context?, KEY: String, defaultValue: Int? = 0): Int? {
//
//        return defaultValue?.let { getSharedPreferences(context!!).getInt(KEY, it) }
//    }
//
//    fun putStrValue(context: Context?, KEY: String, valueString: String?) {
//        val editor = getSharedPreferences(context!!).edit()
//        editor.putString(KEY, valueString)
//        editor.apply()
//    }
//
//    fun putIntValue(context: Context?, KEY: String, valueInt: Int?) {
//        val editor = getSharedPreferences(context!!).edit()
//        valueInt?.let { editor.putInt(KEY, it) }
//        editor.apply()
//    }
//
//    fun clearAllValue(context: Context?) {
//        val editor = getSharedPreferences(context!!).edit()
//        editor.clear()
//        editor.apply()
//        editor.commit()
//    }
//
//}
