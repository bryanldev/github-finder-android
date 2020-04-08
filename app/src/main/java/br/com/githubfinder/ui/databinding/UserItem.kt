package br.com.githubfinder.ui.databinding

import br.com.githubfinder.R
import br.com.githubfinder.data.model.User
import br.com.githubfinder.databinding.CardUserBinding
import com.squareup.picasso.Picasso
import com.xwray.groupie.databinding.BindableItem


class UserItem(val user: User) : BindableItem<CardUserBinding>() {

    override fun bind(binding: CardUserBinding, position: Int) {
        binding.userNameText.text = user.userName
        Picasso
            .get()
            .load(user.avatarUrl)
            .noFade()
            .placeholder(R.drawable.loading_img)
            .error(R.drawable.ic_broken_image)
            .into(binding.avatarImage)

    }

    override fun getLayout(): Int = R.layout.card_user

}