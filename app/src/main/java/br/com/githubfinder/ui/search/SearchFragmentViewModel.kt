package br.com.githubfinder.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import br.com.githubfinder.data.network.configuration.GithubApiService
import br.com.githubfinder.data.repository.SearchRepository
import br.com.githubfinder.vo.Result
import br.com.githubfinder.vo.SearchUserResponse
import br.com.githubfinder.vo.enums.Status
import java.util.*


class SearchFragmentViewModel : ViewModel() {

    private val searchRepository = SearchRepository(GithubApiService.create())

    // Status of the most recent request
    val status : LiveData<Status> = searchRepository.status

    private val _query = MutableLiveData<String>()

    fun setQuery(input: String) {
        val formattedInput = input.lowercase(Locale.getDefault()).trim()

        if(formattedInput.isEmpty())
            return
        if (formattedInput == _query.value) {
            return
        }
        _query.value = formattedInput
    }

    var users: LiveData<Result<SearchUserResponse?>> = _query.switchMap { search ->
        searchRepository.searchUser(search)
    }
}