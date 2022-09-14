package com.egas.demo.bean

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(var uId:Int,var name:String,var des:String):Parcelable {
//    private fun readFromParcel(source: Parcel) {
//        this.uId = source.readInt()
//        this.name = source.readString() ?: ""
//        this.des = source.readString() ?: ""
//    }
}