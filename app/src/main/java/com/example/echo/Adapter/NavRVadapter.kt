package com.example.echo.Adapter

import android.content.Context
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.example.echo.DataModels.NavMenuData
import com.example.echo.Fragments.AboutUs
import com.example.echo.Fragments.Favourites
import com.example.echo.Fragments.HomeFragment
import com.example.echo.Fragments.Settings
import com.example.echo.MainActivity
import com.example.echo.R

class NavRVadapter(val data :ArrayList<NavMenuData>, val context : Context) : RecyclerView.Adapter<NavRVadapter.NavView>() {
    var drawerLayout: DrawerLayout?=null
    class NavView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView = itemView.findViewById(R.id.iconImageView) as ImageView
        val textViewAddress = itemView.findViewById(R.id.menuTextView) as TextView
        val menuContainer = itemView.findViewById(R.id.nav_menu_container) as RelativeLayout
        fun bindItems(data: NavMenuData) {
            iconImageView.setBackgroundResource(data.icon)
            textViewAddress.text = data.text
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NavView {
        var itemView =LayoutInflater.from(p0.context).inflate(R.layout.nav_drawer_menu_layout,p0,false)
        return NavView(itemView)
    }

    override fun onBindViewHolder(p0: NavView, p1: Int) {
        p0.bindItems(data[p1])
        p0.menuContainer.setOnClickListener {
            when (p1) {
                0 -> {
                    val homeFragment = HomeFragment()
                    (context as MainActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer,homeFragment,"HomeFragment")
                        .commit()
                    (context as MainActivity).title ="Echo"
                }
                1 -> {
                    val favourites = Favourites()
                    (context as MainActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer,favourites,"favouritesFragment")
                        .commit()
                    (context as MainActivity).title ="Favourites"
                }
                2 -> {
                    val settings = Settings()
                    (context as MainActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer,settings,"settingsFragment")
                        .commit()
                    (context as MainActivity).title ="Settings"
                }
                else -> {
                    val aboutUs = AboutUs()
                    (context as MainActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer,aboutUs,"aboutUsFragment")
                        .commit()
                    (context as MainActivity).title ="About Us"
                }
            }
            drawerLayout=context.findViewById(R.id.drawer_layout)
            drawerLayout?.closeDrawers()

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}