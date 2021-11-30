package com.example.echo.Fragments

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cleveroad.audiovisualization.AudioVisualization
import com.cleveroad.audiovisualization.GLAudioVisualizationView
import com.example.echo.DataModels.Songs
import com.example.echo.R
import kotlin.collections.ArrayList
import com.cleveroad.audiovisualization.DbmHandler
import android.os.Handler
import android.widget.*
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.example.echo.Database.EchoDatabase
import com.example.echo.Fragments.HomeFragment.static.mediaPlayer
import com.example.echo.Fragments.SongPlaying.static.favData
import com.example.echo.Fragments.SongPlaying.static.playactive
import com.example.echo.Fragments.SongPlaying.static.position
import com.example.echo.Fragments.SongPlaying.static.song
import java.util.concurrent.TimeUnit


class SongPlaying : Fragment() {


    var shuffleactive : Boolean =false
    var looping : Boolean =false
    var fav: Boolean =false

    var favSong : Songs?=null

    var mcontext : Activity?=null

    var playpauseButton : ImageButton?=null
    var nextSongButton: ImageButton?=null
    var previousSongButton : ImageButton?=null
    var suffleButton: ImageButton?=null
    var loopButton : ImageButton?=null
    var songtitle : TextView?=null
    var songArtist : TextView?=null
    var seekBar: SeekBar?=null
    var seekbarEnd: TextView?=null
    var seekbarpos : TextView?=null

    var favbutton : ImageButton?=null


    var audioVizualizer: AudioVisualization?=null
    var glView: GLAudioVisualizationView?=null


    var handler = Handler()
    object static {
        var favData : EchoDatabase?=null
        var playactive: Boolean =false
        var song: ArrayList<Songs>?=null
        var position=0
    }


    private var updateseekbar = object : Runnable
    {
        override fun run() {
            seekBar?.max = mediaPlayer!!.duration
            seekBar?.progress = mediaPlayer!!.currentPosition

            val songDuration = TimeUnit.MILLISECONDS.toMinutes(mediaPlayer!!.duration.toLong())

            Log.d("SongPlaying", "Media player duration in minutes is $songDuration")

            val t = mediaPlayer!!.currentPosition.toLong()
            val s = String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(t),
                TimeUnit.MILLISECONDS.toSeconds(t) -
                        TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(t)
                        )
            )
            seekbarpos?.text =s
            seekbarEnd?.text = getSongDuration(mediaPlayer!!.duration.toLong())
            handler.postDelayed(this,1000)
        }
    }

    private fun getSongDuration(durationInMillis: Long): String {
        val duration: String
        val minutes = (durationInMillis/1000) / 60
        val seconds = (durationInMillis/1000) % 60

        duration = String.format("%02d:%02d", minutes, seconds)
        return duration
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_song_playing, container, false)
        playpauseButton =view.findViewById(R.id.playPauseButton)
        nextSongButton= view.findViewById(R.id.nextSong)
        previousSongButton = view.findViewById(R.id.previousSong)
        suffleButton = view.findViewById(R.id.suffleButton)
        loopButton = view.findViewById(R.id.loopButton)
        favbutton = view.findViewById(R.id.favButton)

        songArtist = view.findViewById(R.id.artistName)
        songtitle = view.findViewById(R.id.songName)

        seekBar =view?.findViewById(R.id.seekbar)
        seekbarEnd=  view?.findViewById(R.id.endTime)
        seekbarpos = view?.findViewById(R.id.startTime)

        glView = view.findViewById(R.id.visualizer_view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        audioVizualizer =glView as AudioVisualization
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

        favData = EchoDatabase(mcontext)

        mediaPlayer = MediaPlayer()
        mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)

        song  = arguments?.getParcelableArrayList("song")
        position = arguments?.getInt("position")!!

        songtitle?.text = song!![position].songTitle
        songArtist?.text = song!![position].songArtist

        playpauseSong()

        playpauseButton?.setOnClickListener{
            playpauseSong()
        }

        nextSongButton?.setOnClickListener{
            if(position== song!!.size-1) {
                position = 0
                changeSong()
            }
            else
            {
                position += 1
                changeSong()
            }

        }

        previousSongButton?.setOnClickListener {
            if(position==0)
            {
                position = song!!.size - 1
                changeSong()
            }
            else
            {
                position -= 1
                changeSong()
            }

        }

        suffleButton?.setOnClickListener {
            if(suffleButton?.isEnabled ==true)
            {
                if (shuffleactive) {
                    shuffleactive = false
                    suffleButton?.setBackgroundResource(R.drawable.shuffle_white_icon)
                    loopButton?.isEnabled = true
                    looping = false
                } else {
                    shuffleactive = true
                    suffleButton?.setBackgroundResource(R.drawable.shuffle_icon)
                    loopButton?.isEnabled = false
                    looping = false

                }
            }
            else
            {
                Toast.makeText(mcontext as Context,"cannot shuffle while loop is on",Toast.LENGTH_SHORT).show()
            }
        }

        loopButton?.setOnClickListener {
            if (loopButton?.isEnabled == true)
            {
                if (looping) {
                    looping = false
                    loopButton?.setBackgroundResource(R.drawable.loop_white_icon)
                    suffleButton?.isEnabled = true
                    shuffleactive = false
                } else {
                    looping = true
                    loopButton?.setBackgroundResource(R.drawable.loop_icon)
                    suffleButton?.isEnabled = false
                    shuffleactive = false
                }
            }
            else
            {
                Toast.makeText(mcontext as Context,"cannot loop while shuffle is on",Toast.LENGTH_SHORT).show()
            }
        }

        favbutton?.setOnClickListener {
            if(fav)
            {
                favbutton?.setBackgroundResource(R.drawable.favorite_off)
                favData?.deleteSongs(song!![position].songID)
                fav=false
            }
            else
            {
                fav=true
                favbutton?.setBackgroundResource(R.drawable.favorite_on)
                favData?.storeSongs(song!![position])

            }
        }

        checkiffav()

        val vizualizerHandler = DbmHandler.Factory.newVisualizerHandler( mcontext as Context, 0)
        audioVizualizer?.linkTo(vizualizerHandler)
    }

    fun changeSong() {
        playactive=false
        songtitle?.text = song!![position].songTitle
        songArtist?.text = song!![position].songArtist
        mediaPlayer?.reset()
        playpauseSong()
    }


    fun playpauseSong()
    {
        if(mediaPlayer!=null)
        {
            try {
                mediaPlayer?.setDataSource(
                    mcontext as Context,
                    Uri.parse(song!![position].songData)
                )
                mediaPlayer?.prepare()
            } catch (e: Exception) {
                Log.d(TAG, "onActivityCreated: In the exception track")
                Log.d(TAG, "onActivityCreated: ${e.stackTrace}")
            }
        }
        checkiffav()
        playactive = if(playactive) {
            playpauseButton?.setBackgroundResource(R.drawable.play_icon)
            mediaPlayer?.pause()
            false

        } else {
            playpauseButton?.setBackgroundResource(R.drawable.pause_icon)
            mediaPlayer?.start()
            handler.postDelayed(updateseekbar,1000)
            true
        }
        mediaPlayer?.setOnCompletionListener {
            when {
                shuffleactive -> {
                    position = (0 until song!!.size).random()
                    changeSong()
                }
                looping -> {
                    changeSong()
                }
                else -> {
                    position += 1
                    changeSong()
                }
            }
        }

        seekBar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }
        })
    }

    fun checkiffav()
    {
        if(favData?.checkSong(song!![position].songID) == true)
        {
            fav=true
            favbutton?.setBackgroundResource(R.drawable.favorite_on)
        }
        else
        {
            favbutton?.setBackgroundResource(R.drawable.favorite_off)
            fav=false
        }
    }


    override fun onPause() {
        audioVizualizer?.onPause()
        super.onPause()

    }
    override fun onResume() {
        super.onResume()
        audioVizualizer?.onResume()
    }
}
