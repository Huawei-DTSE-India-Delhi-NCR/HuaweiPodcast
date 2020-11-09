package com.huawei.podcast.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import com.huawei.podcast.utils.NetworkHelper
import com.huawei.podcast.utils.SingleLiveEvent

class FavoriteViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    val setHeader = SingleLiveEvent<String>()

    init {
        setHeader.value = "Favorites"
    }
}