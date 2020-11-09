package com.huawei.podcast.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EpisodeList(
        @SerializedName("description")
        val description: String?,
        @SerializedName("duration")
        val duration: String?,
        @SerializedName("enclosure_url")
        val enclosureUrl: String?,
        @SerializedName("published_at")
        val publishedAt: String?,
        @SerializedName("slug")
        val slug: String?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("updated_at")
        val updatedAt: String?
) : Parcelable