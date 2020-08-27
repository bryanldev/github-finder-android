package br.com.githubfinder.ui.screens.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.githubfinder.data.network.GithubApiService
import br.com.githubfinder.data.network.GithubApiStatus
import br.com.githubfinder.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.PrintWriter
import java.io.StringWriter



class SearchFragmentViewModel : ViewModel(){

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<GithubApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<GithubApiStatus>
        get() = _status


    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    fun getUsers(userName: String) = viewModelScope.launch {
        try {
            _status.value = GithubApiStatus.LOADING

            if (userName.isNotEmpty()) {
                val apiService = GithubApiService()
                val users = apiService.searchUsers(userName).items
                _users.value = users
            }
            _status.value = GithubApiStatus.DONE
        } catch (e: Exception) {
            _status.value = GithubApiStatus.ERROR

            val sw = StringWriter()
            e.printStackTrace(PrintWriter(sw))
            Log.e("Search Fragment", sw.toString())
        }

    }

    fun clearUsers() {
        _users.value = listOf()
    }
}