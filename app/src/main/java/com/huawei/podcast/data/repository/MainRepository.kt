package com.huawei.podcast.data.repository

import com.huawei.podcast.data.api.ApiHelper


class MainRepository (private val apiHelper: ApiHelper) {

    suspend fun getPodCastList() =  apiHelper.getPodCastList()

}