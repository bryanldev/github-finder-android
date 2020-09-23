package br.com.githubfinder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.githubfinder.data.model.User
import br.com.githubfinder.databinding.ListItemUserBinding
import br.com.githubfinder.ui.search.SearchFragmentDirections

class UserAdapter : ListAdapter<User, RecyclerView.ViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserViewHolder(
            ListItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = getItem(position)
        (holder as UserViewHolder).bind(user)
    }
}

class UserViewHolder(
    private val binding: ListItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.setClickListener {
            binding.user?.let { user ->
                navigateToUserRepo(user, it)
            }
        }
    }

    private fun navigateToUserRepo(
        user: User,
        view: View
    ) {
        val direction = SearchFragmentDirections.actionSearchFragmentToRepositoryFragment(
            user
        )
        view.findNavController().navigate(direction)
    }

    fun bind(item: User) {
        binding.apply {
            user = item
            executePendingBindings()
        }
    }
}

class UserDiffCallBack : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.userName == newItem.userName
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

}