package com.huawei.podcast.data.repository

import com.huawei.podcast.data.api.ApiHelper

class CategoryRepository(private val apiHelper: ApiHelper) {

    suspend fun getPodCastCategoryList() =  apiHelper.getPodCastCategoryList()

}