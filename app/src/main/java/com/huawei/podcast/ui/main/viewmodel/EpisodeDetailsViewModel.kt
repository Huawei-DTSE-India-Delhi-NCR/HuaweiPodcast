package com.huawei.podcast.ui.main.viewmodel

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModel
import com.huawei.podcast.R
import com.huawei.podcast.ui.main.view.PlayAudioActivity
import com.huawei.podcast.utils.NetworkHelper


class EpisodeDetailsViewModel(private val networkHelper: NetworkHelper): ViewModel() {

    fun onClick(view: View){
        val i = Intent(view.context, PlayAudioActivity::class.java)
        view.context.startActivity(i)
    }

    fun showConfirmDialog(view: View) {
        val dialogBuilder = AlertDialog.Builder(view.context)
        val view: View = LayoutInflater.from(view.context).inflate(R.layout.dialog_episode_details, null, false)
        dialogBuilder.setView(view)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    fun onDownloadClick(view: View){

    }


}