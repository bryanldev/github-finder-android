package br.com.githubfinder.data.network

import br.com.githubfinder.data.model.Issue
import br.com.githubfinder.data.model.Repo
import br.com.githubfinder.data.network.response.SearchUserResponse
import br.com.githubfinder.data.model.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.github.com/"

interface GithubApiService {

    @GET("/search/users")
    suspend fun searchUsers(@Query("q") userName: String): SearchUserResponse

    @GET("users/{userName}")
    suspend fun getUser(@Path("userName") userName: String): User

    @GET("users/{userName}/repos")
    suspend fun listRepos(@Path("userName") userName: String): List<Repo>

    @GET("search/issues")
    suspend fun getNumberOfIssues(
        @Query("q") repo: String,
        @Query("per_page") per_page: String = "1"
    ): Issue

    companion object {
        operator fun invoke(): GithubApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubApiService::class.java)
        }
    }
}