package com.huawei.podcast.data.api

import com.huawei.podcast.data.model.PodCastList
import retrofit2.Response

interface ApiHelper {

    suspend fun getPodCastList(): Response<List<PodCastList>>
}