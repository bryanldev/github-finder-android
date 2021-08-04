package br.com.githubfinder.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.githubfinder.data.model.Repo
import br.com.githubfinder.databinding.ListItemRepoBinding

class RepoAdapter : PagingDataAdapter<Repo, RecyclerView.ViewHolder>(RepoDiffCallBack()) {
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
            binding.repo?.let { repo ->
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(repo.html_url))
                it.context.startActivity(browserIntent)
            }
        }
    }

    fun bind(item: Repo?) {
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
