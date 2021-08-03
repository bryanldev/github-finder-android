package br.com.githubfinder.ui.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.githubfinder.data.model.User
import br.com.githubfinder.data.repository.GithubRepository
import java.util.*


class SearchFragmentViewModel(private val repository: GithubRepository) : ViewModel() {

    private val _query = MutableLiveData<String>()

    fun setQuery(input: String) {
        val formattedInput = input.lowercase(Locale.getDefault()).trim()

        if (formattedInput.isEmpty())
            return
        if (formattedInput == _query.value) {
            return
        }
        _query.value = formattedInput
    }

    var users: LiveData<PagingData<User>> = _query.switchMap { search ->
        repository.searchUser(search).cachedIn(viewModelScope)
    }
}