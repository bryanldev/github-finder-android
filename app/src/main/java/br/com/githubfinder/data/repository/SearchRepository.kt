package br.com.githubfinder.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import br.com.githubfinder.data.network.webservice.GithubApiWebService
import br.com.githubfinder.vo.Result
import br.com.githubfinder.vo.enums.Status
import br.com.githubfinder.vo.enums.Status.*
import java.net.ConnectException

class SearchRepository constructor(private val service: GithubApiWebService) {
    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    fun searchUser(userName: String) = liveData {
        try {
            _status.value = LOADING

            val response = service.searchUsers(userName)
            if (response.isSuccessful){
                _status.value = SUCCESS
                emit(Result.Success(response.body()))
            }
            else {
                _status.value = ERROR
                emit(Result.Error(exception = Exception("Failed to fetch address")))
            }
        } catch (e: ConnectException) {
            emit(Result.Error(exception = Exception("Failed to communicate with API")))
        } catch (e: Exception) {
            Log.e("SearchRepository", "searchUser: ", e)
            emit(Result.Error(exception = Exception("An unknown failure has occurred")))
        }
    }
}