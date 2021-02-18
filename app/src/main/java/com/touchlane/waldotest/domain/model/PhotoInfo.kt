package com.touchlane.waldotest.domain.model

import android.os.Parcel
import android.os.Parcelable

data class PhotoInfo(
    val id: String,
    val urls: List<ThumbnailInfo>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readParcelableList(mutableListOf(), ThumbnailInfo::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelableList(urls, 0)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PhotoInfo> = object : Parcelable.Creator<PhotoInfo> {
            override fun createFromParcel(parcel: Parcel): PhotoInfo {
                return PhotoInfo(parcel)
            }

            override fun newArray(size: Int): Array<PhotoInfo?> {
                return arrayOfNulls(size)
            }
        }
    }
}