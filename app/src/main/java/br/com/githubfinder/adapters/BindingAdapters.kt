package br.com.githubfinder.adapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import br.com.githubfinder.R
import br.com.githubfinder.vo.enums.Status
import com.squareup.picasso.Picasso

@BindingAdapter("gitApiStatus")
fun ImageView.bindStatus(status: Status?) {
    when (status) {
        Status.LOADING -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.loading_animation)
        }
        Status.ERROR -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_connection_error)
        }
        Status.SUCCESS -> {
            visibility = View.GONE
        }
    }
}

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Picasso.get()
            .load(imageUrl)
            .noFade()
            .placeholder(R.drawable.loading_img)
            .error(R.drawable.ic_broken_image)
            .into(view)
    }
}