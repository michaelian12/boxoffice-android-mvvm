package com.michaelagustian.boxoffice.features.movie

import android.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Created by Michael Agustian on 31/10/18.
 */

object MovieItemBindings {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImage(imageView: SimpleDraweeView, imageUrl: String?) {
        if (imageUrl != null && imageUrl.isNotBlank())
            imageView.setImageURI(imageUrl)
    }
}