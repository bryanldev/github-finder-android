package br.com.githubfinder.data.network.configuration

import br.com.githubfinder.data.network.webservice.GithubApiWebService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


interface GithubApiService {
    companion object {
        private const val BASE_URL = "https://api.github.com/"

        var okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        fun create(): GithubApiWebService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubApiWebService::class.java)
        }
    }
}