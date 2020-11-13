package com.huawei.podcast.ui.main.view

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.huawei.podcast.R
import com.huawei.podcast.ui.main.adapter.DownLoadAdapter
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.include_header.*


class DownloadActivity : AppCompatActivity() {

    private lateinit var adapter: DownLoadAdapter
    private val fileList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        setUpUI()
    }

    private fun setUpUI() {
        txt_title.text = getString(R.string.download)
        img_back_arrow.setOnClickListener {
            onBackPressed()
        }
        // gets the files in the directory
        val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/PodCast")
        if (folder.listFiles() != null) {
            for (i in folder.listFiles().indices) {
                Log.i("filename", folder.listFiles()[i].toString())
                fileList.add(folder.listFiles()[i].toString())
            }
            rv_fav.layoutManager =
                    LinearLayoutManager(this).also {
                        rv_fav.layoutManager = it
                    }
            adapter = DownLoadAdapter(this, fileList)
            rv_fav.adapter = adapter
            txt_no_data.visibility = View.GONE
            rv_fav.visibility = View.VISIBLE
        } else {
            txt_no_data.visibility = View.VISIBLE
            rv_fav.visibility = View.GONE
            txt_no_data.text = getString(R.string.no_downloads)
        }
    }

}