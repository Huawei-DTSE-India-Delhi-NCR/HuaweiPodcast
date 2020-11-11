package com.huawei.podcast.data.model


import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.Parcelable
import android.view.View
import android.widget.Toast
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
) : Parcelable {
    fun onClick(view: View) {
        val fileUri: Uri = if (enclosureUrl.isNullOrBlank()) {
            Uri.parse("https://cdn.simplecast.com/audio/c3161c7d-d5ac-46a9-82c1-b18cbcc93b5c/episodes/c9e4e248-788f-4307-9d65-51c68dacff27/audio/b1fc2719-4c93-4174-a8bd-d8bffa4a3363/default_tc.mp3")
        } else {
            Uri.parse(enclosureUrl)
        }
        val downloadManager = view.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(fileUri)
        request.setTitle(title)
        request.setDescription("Android Audio download using DownloadManager.")
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS.toString() + "/PodCast", "$title.mp3")
        downloadManager.enqueue(request)
        Toast.makeText(view.context, " A new file is downloaded successfully",
                Toast.LENGTH_LONG).show();
    }
}