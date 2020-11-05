package com.huawei.podcast.data.api


import com.huawei.podcast.data.model.PodCastList
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getPodCastList(): Response<List<PodCastList>> = apiService.getPodCastList()

}