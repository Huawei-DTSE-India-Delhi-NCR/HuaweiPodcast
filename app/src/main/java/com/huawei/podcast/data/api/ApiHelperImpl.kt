package com.huawei.podcast.data.api


import com.huawei.podcast.data.model.CategoryModel
import com.huawei.podcast.data.model.EpisodeModel
import com.huawei.podcast.data.model.PodCastList
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getPodCastCategoryList(): Response<CategoryModel>  = apiService.getPodCastCategoryList(token = "Bearer eyJhcGlfa2V5IjoiNzVkMzc3N2M3NWFhM2QwOTkxOWEyZTI4ZjhiM2M1YTkifQ==")
    override suspend fun getEpisode(): Response<EpisodeModel> = apiService.getEpisodes(token = "Bearer eyJhcGlfa2V5IjoiNzVkMzc3N2M3NWFhM2QwOTkxOWEyZTI4ZjhiM2M1YTkifQ==")
    override suspend fun getPodCastList(): Response<List<PodCastList>> = apiService.getPodCastList()

}