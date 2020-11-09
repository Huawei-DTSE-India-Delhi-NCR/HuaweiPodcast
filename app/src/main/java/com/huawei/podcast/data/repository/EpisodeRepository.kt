package com.huawei.podcast.data.repository

import com.huawei.podcast.data.api.ApiHelper

class EpisodeRepository(private val apiHelper: ApiHelper) {

    suspend fun getEpisode() =  apiHelper.getEpisode()

}