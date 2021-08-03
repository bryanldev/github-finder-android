package br.com.githubfinder.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import br.com.githubfinder.data.model.Repo
import br.com.githubfinder.data.model.User
import br.com.githubfinder.data.network.webservice.GithubApiWebService
import br.com.githubfinder.data.repository.paging.GitHubRepoPagingSource
import br.com.githubfinder.data.repository.paging.GithubUsersPagingSource
import br.com.githubfinder.vo.enums.Status

class GithubRepository(private val service: GithubApiWebService) {

    fun searchUser(query: String): LiveData<PagingData<User>> {
        return Pager(
            config = createPagingConfig(),
            pagingSourceFactory = { GithubUsersPagingSource(service, query) }
        ).liveData
    }

    fun getRepos(query: String): LiveData<PagingData<Repo>> {
        return Pager(
            config = createPagingConfig(),
            pagingSourceFactory = { GitHubRepoPagingSource(service, query) }
        ).liveData
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 30

        private fun createPagingConfig(): PagingConfig = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        )
    }
}