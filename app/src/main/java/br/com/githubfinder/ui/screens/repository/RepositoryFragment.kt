package br.com.githubfinder.ui.screens.repository

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.githubfinder.data.model.Issue
import br.com.githubfinder.data.model.Repo
import br.com.githubfinder.databinding.FragmentRepositoryBinding
import br.com.githubfinder.util.brazilDateFormat
import kotlinx.android.synthetic.main.toolbar.view.*


class RepositoryFragment : Fragment() {

    private lateinit var binding: FragmentRepositoryBinding
    private val args: RepositoryFragmentArgs by navArgs()
    private val viewModel: RepositoryFragmentViewModel by lazy {
        ViewModelProvider(this).get(RepositoryFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment - fragment_repository.xml
        binding = FragmentRepositoryBinding.inflate((inflater))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // From UserFragment
        val repoDetails = args.repoDetails
        val userName = args.userName

        viewModel.getOpenIssues(userName, repoDetails)
        viewModel.getClosedIssues(userName, repoDetails)

        toolBarConfig()
        setObservables()
        setListener(repoDetails)
        setFields(repoDetails)
    }

    private fun toolBarConfig() {
        val repoDetails = args.repoDetails

        binding.toolbar.topAppBar.title = repoDetails.name
        binding.toolbar.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setObservables() {
        viewModel.openIssue.observe(viewLifecycleOwner, { issue ->
            setOpenIssues(issue)
        })

        viewModel.closedIssue.observe(viewLifecycleOwner, { issue ->
            setClosedIssues(issue)
        })
    }

    private fun setListener(repoDetails: Repo) {
        // Takes the user to the git page via browser
        binding.gitButton.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(repoDetails.html_url))
            startActivity(browserIntent)
        }
    }

    private fun setOpenIssues(issue: Issue) {
        binding.issuesOpenText.text = issue.totalCount.toString()
    }

    private fun setClosedIssues(issue: Issue) {
        binding.issuesClosedText.text = issue.totalCount.toString()
    }

    private fun setFields(repoDetails: Repo) {
        binding.apply {
            descriptionText.text = repoDetails.description
            starText.text = repoDetails.stargazersCount.toString()
            forkText.text = repoDetails.forksCount.toString()
            creationDateText.text = repoDetails.createdAt.brazilDateFormat()
        }
    }
}