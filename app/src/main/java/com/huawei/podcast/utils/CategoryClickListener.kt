package com.huawei.podcast.utils


import com.huawei.podcast.data.model.CategoryCollection
import com.huawei.podcast.data.model.SubCategoryCollection


interface CategoryClickListener {
    fun onItemCategoryClick(category : CategoryCollection,position : Int)
    fun onItemClick(category : SubCategoryCollection)
}