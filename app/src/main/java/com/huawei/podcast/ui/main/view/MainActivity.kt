package com.huawei.podcast.ui.main.view


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.huawei.podcast.R
import kotlinx.android.synthetic.main.activity_navigation_drawer.*


class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
        //default fragment
        val tx: FragmentTransaction = supportFragmentManager.beginTransaction()
        tx.replace(R.id.frame_layout, HomeFragment())
        tx.commit()
        // Initialize the action bar drawer toggle instance
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                drawer_layout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)

            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)

            }
        }


        // Configure the drawer layout to add listener and show icon on toolbar
        drawerToggle.isDrawerIndicatorEnabled = true
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()


        // Set navigation view navigation item selected listener
        navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_downloads -> {
                    val i = Intent(this, DownloadActivity::class.java);
                    startActivity(i)
                }
                R.id.nav_subscription -> toast("clicked")
                R.id.nav_playback_history -> toast("clicked")
                R.id.nav_favorites -> {
                    toast("clicked")
                }
            }
            // Close the drawer
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
        /*onclick listener*/
        img_nav_menu.setOnClickListener(this)
        img_search.setOnClickListener(this)
    }



    private fun Context.toast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_nav_menu -> drawer_layout.openDrawer(GravityCompat.START)
            R.id.img_search -> {
                val i = Intent(this, SearchActivity::class.java);
                startActivity(i)
            }
        }
    }
}



