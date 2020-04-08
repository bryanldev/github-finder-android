package br.com.githubfinder.ui.databinding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import br.com.githubfinder.R
import br.com.githubfinder.data.network.GithubApiStatus

@BindingAdapter("gitApiStatus")
fun ImageView.bindStatus(status: GithubApiStatus?) {
    when (status) {
        GithubApiStatus.LOADING -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.loading_animation)
        }
        GithubApiStatus.ERROR -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_connection_error)
        }
        GithubApiStatus.DONE -> {
            visibility = View.GONE
        }
    }
}