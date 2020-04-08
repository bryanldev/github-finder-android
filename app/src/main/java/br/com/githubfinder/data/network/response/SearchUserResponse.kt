package br.com.githubfinder.data.network.response


import br.com.githubfinder.data.model.User
import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
    @SerializedName("total_count")
    val totalCount: Int,
    val items: List<User>
)