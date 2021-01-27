package com.poly.test.diaryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.models.CalendarModel
import kotlinx.android.synthetic.main.layout_date_list.view.*

class CalendarListAdapter(val context: Context, private val calendarArray: ArrayList<CalendarModel>) : RecyclerView.Adapter<CalendarListAdapter.CalendarViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.layout_date_list, parent, false)

        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {

        holder.bind(calendarArray[position])
    }

    override fun getItemCount(): Int {
        return calendarArray.size
    }

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val dateImg = itemView.date_img
        private val dateTitleTv = itemView.date_title_tv
        private val dateTv = itemView.date_tv

        fun bind(dateList: CalendarModel) {

            dateTitleTv.text = dateList.title
            dateTv.text = dateList.date

            Glide.with(context)
                    .load(dateList.photoUri)
                    .placeholder(R.drawable.ic_baseline_calendar_today_24)
                    .transform(RoundedCorners(20))
                    .into(dateImg)


        }
    }


}