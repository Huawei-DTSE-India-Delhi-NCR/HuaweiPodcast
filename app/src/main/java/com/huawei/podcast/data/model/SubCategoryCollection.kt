package com.huawei.podcast.data.model


import com.google.gson.annotations.SerializedName

data class SubCategoryCollection(
    @SerializedName("label")
    val label: String?,
    @SerializedName("value")
    val value: String?
)