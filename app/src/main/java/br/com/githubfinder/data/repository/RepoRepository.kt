package br.com.githubfinder.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import br.com.githubfinder.data.network.webservice.GithubApiWebService
import br.com.githubfinder.vo.Result
import br.com.githubfinder.vo.enums.Status
import java.net.ConnectException

class RepoRepository(private val service: GithubApiWebService) {
    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    fun getRepos(userName: String) = liveData {
        try{
            _status.value = Status.LOADING

            val response = service.listRepos(userName)
            if (response.isSuccessful){
                _status.value = Status.SUCCESS
                emit(Result.Success(response.body()))
            }
            else {
                _status.value = Status.ERROR
                emit(Result.Error(exception = Exception("Failed to fetch address")))
            }
        } catch (e: ConnectException) {
            emit(Result.Error(exception = Exception("Failed to communicate with API")))
        } catch (e: Exception) {
            Log.e("RepoRepository", "getRepos: ", e)
            emit(Result.Error(exception = Exception("An unknown failure has occurred")))
        }
    }
}