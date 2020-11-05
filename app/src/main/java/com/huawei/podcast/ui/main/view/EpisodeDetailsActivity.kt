package com.huawei.podcast.ui.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.huawei.podcast.R
import com.huawei.podcast.databinding.ActivityEpisodeDetailsBinding
import com.huawei.podcast.ui.main.viewmodel.EpisodeDetailsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class EpisodeDetailsActivity : AppCompatActivity(){

    private val episodeViewModel: EpisodeDetailsViewModel by viewModel()
    lateinit  var activityEpisodeDetailsBinding: ActivityEpisodeDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityEpisodeDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_episode_details)
        activityEpisodeDetailsBinding.viewModel = episodeViewModel
        activityEpisodeDetailsBinding.lifecycleOwner = this
        setupUI()
    }

    private fun setupUI(){
        activityEpisodeDetailsBinding.include.imgBackArrow.setOnClickListener {
            onBackPressed()
        }
    }
}