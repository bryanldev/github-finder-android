package br.com.githubfinder.ui.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.githubfinder.data.repository.GithubRepository

class RepoFragmentViewModelFactory(private val repository: GithubRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepoFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RepoFragmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}