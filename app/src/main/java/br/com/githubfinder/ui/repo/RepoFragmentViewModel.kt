package br.com.githubfinder.ui.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import br.com.githubfinder.data.model.Repo
import br.com.githubfinder.data.repository.GithubRepository
import br.com.githubfinder.util.AbsentLiveData
import br.com.githubfinder.vo.Result
import br.com.githubfinder.vo.enums.Status

class RepoFragmentViewModel(private val repository: GithubRepository) : ViewModel() {

    // Status of the most recent request
    val status : LiveData<Status> = repository.status

    private val _login = MutableLiveData<String?>()

    var repos: LiveData<Result<List<Repo>?>> = _login.switchMap { login ->
        if (login == null) {
            AbsentLiveData.create()
        } else {
            repository.getRepos(login)
        }
    }

    fun setLogin(login: String?) {
        if (_login.value != login)
            _login.value = login
    }

    fun retry() {
        _login.value?.let {
            _login.value = it
        }
    }
}