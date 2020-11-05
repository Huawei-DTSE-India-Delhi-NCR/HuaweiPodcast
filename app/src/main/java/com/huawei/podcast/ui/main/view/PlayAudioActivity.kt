package com.huawei.podcast.ui.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.huawei.podcast.R
import kotlinx.android.synthetic.main.include_play_audio.*

class PlayAudioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_audio)
        setUpUI()
    }

    private fun setUpUI(){
        img_back_arrow.setOnClickListener {
            onBackPressed()
        }
    }
}