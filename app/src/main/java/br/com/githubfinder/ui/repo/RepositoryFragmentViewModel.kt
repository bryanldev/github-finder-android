package br.com.githubfinder.ui.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.githubfinder.data.model.Repo
import br.com.githubfinder.data.model.User
import br.com.githubfinder.data.network.configuration.GithubApiService
import br.com.githubfinder.vo.enums.Status
import kotlinx.coroutines.launch
import java.io.PrintWriter
import java.io.StringWriter

class RepositoryFragmentViewModel(val userName: String) : ViewModel() {

    private val apiService = GithubApiService.create()

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<Status>()

    // The external immutable LiveData for the request status
    val status: LiveData<Status>
        get() = _status

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _repos = MutableLiveData<List<Repo>>()
    val repos: LiveData<List<Repo>>
        get() = _repos

    fun getRepos() = viewModelScope.launch {
        try {
            _status.value = Status.LOADING

            val repos = apiService.listRepos(userName)

            _status.value = Status.SUCCESS

            _repos.value = repos

        } catch (e: Exception) {
            _status.value = Status.ERROR

            val sw = StringWriter()
            e.printStackTrace(PrintWriter(sw))
            Log.e("Search Fragment", sw.toString())
        }
    }
}