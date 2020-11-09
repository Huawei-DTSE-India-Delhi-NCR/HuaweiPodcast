package com.huawei.podcast.utils



import com.huawei.podcast.data.model.EpisodeList



interface EpisodeClickListener {
    fun onItemClick(category: EpisodeList)
}

