package com.example.echo.DataModels

import android.os.Parcel
import android.os.Parcelable

class Songs(val songID: Long, val songTitle :String, val songArtist : String,
                val songData: String, val dateAdded: Long) : Parcelable{
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }
}