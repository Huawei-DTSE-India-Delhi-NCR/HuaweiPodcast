package com.huawei.podcast.utils

import com.huawei.podcast.data.model.PodCastList

interface ClickListener {
    fun onItemClick(country : PodCastList)
}