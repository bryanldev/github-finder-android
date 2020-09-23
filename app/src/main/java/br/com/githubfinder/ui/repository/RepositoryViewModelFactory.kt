package br.com.githubfinder.ui.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class RepositoryViewModelFactory(
   private val  userName: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepositoryFragmentViewModel::class.java))
            return RepositoryFragmentViewModel(userName) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}