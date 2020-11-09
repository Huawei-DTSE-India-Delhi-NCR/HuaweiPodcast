package com.huawei.podcast.data.model


import com.google.gson.annotations.SerializedName

data class CategoryModel(
    @SerializedName("collection")
    val collection: List<CategoryCollection>?,
    @SerializedName("href")
    val href: String?
)