package br.com.githubfinder.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.githubfinder.data.model.Repo
import br.com.githubfinder.data.network.webservice.GithubApiWebService
import retrofit2.HttpException
import java.io.IOException

// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
private const val GITHUB_STARTING_PAGE_INDEX = 1
private const val NETWORK_PAGE_SIZE = 30

class GitHubRepoPagingSource(
    private val service: GithubApiWebService,
    private val query: String
) : PagingSource<Int, Repo>(){

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, Repo> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX

        return try {
            val response = service.listRepos(query, position, params.loadSize)
            val nextKey = if (response.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = response,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}