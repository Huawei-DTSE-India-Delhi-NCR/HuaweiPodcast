package com.huawei.podcast.ui.main.view

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.huawei.podcast.R

class DownloadActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        // gets the files in the directory
        val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/PodCast")
        if(folder.listFiles() != null){
         for(i in folder.listFiles().indices){
            Log.i("filename", folder.listFiles()[i].toString())
         }
        }
    }


}