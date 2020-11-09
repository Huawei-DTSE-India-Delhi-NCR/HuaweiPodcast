package com.huawei.podcast.data.api

import com.huawei.podcast.data.model.CategoryModel
import com.huawei.podcast.data.model.EpisodeModel
import com.huawei.podcast.data.model.PodCastList
import retrofit2.Response

interface ApiHelper {

    suspend fun getPodCastCategoryList(): Response<CategoryModel>
    suspend fun getEpisode(): Response<EpisodeModel>
    suspend fun getPodCastList(): Response<List<PodCastList>>

}