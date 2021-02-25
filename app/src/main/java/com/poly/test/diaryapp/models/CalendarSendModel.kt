package com.poly.test.diaryapp.models


data class CalendarSendModel(
    var title: String? = null,
    var content: String? = null,
    var photoUrl: String? = null,
    var friendEmail: String? = null,
    var friendName: String? = null,
    var myEmail: String? = null,
    var myName: String? = null,
    var myPath: String? = null,
    var sendDate: String? = null
)