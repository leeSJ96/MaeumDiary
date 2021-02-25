package com.poly.test.diaryapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class CalendarModel(
    var name: String? = null,
    var uid : String? = null,
    var content : String? = null,
    var title : String? = null,
    var date : String? = null,
    var uploadTime : Long? = null,
    var photoUri : String? =null,
    var pathData : String? = null,
    var mon : String? =null
) : Parcelable