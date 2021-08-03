package br.com.githubfinder.data.network.webservice

import br.com.githubfinder.data.model.Repo
import br.com.githubfinder.vo.SearchUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiWebService {
    @GET("/search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): SearchUserResponse

    @GET("users/{userName}/repos")
    suspend fun listRepos(
        @Path("userName") userName: String
    ): Response<List<Repo>>
}