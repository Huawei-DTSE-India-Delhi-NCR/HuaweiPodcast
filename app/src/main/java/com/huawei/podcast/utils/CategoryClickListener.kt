package com.huawei.podcast.utils


import com.huawei.podcast.data.model.CategoryCollection
import com.huawei.podcast.data.model.SubCategoryCollection
import com.huawei.podcast.data.model.TrendingModel


interface CategoryClickListener {
    fun onItemCategoryClick(category : CategoryCollection,position : Int)
    fun onItemClick(category : SubCategoryCollection)
    fun onTrendingItemClick(category : TrendingModel)
}