package br.com.githubfinder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.githubfinder.data.model.Repo
import br.com.githubfinder.databinding.ListItemRepoBinding
import br.com.githubfinder.ui.repository.RepositoryFragmentDirections

class RepoAdapter : ListAdapter<Repo, RecyclerView.ViewHolder>(RepoDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RepoViewHolder(
            ListItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repo = getItem(position)
        (holder as RepoViewHolder).bind(repo)
    }
}

class RepoViewHolder(
    private val binding: ListItemRepoBinding
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.setClickListener {
            binding.repo?.let {  repo ->
                navigateToRepoDetails(repo, it)
            }
        }
    }

    private fun navigateToRepoDetails(
        repo: Repo,
        view: View
    ) {
        val direction = RepositoryFragmentDirections.actionRepositoryFragmentToRepoDetailsFragment(
            repo
        )
        view.findNavController().navigate(direction)
    }

    fun bind(item: Repo) {
        binding.apply {
            repo = item
            executePendingBindings()
        }
    }
}

class RepoDiffCallBack : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem.html_url == newItem.html_url
    }

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem == newItem
    }

}
