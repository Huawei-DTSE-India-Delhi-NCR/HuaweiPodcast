package com.huawei.podcast.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Season(
    @SerializedName("href")
    val href: String?,
    @SerializedName("number")
    val number: Int?
): Parcelable