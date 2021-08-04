package br.com.githubfinder.adapters

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import br.com.githubfinder.R
import br.com.githubfinder.util.brazilDateFormat
import com.squareup.picasso.Picasso
import java.util.*

@BindingAdapter("visibilityByLoadingStatus")
fun setVisibility(view: View, status: LoadState) {
    when (view) {
        is RecyclerView -> view.isVisible = status is LoadState.NotLoading
        is ProgressBar -> view.isVisible = status is LoadState.Loading
        is Button -> view.isVisible = status is LoadState.Error
        is TextView -> view.isVisible = status is LoadState.Error
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

@BindingAdapter("DateText")
fun bindDateText(view: TextView, date: Date?) {
    if (date != null) {
        view.text = date.toString().brazilDateFormat()
    }
}