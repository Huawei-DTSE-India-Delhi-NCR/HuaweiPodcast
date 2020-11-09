package com.huawei.podcast.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EpisodeList(
    @SerializedName("analytics")
    val analytics: String?,
    @SerializedName("audio_status")
    val audioStatus: String?,
    @SerializedName("days_since_release")
    val daysSinceRelease: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("duration")
    val duration: String?,
    @SerializedName("enclosure_url")
    val enclosureUrl: String?,
    @SerializedName("feeds")
    val feeds: String?,
    @SerializedName("guid")
    val guid: String?,
    @SerializedName("href")
    val href: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image_path")
    val imagePath: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("is_hidden")
    val isHidden: Boolean?,
    @SerializedName("number")
    val number: Int?,
    @SerializedName("published_at")
    val publishedAt: String?,
    @SerializedName("scheduled_for")
    val scheduledFor: String?,
    @SerializedName("season")
    val season: Season?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
): Parcelable