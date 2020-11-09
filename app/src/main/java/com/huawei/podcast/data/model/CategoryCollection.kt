package com.huawei.podcast.data.model


import com.google.gson.annotations.SerializedName

data class CategoryCollection(
    @SerializedName("label")
    val label: String?,
    @SerializedName("rss_label")
    val rssLabel: String?,
    @SerializedName("subcategories")
    val subcategories: Subcategories?,
    @SerializedName("value")
    val value: String?
)