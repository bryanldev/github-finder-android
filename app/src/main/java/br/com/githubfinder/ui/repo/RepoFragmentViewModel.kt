package br.com.githubfinder.ui.repo

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.githubfinder.data.model.Repo
import br.com.githubfinder.data.repository.GithubRepository

class RepoFragmentViewModel(private val repository: GithubRepository) : ViewModel() {

    private val _login = MutableLiveData<String>()

    var repos: LiveData<PagingData<Repo>> = _login.switchMap { login ->
        repository.getRepos(login).cachedIn(viewModelScope)
    }

    fun setLogin(login: String) {
        if (_login.value != login)
            _login.value = login
    }
}