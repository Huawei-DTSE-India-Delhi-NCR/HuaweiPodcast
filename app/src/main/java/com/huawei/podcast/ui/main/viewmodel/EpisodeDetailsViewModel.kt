package com.huawei.podcast.ui.main.viewmodel

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huawei.podcast.R
import com.huawei.podcast.data.model.EpisodeList
import com.huawei.podcast.data.model.EpisodeModel
import com.huawei.podcast.ui.main.view.PlayAudioActivity
import com.huawei.podcast.utils.NetworkHelper
import com.huawei.podcast.utils.Resource
import com.huawei.podcast.utils.SingleLiveEvent


class EpisodeDetailsViewModel(): ViewModel() {

    val _list = MutableLiveData<EpisodeList>()
    val setTitle = SingleLiveEvent<String>()
    val setDescription = SingleLiveEvent<String>()
    val setPublishedAt = SingleLiveEvent<String>()
    val setSlug = SingleLiveEvent<String>()

    init{
        setTitle.value = _list.value?.title
        setDescription.value = _list.value?.description
        setPublishedAt.value = _list.value?.publishedAt
        setSlug.value = _list.value?.slug
    }

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