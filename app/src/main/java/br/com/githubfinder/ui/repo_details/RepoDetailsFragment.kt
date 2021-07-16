package br.com.githubfinder.ui.repo_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.githubfinder.databinding.FragmentRepoDetailsBinding
import br.com.githubfinder.ui.repo_details.RepoDetailsFragment.Callback
import kotlinx.android.synthetic.main.toolbar.view.*

class RepoDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRepoDetailsBinding
    private val args: RepoDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment - fragment_repo_details.xml
        binding = FragmentRepoDetailsBinding.inflate((inflater))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.repoInfo = args.repoDetails
        binding.callback = Callback { repoUrl ->
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(repoUrl))
            startActivity(browserIntent)
        }

        toolBarConfig()
    }

    private fun toolBarConfig() {
        val repoDetails = args.repoDetails

        binding.toolbar.topAppBar.title = repoDetails.name
        binding.toolbar.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    fun interface Callback {
        fun navigate(url: String)
    }
}