package com.example.echo

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.FrameLayout
import android.widget.Toast

import com.example.echo.Adapter.NavRVadapter
import com.example.echo.DataModels.NavMenuData
import com.example.echo.Fragments.HomeFragment


class MainActivity : AppCompatActivity() {

    var drawerLayout: DrawerLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val homeFragment = HomeFragment()
        this.supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer,homeFragment,"HomeFragment")
            .commit()
        initializedrawerlayout()
        initialiseRecyclerView(this)
    }

    override fun onStart() {
        super.onStart()
    }

    fun initializedrawerlayout()
    {
        val toolbar= findViewById<android.support.v7.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout=findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open
            ,R.string.navigation_drawer_close)
        drawerLayout?.setDrawerListener(toggle)
        toggle.syncState()
    }

    fun initialiseRecyclerView(context :Context){


        val NavMenuRecyclerView : RecyclerView =findViewById(R.id.nav_menu_recycler_view)
        NavMenuRecyclerView.layoutManager = LinearLayoutManager(context)

        val users = ArrayList<NavMenuData>()
        users.add(NavMenuData("All Songs", R.drawable.navigation_allsongs))
        users.add(NavMenuData("Favourites", R.drawable.navigation_favorites))
        users.add(NavMenuData("Settings", R.drawable.navigation_settings))
        users.add(NavMenuData("About Us",R.drawable.navigation_aboutus))


        var NavRVadapter =NavRVadapter(users,this@MainActivity)
        NavMenuRecyclerView.adapter =NavRVadapter


    }


}