package br.com.githubfinder.ui.screens.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.githubfinder.R
import br.com.githubfinder.data.model.User
import br.com.githubfinder.databinding.FragmentUserBinding
import br.com.githubfinder.ui.databinding.RepoItem
import br.com.githubfinder.util.autoCleared
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.toolbar.view.*


class UserFragment : Fragment() {

    private var binding by autoCleared<FragmentUserBinding>()
    private var adapter by autoCleared<GroupAdapter<GroupieViewHolder>>()
    private val args: UserFragmentArgs by navArgs()
    private val viewModel: UserFragmentViewModel by lazy {
        ViewModelProvider(this).get(UserFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment - fragment_repository.xml
        binding = FragmentUserBinding.inflate((inflater))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // The userName from SearchFragment
        val userName = args.userName

        viewModel.getUser(userName)
        viewModel.getRepos(userName)

        val groupAdapter = GroupAdapter<GroupieViewHolder>()
        setupAdapter(groupAdapter)

        binding.recyclerviewRepo.adapter = groupAdapter
        adapter = groupAdapter

        toolBarConfig()
        setObservables()
    }

    private fun setupAdapter(groupAdapter: GroupAdapter<GroupieViewHolder>) {
        groupAdapter.setOnItemClickListener { item, view ->
            val userName = args.userName
            val repoItem = item as RepoItem

            val action = UserFragmentDirections.actionUserFragmentToRepositoryFragment(
                repoItem.repo, userName
            )
            view.findNavController().navigate(action)
        }
    }

    private fun toolBarConfig() {
        binding.toolbar.topAppBar.title = getString(R.string.about)
        binding.toolbar.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setObservables() {
        viewModel.user.observe(viewLifecycleOwner, { newUser ->
            setFields(newUser)
        })

        viewModel.repos.observe(viewLifecycleOwner, { newRepos ->
            if (adapter.itemCount == 0)
                newRepos.forEach { repo ->
                    adapter.add(RepoItem(repo))
                }
        })
    }

    private fun setFields(user: User) {
        Picasso
            .get()
            .load(user.avatarUrl)
            .noFade()
            .placeholder(R.drawable.loading_img)
            .error(R.drawable.ic_broken_image)
            .into(binding.avatarImage)

        binding.usernameText.text = user.userName
        binding.repoNumbers.text = user.publicRepos
    }
}
