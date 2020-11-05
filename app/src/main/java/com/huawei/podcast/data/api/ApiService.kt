package com.huawei.podcast.data.api

import com.huawei.podcast.data.model.PodCastList
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getPodCastList(): Response<List<PodCastList>>

}