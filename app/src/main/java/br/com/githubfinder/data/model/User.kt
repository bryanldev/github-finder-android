package br.com.githubfinder.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("login")
    val userName: String,
    val bio: String,
    @SerializedName("public_repos")
    val publicRepos: String
): Parcelable