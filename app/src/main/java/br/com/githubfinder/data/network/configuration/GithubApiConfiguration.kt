package br.com.githubfinder.data.network.configuration

import br.com.githubfinder.data.network.webservice.GithubApiWebService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface GithubApiService {
    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): GithubApiWebService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubApiWebService::class.java)
        }
    }
}