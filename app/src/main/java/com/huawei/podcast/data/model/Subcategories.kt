package com.huawei.podcast.data.model


import com.google.gson.annotations.SerializedName

data class Subcategories(
    @SerializedName("collection")
    val collection: List<SubCategoryCollection>?
)