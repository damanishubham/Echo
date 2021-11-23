package com.example.echo.DataModels

import android.os.Parcel
import android.os.Parcelable

class Songs(val songID: Long, val songTitle :String, val songArtist : String,
                val songData: String, val dateAdded: Long) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong()
    ) {
    }

    override fun describeContents(): Int {
        return  0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(songID)
        dest?.writeString(songTitle)
        dest?.writeString(songArtist)
        dest?.writeString(songData)
        dest?.writeLong(dateAdded)
    }

    companion object CREATOR : Parcelable.Creator<Songs> {
        override fun createFromParcel(parcel: Parcel): Songs {
            return Songs(parcel)
        }

        override fun newArray(size: Int): Array<Songs?> {
            return arrayOfNulls(size)
        }
    }
}