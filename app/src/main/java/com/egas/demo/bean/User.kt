package com.egas.demo.bean

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(var name: String?="") : Parcelable {
    fun readFromParcel(source: Parcel) {
        this.name = source.readString() ?: ""
    }
}
