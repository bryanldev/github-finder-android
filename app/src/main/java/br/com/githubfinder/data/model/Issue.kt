package br.com.githubfinder.data.model


import com.google.gson.annotations.SerializedName

class Issue(
    @SerializedName("total_count")
    val totalCount: Int
)