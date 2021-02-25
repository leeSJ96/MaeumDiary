package com.poly.test.diaryapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.poly.test.diaryapp.App
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.models.CalendarReceiveModel
import com.poly.test.diaryapp.models.FriendDataModel
import com.poly.test.diaryapp.utils.Constants
import com.poly.test.diaryapp.utils.Constants.COLOR_ICON_PROFILE_IMAGE
import kotlinx.android.synthetic.main.layout_home_share_item.view.*
import kotlinx.android.synthetic.main.layout_rv_home.view.*

class FriendShareDiaryAdapter(private val receiveArray: ArrayList<CalendarReceiveModel>) :RecyclerView.Adapter<FriendShareDiaryAdapter.FriendShareDiaryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendShareDiaryHolder {

        val view = LayoutInflater.from(App.instance).inflate(R.layout.layout_home_share_item, parent, false)

        return FriendShareDiaryHolder(view)
    }

    override fun onBindViewHolder(holder: FriendShareDiaryHolder, position: Int) {

        holder.bind(receiveArray[position])
    }

    override fun getItemCount(): Int {
        return receiveArray.size
    }

    inner class  FriendShareDiaryHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


        private val friendName = itemView.friend_name_tv
        private val friendDate = itemView.share_date_tv
        private val friendImage = itemView.friend_diary_image
        private val friendTitle = itemView.content_title_tv
        private val friendContent = itemView.content_tv
        private val friendBack = itemView.friend_item_back

        fun bind(dateList: CalendarReceiveModel) {

            Glide.with(App.instance)
                .load(dateList.photoUrl)
                .placeholder(COLOR_ICON_PROFILE_IMAGE)
                .error(COLOR_ICON_PROFILE_IMAGE)
                .transform(CenterCrop() , RoundedCorners(20))
                .into(friendImage)

            friendName.text = dateList.sendUserName
            friendTitle.text = dateList.title
            friendDate.text = dateList.receiveDate
            friendContent.text = dateList.content


            friendName.setTextColor(App.instance.resources.getColor(Constants.COLOR_TEXT_W))
            friendDate.setTextColor(App.instance.resources.getColor(Constants.COLOR_TEXT_W))
            friendTitle.setTextColor(App.instance.resources.getColor(Constants.COLOR_TEXT_W))
            friendContent.setTextColor(App.instance.resources.getColor(Constants.COLOR_TEXT_W))
            friendBack.setBackgroundResource(Constants.COLOR_HOME_ITEM)

        }

    }
}