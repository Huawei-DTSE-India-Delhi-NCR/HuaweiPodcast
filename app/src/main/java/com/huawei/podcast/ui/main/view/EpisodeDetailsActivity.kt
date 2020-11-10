package com.huawei.podcast.ui.main.view


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.huawei.podcast.R
import com.huawei.podcast.data.model.EpisodeModel
import kotlinx.android.synthetic.main.activity_episode_details.*
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

        img_back_arrow.setOnClickListener(this)
        txt_stream.setOnClickListener(this)
        img_sub_menu.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.img_back_arrow -> onBackPressed()
            R.id.txt_stream -> {
                val i = Intent(this, PlayAudioActivity::class.java)
                i.putExtra("episode_list", eList)
                i.putExtra("position",position)
                startActivity(i)
            }
            R.id.img_sub_menu -> showConfirmDialog()
        }
    }

    private fun showConfirmDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_episode_details, null, false)
        dialogBuilder.setView(view)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

}