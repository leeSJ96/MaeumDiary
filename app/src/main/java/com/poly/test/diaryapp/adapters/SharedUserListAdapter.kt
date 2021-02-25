package com.poly.test.diaryapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.poly.test.diaryapp.App
import com.poly.test.diaryapp.Calendar.ISharedPosition
import com.poly.test.diaryapp.R
import com.poly.test.diaryapp.models.FriendAddModel
import kotlinx.android.synthetic.main.layout_user_list_item.view.*

class SharedUserListAdapter(
    private val userArray: ArrayList<FriendAddModel>,
    clickInterface: ISharedPosition
) : RecyclerView.Adapter<SharedUserListAdapter.SharedViewHolder>() {


    private var iClickInterface: ISharedPosition? = null
    private var rowIndex = -1

    init {
        iClickInterface = clickInterface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SharedViewHolder {

        val view =
            LayoutInflater.from(App.instance).inflate(R.layout.layout_user_list_item, parent, false)

        return SharedViewHolder(view, iClickInterface!!)

    }

    override fun onBindViewHolder(holder: SharedViewHolder, position: Int) {

        holder.bind(userArray[position])
    }

    override fun getItemCount(): Int {

        return userArray.size
    }

    inner class SharedViewHolder(itemView: View, iClickInterface: ISharedPosition) :
        RecyclerView.ViewHolder(itemView) {

        private val userView = itemView.user_item_layout
        private val userNickName = itemView.user_name
        private val userEmail = itemView.user_email
        private var userClickListener: ISharedPosition = iClickInterface


        fun bind(friendData: FriendAddModel) {

            userNickName.text = friendData.friendName
            userEmail.text = friendData.friendEmail

            userView.setOnClickListener {
                this.userClickListener.onClickListener(friendData)
                rowIndex = adapterPosition
                notifyDataSetChanged()
                userView.isActivated = !userView.isActivated
            }
            userView.isActivated = rowIndex == adapterPosition

        }
    }

}