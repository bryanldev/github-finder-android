package br.com.githubfinder.ui.databinding

import br.com.githubfinder.R
import br.com.githubfinder.data.model.Repo
import br.com.githubfinder.databinding.CardRepoBinding
import com.xwray.groupie.databinding.BindableItem

class RepoItem(val repo: Repo): BindableItem<CardRepoBinding>() {
    override fun getLayout(): Int = R.layout.card_repo

    override fun bind(binding: CardRepoBinding, position: Int) {
        binding.repoName.text = repo.name
        binding.repoDescription.text = repo.description
    }
}