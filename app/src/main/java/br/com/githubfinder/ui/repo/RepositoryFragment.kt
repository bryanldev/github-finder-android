package br.com.githubfinder.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.githubfinder.R
import br.com.githubfinder.adapters.RepoAdapter
import br.com.githubfinder.data.model.User
import br.com.githubfinder.databinding.FragmentRepositoryBinding
import br.com.githubfinder.util.autoCleared
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.toolbar.view.*


class RepositoryFragment : Fragment() {

    private var binding by autoCleared<FragmentRepositoryBinding>()
    private var adapter by autoCleared<RepoAdapter>()
    private val args: RepositoryFragmentArgs by navArgs()

    private val viewModel by viewModels<RepositoryFragmentViewModel> {
        RepositoryViewModelFactory(
            args.user.userName
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment - fragment_repository.xml
        binding = FragmentRepositoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        loadRepos()
        setupAdapter()
        subscribeUi()
        setFields()
        toolBarConfig()
    }

    private fun loadRepos() {
        viewModel.getRepos()
    }

    private fun setupAdapter() {
        val adapter = RepoAdapter()
        this.adapter = adapter
        binding.recyclerviewRepo.adapter = adapter
    }

    private fun subscribeUi() {
        viewModel.repos.observe(viewLifecycleOwner) { repos ->
            adapter.submitList(repos)
        }
    }

    private fun setFields() {
        val user = args.user

        loadAvatar(user)
        binding.usernameText.text = user.userName
        binding.repoNumbers.text = user.publicRepos
    }

    private fun loadAvatar(user: User) {
        Picasso
            .get()
            .load(user.avatarUrl)
            .noFade()
            .placeholder(R.drawable.loading_img)
            .error(R.drawable.ic_broken_image)
            .into(binding.avatarImage)
    }

    private fun toolBarConfig() {
        binding.toolbar.topAppBar.title = getString(R.string.about)
        binding.toolbar.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }


}
