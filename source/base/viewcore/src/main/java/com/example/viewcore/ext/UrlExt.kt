package com.example.viewcore.ext

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlin.coroutines.suspendCoroutine

suspend fun String.getBitmap() = suspendCoroutine<Bitmap> { continuation ->
    val target = object : Target {
        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            bitmap?.let { resultBitmap ->
                continuation.resumeWith(Result.success(resultBitmap))
            }
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            val resultException = e ?: IllegalStateException("Cannot get an exception")
            continuation.resumeWith(Result.failure(resultException))
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
    }
    Picasso.get().load(this).into(target)
}