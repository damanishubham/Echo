package com.example.echo.Adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.echo.Adapter.SongListAdapter.static.songPlayingFragment
import com.example.echo.DataModels.NavMenuData
import com.example.echo.DataModels.Songs
import com.example.echo.Fragments.SongPlaying
import com.example.echo.MainActivity
import com.example.echo.R

class SongListAdapter(val song : ArrayList<Songs>, val context : Context) : RecyclerView.Adapter<SongListAdapter.SongVH>() {

    object static{
        val songPlayingFragment = SongPlaying()
    }

    class SongVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val songTitleTextView = itemView.findViewById(R.id.songName) as TextView
        val artistTextView = itemView.findViewById(R.id.artistName) as TextView
        val songContainer = itemView.findViewById(R.id.song_list_container) as RelativeLayout

        fun bindItems(data: Songs) {
            songTitleTextView.text = data.songTitle
            artistTextView.text = data.songArtist
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, positin: Int): SongVH {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.song_layout,parent,false)
        return SongVH(itemView)
    }

    override fun onBindViewHolder(viewHolder: SongVH, position: Int) {
        viewHolder.bindItems(song[position])
        viewHolder.songContainer.setOnClickListener{
            var args =Bundle()
            args.putParcelableArrayList("song",song)
            args.putInt("position",position)
            songPlayingFragment.arguments = args
            (context as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer,songPlayingFragment,"SongPlayingFragment")
                .addToBackStack("HomeFragment")
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return if(song.isEmpty())
            0
        else
            song.size
    }


}