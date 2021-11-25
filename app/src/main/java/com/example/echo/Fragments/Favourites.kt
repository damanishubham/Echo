package com.example.echo.Fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.echo.R

class Favourites : Fragment() {

    var mcontext : Activity?=null
    var favsonglistRV : RecyclerView?=null
    var favsonglistVisibleLayout: RelativeLayout?=null
    var favnoSongs : RelativeLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_favourites,container,false)
        favsonglistRV = view.findViewById(R.id.fav_songsRV)
        favsonglistVisibleLayout = view.findViewById(R.id.fav_songslist_visible_layouyt)
        favnoSongs = view.findViewById(R.id.fav_nosongs)
        return super.onCreateView(inflater, container, savedInstanceState)
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
    }
}