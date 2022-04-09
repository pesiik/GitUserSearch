package com.example.viewcore.ext

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.setImage(url: String, imageStub: Drawable? = null) {
    val requestCreator = Picasso.get().load(url)
    if (imageStub != null) {
        requestCreator.placeholder(imageStub)
    }
    requestCreator.into(this)
}