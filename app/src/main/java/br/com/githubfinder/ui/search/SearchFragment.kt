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
import br.com.githubfinder.R
import br.com.githubfinder.adapters.UserAdapter
import br.com.githubfinder.databinding.FragmentSearchBinding
import br.com.githubfinder.di.Injection
import br.com.githubfinder.vo.Result
import com.google.android.material.snackbar.Snackbar

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchFragmentViewModel by lazy {
        ViewModelProvider(this, Injection.provideSearchFragmentViewModelFactory()).get(
            SearchFragmentViewModel::class.java
        )
    }

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

        val adapter = UserAdapter()
        binding.recyclerviewUsers.adapter = adapter
        subscribeUi(adapter)

        setupEditorActionListener()
    }

    private fun subscribeUi(adapter: UserAdapter) {
        viewModel.users.observe(viewLifecycleOwner) { response ->
            response?.let { result ->
                when (result) {
                    is Result.Success -> {
                        result.data?.let {
                            adapter.submitList(it.items)
                        }
                    }
                    is Result.Error -> {
                        Snackbar.make(
                            binding.usernameInput,
                            result.exception.message.toString(),
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
        }

        viewModel.status.observe(viewLifecycleOwner) { status ->
            status?.let {
                binding.status = status
            }
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
        val query = binding.usernameInput.text.toString()
        viewModel.setQuery(query)
    }

    private fun hideKeyboard(view: View?) {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}