package com.example.echo.Fragments

import android.app.Activity
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.echo.Adapter.SongListAdapter
import com.example.echo.DataModels.Songs
import com.example.echo.MainActivity
import com.example.echo.R

class HomeFragment : Fragment() {

    var mcontext :Activity?=null
    var songlistRV : RecyclerView?=null
    var songlistVisibleLayout: RelativeLayout?=null
    var noSongs : RelativeLayout?=null
    var songPlayingWidget: RelativeLayout?=null
    var playpausebutton : ImageButton?=null
    var nowplayingsongtitle : TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        songlistVisibleLayout = view.findViewById<RelativeLayout>(R.id.songslist_visible_layouyt)
        noSongs = view.findViewById<RelativeLayout>(R.id.nosongs)
        songPlayingWidget = view.findViewById<RelativeLayout>(R.id.songPlayingwidget)
        playpausebutton = view.findViewById<ImageButton>(R.id.playPauseButton)
        nowplayingsongtitle = view.findViewById<TextView>(R.id.nowPlayingsongtitle)
        songlistRV = view.findViewById<RecyclerView>(R.id.songsRV)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mcontext =context as Activity
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mcontext =activity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var songs= getsongs()
        intitialiseRV(songs,mcontext as Context)
        if (songs.isEmpty())
        {
            songlistVisibleLayout?.visibility = View.INVISIBLE
            noSongs?.visibility =View.VISIBLE
        }
        else
        {
            songlistVisibleLayout?.visibility = View.VISIBLE
            noSongs?.visibility =View.INVISIBLE
        }

        var isplaying = arguments?.getBoolean("isplaying")
        if(isplaying == true)
        {
            songPlayingWidget?.visibility =View.VISIBLE
        }
    }

    fun getsongs() : ArrayList<Songs>{
        var songlist = ArrayList<Songs>()
        var contentResolver = mcontext?.contentResolver
        var songURI= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        var cursor = contentResolver?.query(songURI,null,null,null,null)
        if(cursor!=null && cursor.moveToFirst())
        {
            val songID = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val songTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val songArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val songData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            val dateAdded = cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED)
            do
            {
                var id= cursor.getLong(songID)
                var title =cursor.getString(songTitle)
                var artist = cursor.getString(songArtist)
                var data= cursor.getString(songData)
                var date = cursor.getLong(dateAdded)
                songlist.add(Songs(id,title,artist,data,date))
            }while (cursor.moveToNext())
        }

        return songlist
    }

    fun intitialiseRV(songs: ArrayList<Songs>,context: Context)
    {
        songlistRV?.layoutManager =LinearLayoutManager(context)
        var adapter = SongListAdapter(songs,context)
        songlistRV?.adapter =adapter
    }
}