package br.com.githubfinder.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import br.com.githubfinder.R
import br.com.githubfinder.adapters.LoadStateAdapter
import br.com.githubfinder.adapters.RepoAdapter
import br.com.githubfinder.databinding.FragmentRepoBinding
import br.com.githubfinder.di.Injection.provideRepoFragmentViewModelFactory
import br.com.githubfinder.util.autoCleared
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class RepoFragment : Fragment() {

    private var binding by autoCleared<FragmentRepoBinding>()
    private var adapter by autoCleared<RepoAdapter>()
    private val args: RepoFragmentArgs by navArgs()

    private var searchJob: Job? = null

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            provideRepoFragmentViewModelFactory()
        ).get(RepoFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment - fragment_repository.xml
        binding = FragmentRepoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.user = args.user
        binding.retryButton.setOnClickListener { adapter.retry() }

        loadRepos()
        initAdapter()
        subscribeUi()
        toolBarConfig()
    }

    private fun loadRepos() {
        viewModel.setLogin(args.user.userName)
    }

    private fun initAdapter() {
        this.adapter = RepoAdapter()
        binding.recyclerviewRepo.adapter = adapter.withLoadStateFooter(
            footer = LoadStateAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->
            binding.loadingStatus = loadState.source.refresh

            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            showEmptyList(isListEmpty)

            checkLoadingErrors(loadState)
        }

        
    }

    private fun subscribeUi() {
        viewModel.repos.observe(viewLifecycleOwner) { response ->
            response?.let { result ->
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    adapter.submitData(result)
                }
            }
        }

        // Scroll to top when the list is refreshed from network.
        lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.recyclerviewRepo.scrollToPosition(0) }
        }
    }

    private fun toolBarConfig() {
        binding.toolbar.topAppBar.title = getString(R.string.about)
        binding.toolbar.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.recyclerviewRepo.visibility = View.GONE
        } else {
            binding.recyclerviewRepo.visibility = View.VISIBLE
        }
    }

    private fun checkLoadingErrors(loadState: CombinedLoadStates) {
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
            ?: loadState.append as? LoadState.Error
            ?: loadState.prepend as? LoadState.Error
        errorState?.let {
            Snackbar.make(
                binding.retryButton,
                "\uD83D\uDE28 Wooops ${it.error}",
                Snackbar.LENGTH_LONG
            )
                .setBackgroundTint(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                .setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorPrimary
                    )
                )
                .show()
        }
    }
}
