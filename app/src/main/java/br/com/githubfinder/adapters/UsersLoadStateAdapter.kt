package br.com.githubfinder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.githubfinder.databinding.SearchLoadStateFooterViewItemBinding

class UsersLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<UsersLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: UsersLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): UsersLoadStateViewHolder {
        return UsersLoadStateViewHolder.create(parent, retry)
    }
}

class UsersLoadStateViewHolder(
    private val binding: SearchLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.status = loadState
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): UsersLoadStateViewHolder {
            return UsersLoadStateViewHolder(
                SearchLoadStateFooterViewItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                retry
            )
        }
    }
}