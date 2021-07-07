package br.com.githubfinder.vo

import br.com.githubfinder.vo.enums.Status
import br.com.githubfinder.vo.enums.Status.*

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}