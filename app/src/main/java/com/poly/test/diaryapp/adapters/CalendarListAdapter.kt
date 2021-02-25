package com.poly.test.diaryapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.poly.test.diaryapp.App
import com.poly.test.diaryapp.Calendar.IDetailPosition
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.models.CalendarModel
import com.poly.test.diaryapp.utils.Constants
import kotlinx.android.synthetic.main.layout_date_list.view.*

class CalendarListAdapter(private val calendarArray: ArrayList<CalendarModel>, clickInterface : IDetailPosition)
    : RecyclerView.Adapter<CalendarListAdapter.CalendarViewHolder>() {




    private var iClickInterface : IDetailPosition? = null


    init {
        Log.d("로그", "call Adapter init")
        this.iClickInterface = clickInterface

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {

        val view = LayoutInflater.from(App.instance).inflate(R.layout.layout_date_list, parent, false)

        return CalendarViewHolder(view, this.iClickInterface!!)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {

        holder.bind(calendarArray[position])
    }

    override fun getItemCount(): Int {
        return calendarArray.size
    }

    inner class CalendarViewHolder(itemView: View, private var iClickInterface: IDetailPosition) : RecyclerView.ViewHolder(itemView) {

        private val dateView = itemView.calendar_item_layout
        private val dateImg = itemView.date_img
        private val dateTitleTv = itemView.date_title_tv
        private val dateTv = itemView.date_tv
        private val calendarBack = itemView.calendar_back

        fun bind(dateList: CalendarModel) {

            dateTitleTv.text = dateList.title
            dateTv.text = dateList.date

            Glide.with(App.instance)
                .load(dateList.photoUri)
                .placeholder(Constants.COLOR_ICON_PROFILE_IMAGE)
                .error(Constants.COLOR_ICON_PROFILE_IMAGE)
                .transform(CenterCrop() ,RoundedCorners(20))
                .into(dateImg)

            dateView.setOnClickListener {

                this.iClickInterface.onItemClicked(adapterPosition,dateList)

            }
            dateTv.setTextColor(App.instance.resources.getColor(Constants.COLOR_TEXT_W))
            dateTitleTv.setTextColor(App.instance.resources.getColor(Constants.COLOR_TEXT_W))
            calendarBack.setBackgroundResource(Constants.COLOR_HOME_ITEM)

        }


    }


}