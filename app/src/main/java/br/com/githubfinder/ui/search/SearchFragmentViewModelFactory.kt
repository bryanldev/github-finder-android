package br.com.githubfinder.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.githubfinder.data.repository.GithubRepository

class SearchFragmentViewModelFactory(private val repository: GithubRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchFragmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}