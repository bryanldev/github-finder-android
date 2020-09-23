package br.com.githubfinder.ui.databinding

import br.com.githubfinder.R
import br.com.githubfinder.data.model.Repo
import br.com.githubfinder.databinding.ListItemRepoBinding
import com.xwray.groupie.databinding.BindableItem

class RepoItem(val repo: Repo): BindableItem<ListItemRepoBinding>() {
    override fun getLayout(): Int = R.layout.list_item_repo

    override fun bind(binding: ListItemRepoBinding, position: Int) {
        binding.repoName.text = repo.name
        binding.repoDescription.text = repo.description
    }
}