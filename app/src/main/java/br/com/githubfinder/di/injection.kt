package br.com.githubfinder.di

import androidx.lifecycle.ViewModelProvider
import br.com.githubfinder.data.network.configuration.GithubApiService
import br.com.githubfinder.data.repository.GithubRepository
import br.com.githubfinder.ui.repo.RepoFragmentViewModelFactory
import br.com.githubfinder.ui.search.SearchFragmentViewModelFactory

object Injection {

    private fun provideGithubRepository(): GithubRepository {
        return GithubRepository(GithubApiService.create())
    }

    fun provideSearchFragmentViewModelFactory(): ViewModelProvider.Factory {
        return SearchFragmentViewModelFactory(provideGithubRepository())
    }

    fun provideRepoFragmentViewModelFactory(): ViewModelProvider.Factory {
        return RepoFragmentViewModelFactory(provideGithubRepository())
    }
}