package br.com.githubfinder.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.githubfinder.adapters.UserAdapter
import br.com.githubfinder.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchFragmentViewModel by lazy {
        ViewModelProvider(this).get(SearchFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        viewModel.users.observe(viewLifecycleOwner) { users ->
            adapter.submitList(users)
        }
    }

    private fun setupEditorActionListener() {
        binding.usernameInput.setOnEditorActionListener { view, actionId, _ ->
            hideKeyboard(view)
            return@setOnEditorActionListener when (actionId) {

                EditorInfo.IME_ACTION_SEARCH -> {
                    viewModel.getUsers(binding.usernameInput.text.toString())
                    true
                }
                else -> false
            }
        }
    }

    private fun hideKeyboard(view: View?) {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}