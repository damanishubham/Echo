package com.example.echo.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.echo.DataModels.Songs
import com.example.echo.Database.EchoDatabase.static.db_name
import java.lang.Exception

class EchoDatabase : SQLiteOpenHelper {
    object static
    {
        val db_name ="fav_song_db"
    }

    val tname = "fav_song_list"
    val _id ="song_ID"
    val title ="song_title"
    val artist = "song_artist"
    val path = "song_path"

    constructor(
        context: Context?,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : super(context, name, factory, version)

    constructor(context: Context?) : super(context,db_name,null,1)

    override fun onCreate(db: SQLiteDatabase?) {
        var query = "CREATE TABLE $tname($_id LONG,$title STRING,$artist STRING,$path STRING );"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun storeSongs (i : Songs)
    {
        val db = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(_id,i.songID)
        contentValues.put(title,i.songTitle)
        contentValues.put(artist,i.songArtist)
        contentValues.put(path,i.songData)
        db.insert(tname,null,contentValues)
        db.close()
    }

    fun checkSong(songID: Long): Boolean {
        val db = this.readableDatabase
        var query = "SELECT * FROM $tname WHERE $_id = $songID;"
        var cursor = db.rawQuery(query,null)
        try {
            if(cursor.moveToFirst())
            {
                db.close()
                return true
            }
        }
        catch (e: Exception)
        {
            e.printStackTrace()
        }
        db.close()
        return false
    }

    fun deleteSongs(songs: Long) {
        val db = this.writableDatabase
        db.delete(tname, "$_id=$songs", null)
        db.close()
    }

}