package com.poly.test.diaryapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.poly.test.diaryapp.App
import com.poly.test.diaryapp.Home.IHomePosition
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.models.FriendDataModel
import kotlinx.android.synthetic.main.layout_user_list_item.view.*

class UserListAdapter(private val userArray: ArrayList<FriendDataModel>, clickInterface : IHomePosition) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {



    private var iClickInterface : IHomePosition? = null
    private var rowIndex = -1

    init {
        this.iClickInterface = clickInterface
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val view = LayoutInflater.from(App.instance).inflate(R.layout.layout_user_list_item, parent, false)

        return UserViewHolder(view, this.iClickInterface!!)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        holder.bind(userArray[position])

    }

    override fun getItemCount(): Int {

        return userArray.size
    }

    inner class UserViewHolder(itemView: View, iClickInterface : IHomePosition) : RecyclerView.ViewHolder(itemView) {


        private val userView = itemView.user_item_layout
        private val userNickName = itemView.user_name
        private val userEmail = itemView.user_email
        private var userPosition : IHomePosition = iClickInterface

        fun bind(userData : FriendDataModel) {

            userNickName.text = userData.friendName
            userEmail.text = userData.friendEmail

            userView.setOnClickListener {
                this.userPosition.onClickListener(adapterPosition , userData)
                rowIndex = adapterPosition
                notifyDataSetChanged()
                userView.isActivated = !userView.isActivated
            }
            userView.isActivated = rowIndex == adapterPosition

        }

    }


}