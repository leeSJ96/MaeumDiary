package com.poly.test.diaryapp.Home

import com.poly.test.diaryapp.models.FriendAddModel
import com.poly.test.diaryapp.models.FriendDataModel

interface IHomePosition {

    fun onClickListener(position: Int, friendModel : FriendDataModel)
}