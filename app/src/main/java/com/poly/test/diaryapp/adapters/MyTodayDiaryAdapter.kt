package com.poly.test.diaryapp.adapters

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.firebase.firestore.FirebaseFirestore
import com.poly.test.diaryapp.App
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.models.CalendarModel
import com.poly.test.diaryapp.utils.Constants
import com.poly.test.diaryapp.utils.Constants.COLOR_ICON_PROFILE_IMAGE
import kotlinx.android.synthetic.main.layout_home_today_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MyTodayDiaryAdapter(private val calendarArray: ArrayList<CalendarModel>) : RecyclerView.Adapter<MyTodayDiaryAdapter.MyTodayDiaryHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTodayDiaryHolder {

        val view = LayoutInflater.from(App.instance).inflate(R.layout.layout_home_today_item, parent, false)

        return MyTodayDiaryHolder(view)
    }

    override fun onBindViewHolder(holder: MyTodayDiaryHolder, position: Int) {

        holder.bind(calendarArray[position])
    }

    override fun getItemCount(): Int {
        return calendarArray.size
    }
    inner class MyTodayDiaryHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val dateImg = itemView.diary_image
        private val dateTitleTv = itemView.content_title_tv
        private val datecontent = itemView.content_content_tv
        private val homeBack = itemView.home_item_back

        fun bind(dateList: CalendarModel) {



            dateTitleTv.text = dateList.title
            datecontent.text = dateList.content

            Glide.with(App.instance)
                .load(dateList.photoUri)
                .placeholder(COLOR_ICON_PROFILE_IMAGE)
                .error(COLOR_ICON_PROFILE_IMAGE)
                .transform(CenterCrop() ,RoundedCorners(20))
                .into(dateImg)

            dateTitleTv.setTextColor(App.instance.resources.getColor(Constants.COLOR_TEXT_W))
            datecontent.setTextColor(App.instance.resources.getColor(Constants.COLOR_TEXT_W))
            homeBack.setBackgroundResource(Constants.COLOR_HOME_ITEM)


        }



    }
}