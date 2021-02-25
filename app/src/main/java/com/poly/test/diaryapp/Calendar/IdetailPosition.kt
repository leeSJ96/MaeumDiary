package com.poly.test.diaryapp.Calendar


import com.poly.test.diaryapp.models.CalendarModel
import com.poly.test.diaryapp.models.FriendAddModel


interface IDetailPosition {

    //아이템 버튼 클릭
    fun onItemClicked(position: Int, dateList: CalendarModel)

}

interface IDeletePosition {

    //아이템 버튼 클릭
    fun onItemClicked(position: Int, dateList: FriendAddModel)

}