package br.com.githubfinder.ui.screens.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.githubfinder.data.model.Issue
import br.com.githubfinder.data.model.Repo
import br.com.githubfinder.data.network.GithubApiService
import br.com.githubfinder.data.network.GithubApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.Exception

class RepositoryFragmentViewModel : ViewModel() {

    private val apiService = GithubApiService()

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<GithubApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<GithubApiStatus>
        get() = _status

    private val _openIssues = MutableLiveData<Issue>()
    val openIssue: LiveData<Issue>
        get() = _openIssues

    private val _closedIssues = MutableLiveData<Issue>()
    val closedIssue: LiveData<Issue>
        get() = _closedIssues


    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    fun getOpenIssues(userName: String, repo: Array<Repo>) = coroutineScope.launch {

        try {

            _status.value = GithubApiStatus.LOADING
            val openIssuesOptions = userName + "/${repo[0].name}+type:issue+state:open"
            val result = apiService.getNumberOfIssues(openIssuesOptions)

            _openIssues.value = result

            _status.value = GithubApiStatus.DONE

        } catch (e: Exception) {
            _status.value = GithubApiStatus.ERROR

            val sw = StringWriter()
            e.printStackTrace(PrintWriter(sw))
            Log.e("Search Fragment", sw.toString())
        }

    }

    fun getClosedIssues(userName: String, repo: Array<Repo>) = coroutineScope.launch {
        try {

            val closedIssuesOptions = userName + "/${repo[0].name}+type:issue+state:closed"
            val result = apiService.getNumberOfIssues(closedIssuesOptions)

            _closedIssues.value = result

        } catch (e: Exception) {
            val sw = StringWriter()
            e.printStackTrace(PrintWriter(sw))
            Log.e("Search Fragment", sw.toString())
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}