package com.huawei.podcast.data.api

import com.huawei.podcast.data.model.CategoryModel
import com.huawei.podcast.data.model.EpisodeModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    /*category*/
    @GET("categories")
    suspend fun getPodCastCategoryList(@Header("Authorization") token: String): Response<CategoryModel>

    /*episodes*/
    @GET("podcasts/c3161c7d-d5ac-46a9-82c1-b18cbcc93b5c/episodes")
    suspend fun getEpisodes(@Header("Authorization") token: String): Response<EpisodeModel>
}