package br.com.githubfinder.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import br.com.githubfinder.R
import br.com.githubfinder.adapters.UserAdapter
import br.com.githubfinder.adapters.LoadStateAdapter
import br.com.githubfinder.databinding.FragmentSearchBinding
import br.com.githubfinder.di.Injection
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchFragmentViewModel by lazy {
        ViewModelProvider(this, Injection.provideSearchFragmentViewModelFactory()).get(
            SearchFragmentViewModel::class.java
        )
    }
    private var adapter = UserAdapter()

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment - fragment_search.xml
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.retryButton.setOnClickListener { adapter.retry() }

        initAdapter()
        subscribeUi()

        setupEditorActionListener()
    }

    private fun initAdapter() {
        binding.recyclerviewUsers.adapter = adapter.withLoadStateFooter(
            footer = LoadStateAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->
            binding.status = loadState.source.refresh

            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            showEmptyList(isListEmpty)

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Snackbar.make(
                    binding.usernameInput,
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

    private fun subscribeUi() {
        viewModel.users.observe(viewLifecycleOwner) { response ->
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
                .collect { binding.recyclerviewUsers.scrollToPosition(0) }
        }
    }

    private fun setupEditorActionListener() {
        binding.usernameInput.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(view)
                true
            } else {
                false
            }
        }
    }

    private fun doSearch(view: View) {
        hideKeyboard(view)
        binding.usernameInput.text.trim().let {
            if (it.isNotEmpty()) {
                binding.recyclerviewUsers.scrollToPosition(0)
                viewModel.setQuery(it.toString())
            }
        }
    }

    private fun hideKeyboard(view: View?) {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.recyclerviewUsers.visibility = View.GONE
        } else {
            binding.recyclerviewUsers.visibility = View.VISIBLE
        }
    }
}