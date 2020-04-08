package br.com.githubfinder.data.model


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("login")
    val userName: String,
    val bio: String,
    @SerializedName("public_repos")
    val publicRepos: String
)