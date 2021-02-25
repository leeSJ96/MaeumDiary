package com.poly.test.diaryapp.models

data class CalendarReceiveModel(
    var title: String? = null,
    var content: String? = null,
    var photoUrl: String? = null,
    var sendUserEmail: String? = null,
    var sendUserName: String? = null,
    var receiveUserEmail: String? = null,
    var receiveUserName: String? = null,
    var myPath: String? = null,
    var receiveDate: String? = null
)