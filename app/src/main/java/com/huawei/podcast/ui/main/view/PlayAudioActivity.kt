package com.huawei.podcast.ui.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.huawei.podcast.R
import com.huawei.podcast.data.model.EpisodeList
import kotlinx.android.synthetic.main.activity_play_audio.*
import kotlinx.android.synthetic.main.include_play_audio.*

class PlayAudioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_audio)
        setUpUI()
    }

    private fun setUpUI(){
       val eList = intent.extras?.getParcelable<EpisodeList>("episode_list")
        txt_title.text = eList?.title
        txt_author.text = eList?.slug
        img_back_arrow.setOnClickListener {
            onBackPressed()
        }
    }
}