package br.com.githubfinder.ui.screens.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import br.com.githubfinder.R
import br.com.githubfinder.data.model.User
import br.com.githubfinder.databinding.FragmentUserBinding
import br.com.githubfinder.ui.databinding.RepoItem
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


class UserFragment : Fragment() {

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val args: UserFragmentArgs by navArgs()
    private lateinit var binding: FragmentUserBinding
    private val viewModel: UserFragmentViewModel by lazy {
        ViewModelProvider(this).get(UserFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // The userName from SearchFragment
        val userName = args.userName

        // Inflate the layout for this fragment - fragment_repository.xml
        binding = FragmentUserBinding.inflate((inflater))
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.getUser(userName)
        viewModel.getRepos(userName)
        setObservables()
        initRecyclerView()

        return binding.root
    }

    private fun setObservables() {
        viewModel.user.observe(viewLifecycleOwner, Observer { newUser ->
            setFields(newUser)
        })

        viewModel.repos.observe(viewLifecycleOwner, Observer { newRepos ->
            newRepos.forEach { repo ->
                groupAdapter.add(RepoItem(repo))
            }
        })
    }

    private fun initRecyclerView() {
        groupAdapter.apply {
            setOnItemClickListener { item, view ->
                viewModel.clearRepos()

                val userName = args.userName
                val repoItem = item as RepoItem

                val action = UserFragmentDirections.actionUserFragmentToRepositoryFragment(
                    arrayOf(repoItem.repo), userName
                )
                view.findNavController().navigate(action)
            }
        }

        binding.recyclerviewRepo.apply {
            adapter = groupAdapter
        }
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
