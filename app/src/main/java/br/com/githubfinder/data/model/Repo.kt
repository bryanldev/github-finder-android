package br.com.githubfinder.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repo(
    @SerializedName("created_at")
    val createdAt: String,
    val description: String,
    @SerializedName("forks_count")
    val forksCount: Int,
    val language: String,
    val name: String,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    val html_url: String
) : Parcelable