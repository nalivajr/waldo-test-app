package com.touchlane.waldotest.domain.model

import android.os.Parcel
import android.os.Parcelable

data class ThumbnailInfo(
    val size: ThumbnailSize,
    val width: Int,
    val height: Int,
    val url: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readSerializable() as ThumbnailSize,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(size)
        parcel.writeInt(width)
        parcel.writeInt(height)
        parcel.writeString(url)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ThumbnailInfo> = object: Parcelable.Creator<ThumbnailInfo> {
            override fun createFromParcel(parcel: Parcel): ThumbnailInfo {
                return ThumbnailInfo(parcel)
            }

            override fun newArray(size: Int): Array<ThumbnailInfo?> {
                return arrayOfNulls(size)
            }
        }
    }
}