package br.com.githubfinder.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.githubfinder.data.model.User
import br.com.githubfinder.data.network.webservice.GithubApiWebService
import retrofit2.HttpException
import java.io.IOException

// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
private const val GITHUB_STARTING_PAGE_INDEX = 1
private const val NETWORK_PAGE_SIZE = 30

class GithubUsersPagingSource(
    private val service: GithubApiWebService,
    private val query: String
) : PagingSource<Int, User>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX

        return try {
            val response = service.searchUsers(query, position, params.loadSize)
            val users = response.items
            val nextKey = if (users.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = users,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}