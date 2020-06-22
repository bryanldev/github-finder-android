package br.com.githubfinder.ui.screens.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import br.com.githubfinder.databinding.FragmentSearchBinding
import br.com.githubfinder.ui.databinding.UserItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class SearchFragment : Fragment() {

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchFragmentViewModel by lazy {
        ViewModelProvider(this).get(SearchFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment - fragment_user.xml
        binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setListeners()
        setObservables()
        initRecyclerView()

        return binding.root
    }

    private fun setObservables() {
        viewModel.users.observe(viewLifecycleOwner, Observer { newUsers ->
            newUsers.forEach { user ->
                groupAdapter.add(UserItem(user))
            }
        })
    }

    @SuppressLint("RestrictedApi")
    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.setShowHideAnimationEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    private fun setListeners() {
        binding.usernameInput.setOnEditorActionListener { view, actionId, _ ->
            groupAdapter.clear()
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

    private fun initRecyclerView() {
        groupAdapter.apply {
            setOnItemClickListener { item, view ->
                viewModel.clearUsers()

                val userItem = item as UserItem

                val action = SearchFragmentDirections.actionSearchFragmentToUserFragment(
                    userItem.user.userName
                )
                view.findNavController().navigate(action)
            }
        }

        binding.recyclerviewUsers.apply {
            adapter = groupAdapter
        }
    }

    private fun hideKeyboard(view: View?) {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}