package br.com.githubfinder.ui.repo_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.githubfinder.data.model.Issue
import br.com.githubfinder.data.model.Repo
import br.com.githubfinder.data.network.configuration.GithubApiService
import br.com.githubfinder.vo.enums.Status
import kotlinx.coroutines.launch
import java.io.PrintWriter
import java.io.StringWriter

class RepoDetailsFragmentViewModel : ViewModel() {

    private val apiService = GithubApiService.create()

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<Status>()

    // The external immutable LiveData for the request status
    val status: LiveData<Status>
        get() = _status

    private val _openIssues = MutableLiveData<Issue>()
    val openIssue: LiveData<Issue>
        get() = _openIssues

    private val _closedIssues = MutableLiveData<Issue>()
    val closedIssue: LiveData<Issue>
        get() = _closedIssues


    fun getOpenIssues(userName: String, repo: Repo) = viewModelScope.launch {

        try {

            _status.value = Status.LOADING
            val openIssuesOptions = userName + "/${repo.name}+type:issue+state:open"
            val result = apiService.getNumberOfIssues(openIssuesOptions)

            _openIssues.value = result

            _status.value = Status.SUCCESS

        } catch (e: Exception) {
            _status.value = Status.ERROR

            val sw = StringWriter()
            e.printStackTrace(PrintWriter(sw))
            Log.e("Search Fragment", sw.toString())
        }

    }

    fun getClosedIssues(userName: String, repo: Repo) = viewModelScope.launch {
        try {

            val closedIssuesOptions = userName + "/${repo.name}+type:issue+state:closed"
            val result = apiService.getNumberOfIssues(closedIssuesOptions)

            _closedIssues.value = result

        } catch (e: Exception) {
            val sw = StringWriter()
            e.printStackTrace(PrintWriter(sw))
            Log.e("Search Fragment", sw.toString())
        }
    }
}