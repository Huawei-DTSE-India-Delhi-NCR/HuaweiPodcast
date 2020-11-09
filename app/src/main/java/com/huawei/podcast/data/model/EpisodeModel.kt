package com.huawei.podcast.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EpisodeModel(
        @SerializedName("average_duration")
    val averageDuration: Double?,
        @SerializedName("collection")
    val collection: List<EpisodeList>?,
        @SerializedName("count")
    val count: Int?,
        @SerializedName("create")
    val create: String?,
        @SerializedName("dashboard_link")
    val dashboardLink: String?,
        @SerializedName("href")
    val href: String?

): Parcelable