package com.huawei.podcast.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.huawei.podcast.R
import com.huawei.podcast.databinding.ActivityNavigationDrawerBindingImpl
import com.huawei.podcast.ui.main.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_navigation_drawer.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    lateinit var activityNavigationDrawerBindingImpl: ActivityNavigationDrawerBindingImpl
    private val mainViewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNavigationDrawerBindingImpl =
            DataBindingUtil.setContentView(this, R.layout.activity_navigation_drawer)
        activityNavigationDrawerBindingImpl.viewModel = mainViewModel
        activityNavigationDrawerBindingImpl.lifecycleOwner = this
        toggleDrawer()
        initializeDefaultFragment(savedInstanceState, 0)

        img_nav_menu.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        /*display home screen if user enters app after selecting choose interest*/
        val settings = getSharedPreferences("prefs", 0)
        val editor = settings.edit()
        editor.putBoolean("firstRun", false)
        editor.apply()
        val firstRun = settings.getBoolean("firstRun", true)
        Log.d("TAG1", "firstRun: " + java.lang.Boolean.valueOf(firstRun).toString())

    }

    private fun initializeDefaultFragment(
        savedInstanceState: Bundle?,
        itemIndex: Int
    ) {
        if (savedInstanceState == null) {
            val menuItem: MenuItem =
                navigation_view.menu.getItem(itemIndex).setChecked(true)
            onNavigationItemSelected(menuItem)
        }
    }

    private fun toggleDrawer() {
        val drawerToggle = ActionBarDrawerToggle(
            this, drawer_layout,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    override fun onBackPressed() {
        //Checks if the navigation drawer is open -- If so, close it
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun onNavigationItemSelected(@NonNull menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_downloads -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, HomeFragment())
                    .commit()
                closeDrawer()
            }
            R.id.nav_subscription -> {

            }
            R.id.nav_playback_history -> {

            }
            R.id.nav_favorites -> {
                val  i = Intent(this,FavouritesActivity::class.java)
                startActivity(i)
            }
            R.id.nav_add_pod_cast -> {

            }

        }
        return true
    }

    /**
     * Checks if the navigation drawer is open - if so, close it
     */
    private fun closeDrawer() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
    }


}