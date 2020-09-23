package br.com.githubfinder.ui.screens.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import br.com.githubfinder.databinding.FragmentSearchBinding
import br.com.githubfinder.ui.databinding.UserItem
import br.com.githubfinder.util.autoCleared
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class SearchFragment : Fragment() {

    private var binding by autoCleared<FragmentSearchBinding>()
    private var adapter by autoCleared<GroupAdapter<GroupieViewHolder>>()
    private val viewModel: SearchFragmentViewModel by lazy {
        ViewModelProvider(this).get(SearchFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment - fragment_repository.xml
        binding = FragmentSearchBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val groupAdapter = GroupAdapter<GroupieViewHolder>()
        setupAdapter(groupAdapter)

        binding.recyclerviewUsers.adapter = groupAdapter
        adapter = groupAdapter

        setupEditorActionListener()
        setupUsersObservable()
    }

    private fun setupAdapter(groupAdapter: GroupAdapter<GroupieViewHolder>) {
        groupAdapter.setOnItemClickListener { item, v ->
            val userItem = item as UserItem

            val action = SearchFragmentDirections.actionSearchFragmentToUserFragment(
                userItem.user.userName
            )
            v.findNavController().navigate(action)
        }
    }

    private fun setupEditorActionListener() {
        binding.usernameInput.setOnEditorActionListener { view, actionId, _ ->
            hideKeyboard(view)
            adapter.clear()
            return@setOnEditorActionListener when (actionId) {

                EditorInfo.IME_ACTION_SEARCH -> {
                    viewModel.getUsers(binding.usernameInput.text.toString())
                    true
                }
                else -> false
            }
        }
    }

    private fun setupUsersObservable() {
        viewModel.users.observe(viewLifecycleOwner, { newUsers ->
            newUsers.forEach { user ->
                adapter.add(UserItem(user))
            }
        })
    }

    private fun hideKeyboard(view: View?) {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}