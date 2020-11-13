package com.huawei.podcast.ui.main.view


import android.app.AlertDialog
import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huawei.podcast.R
import com.huawei.podcast.data.model.EpisodeModel
import kotlinx.android.synthetic.main.activity_episode_details.*
import kotlinx.android.synthetic.main.dialog_episode_details.*
import kotlinx.android.synthetic.main.include_play_audio.*


class EpisodeDetailsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var eList: EpisodeModel
    private var position: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode_details)
        setupUI()
    }

    private fun setupUI() {
        eList = intent.extras?.getParcelable("episode_list")!!
        position = intent.extras!!.getInt("position")
        setDetails(position)
        img_back_arrow.setOnClickListener(this)
        txt_stream.setOnClickListener(this)
        img_sub_menu.setOnClickListener(this)
        txt_download.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.img_back_arrow -> onBackPressed()
            R.id.txt_stream -> {
                val i = Intent(this, PlayAudioActivity::class.java)
                i.putExtra("episode_list", eList)
                i.putExtra("position", position)
                startActivity(i)
            }
            R.id.img_sub_menu -> showConfirmDialog()
            R.id.txt_download -> {
                val fileUri: Uri = if(eList.collection?.get(position)?.enclosureUrl.isNullOrBlank()) {
                    Uri.parse("https://cdn.simplecast.com/audio/c3161c7d-d5ac-46a9-82c1-b18cbcc93b5c/episodes/c9e4e248-788f-4307-9d65-51c68dacff27/audio/b1fc2719-4c93-4174-a8bd-d8bffa4a3363/default_tc.mp3")
                }else {
                    Uri.parse(eList.collection?.get(position)?.enclosureUrl)
                }
                val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val request = DownloadManager.Request(fileUri)
                request.setTitle(eList.collection?.get(position)?.title)
                request.setDescription("Android Audio download using DownloadManager.")
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PODCASTS, "$title.mp3")
                downloadManager.enqueue(request)
                Toast.makeText(this, " A new file is downloaded successfully",
                        Toast.LENGTH_LONG).show();

            }
        }
    }

    private fun setDetails(position: Int){
        txt_title.text = eList.collection?.get(position)?.title
        if (eList.collection?.get(position)?.description.isNullOrBlank()) {
            txt_details.text = getString(R.string.details)
        } else {
            txt_details.text = eList.collection?.get(position)?.description
        }
        if (eList.collection?.get(position)?.duration.isNullOrBlank() && eList.collection?.get(position)?.publishedAt.isNullOrBlank()) {
            txt_duration_date.text = "00:21:00 11 NOV"
        } else {
            txt_duration_date.text = "${eList.collection?.get(position)?.duration} - ${eList.collection?.get(position)?.publishedAt}"
        }
        txt_episode.text = eList.collection?.get(position)?.slug
    }

    private fun showConfirmDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_episode_details)
        val txtNext = dialog.findViewById(R.id.txt_next) as TextView
        txtNext.setOnClickListener{
            position += 1
            if(position < eList.collection?.size!!) {
                setDetails(position)
            }else{
                position = 0
                setDetails(position)
            }
            dialog.dismiss()
        }
        dialog.show()
    }

}