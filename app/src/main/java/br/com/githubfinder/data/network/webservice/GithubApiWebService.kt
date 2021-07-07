package br.com.githubfinder.data.network.webservice

import br.com.githubfinder.data.model.Issue
import br.com.githubfinder.data.model.Repo
import br.com.githubfinder.vo.SearchUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiWebService {
    @GET("/search/users")
    suspend fun searchUsers(@Query("q") userName: String): Response<SearchUserResponse>

    @GET("users/{userName}/repos")
    suspend fun listRepos(@Path("userName") userName: String): List<Repo>

    @GET("search/issues")
    suspend fun getNumberOfIssues(
        @Query("q") repo: String,
        @Query("per_page") per_page: String = "1"
    ): Issue
}