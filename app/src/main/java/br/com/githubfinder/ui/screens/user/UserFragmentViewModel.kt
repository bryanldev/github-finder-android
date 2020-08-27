package br.com.githubfinder.ui.screens.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.githubfinder.data.model.Repo
import br.com.githubfinder.data.model.User
import br.com.githubfinder.data.network.GithubApiService
import br.com.githubfinder.data.network.GithubApiStatus
import kotlinx.coroutines.launch
import java.io.PrintWriter
import java.io.StringWriter

class UserFragmentViewModel : ViewModel() {

    private val apiService = GithubApiService()

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<GithubApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<GithubApiStatus>
        get() = _status

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _repos = MutableLiveData<List<Repo>>()
    val repos: LiveData<List<Repo>>
        get() = _repos


    fun getUser(userName: String) = viewModelScope.launch {
        try {

            val user = apiService.getUser(userName)

            _user.value = user

        } catch (e: Exception) {
            val sw = StringWriter()
            e.printStackTrace(PrintWriter(sw))
            Log.e("Search Fragment", e.message.toString())
            Log.e("Search Fragment", sw.toString())
        }
    }

    fun getRepos(userName: String) = viewModelScope.launch {
        try {
            _status.value = GithubApiStatus.LOADING

            val repos = apiService.listRepos(userName)

            _status.value = GithubApiStatus.DONE

            _repos.value = repos

        } catch (e: Exception) {
            _status.value = GithubApiStatus.ERROR

            val sw = StringWriter()
            e.printStackTrace(PrintWriter(sw))
            Log.e("Search Fragment", sw.toString())
        }
    }

    fun clearRepos() {
        _repos.value = listOf()
    }

}