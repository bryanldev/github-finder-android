package br.com.githubfinder.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.githubfinder.R
import br.com.githubfinder.adapters.RepoAdapter
import br.com.githubfinder.databinding.FragmentRepoBinding
import br.com.githubfinder.util.autoCleared
import br.com.githubfinder.vo.Result
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.toolbar.view.*

class RepoFragment : Fragment() {

    private var binding by autoCleared<FragmentRepoBinding>()
    private var adapter by autoCleared<RepoAdapter>()
    private val args: RepoFragmentArgs by navArgs()

    private val viewModel by viewModels<RepoFragmentViewModel>()

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
        binding.viewModel = viewModel
        binding.user = args.user

        loadRepos()
        setupAdapter()
        subscribeUi()
        toolBarConfig()
    }

    private fun loadRepos() {
        viewModel.setLogin(args.user.userName)
    }

    private fun setupAdapter() {
        this.adapter = RepoAdapter()
        binding.recyclerviewRepo.adapter = adapter
    }

    private fun subscribeUi() {
        viewModel.repos.observe(viewLifecycleOwner) { repos ->
            repos?.let{ result ->
                when (result){
                    is Result.Success -> {
                        adapter.submitList(result.data)
                    }
                    is Result.Error -> {
                        Snackbar.make(
                            binding.usernameText,
                            result.exception.message.toString(),
                            Snackbar.LENGTH_LONG
                        )
                            .setBackgroundTint(ContextCompat.getColor(requireContext(),R.color.white))
                            .setTextColor(ContextCompat.getColor(requireContext(),R.color.colorPrimary))
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

    private fun toolBarConfig() {
        binding.toolbar.topAppBar.title = getString(R.string.about)
        binding.toolbar.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }


}
