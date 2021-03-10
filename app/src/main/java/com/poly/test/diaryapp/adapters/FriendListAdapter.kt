package com.poly.test.diaryapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.poly.test.diaryapp.App
import com.poly.test.diaryapp.Calendar.IDeletePosition
import com.poly.test.diaryapp.Calendar.IDetailPosition
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.models.FriendAddModel
import com.poly.test.diaryapp.utils.Constants
import kotlinx.android.synthetic.main.layout_friend.view.*

class FriendListAdapter(private val FriendAddArray: ArrayList<FriendAddModel>,clickInterface : IDeletePosition) : RecyclerView.Adapter<FriendListAdapter.FriendAddViewHolder>() {

    private var iClickInterface : IDeletePosition? = null

    init {
        Log.d("로그", "call Adapter init")
        this.iClickInterface = clickInterface

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendAddViewHolder {

        val view =
            LayoutInflater.from(App.instance).inflate(R.layout.layout_friend, parent, false)

        return FriendAddViewHolder(view,this.iClickInterface!!)
    }

    override fun onBindViewHolder(holder: FriendAddViewHolder, position: Int) {

        holder.bind(FriendAddArray[position])

    }

    override fun getItemCount(): Int {
        return FriendAddArray.size
    }

    inner class FriendAddViewHolder(itemView: View , iClickInterface : IDeletePosition) : RecyclerView.ViewHolder(itemView) {


        private val dateEmail = itemView.friend_id
        private val dateName = itemView.friend_name

        private val datadelete = itemView.friend_delete
        private var iClickInterface : IDeletePosition

        init {
            this.iClickInterface = iClickInterface
        }

        fun bind(dateList: FriendAddModel) {

            dateEmail.text = dateList.friendEmail
            dateName.text = dateList.friendName
            Log.d(Constants.TAG, "myPath : ${dateList.myPath}")

            datadelete.setOnClickListener {
                Constants.LIST = 1
                this.iClickInterface.onItemClicked(adapterPosition,dateList)


            }


        }
    }
}




