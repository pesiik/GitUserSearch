package com.example.viewcore.resources

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import javax.inject.Inject

class ResourcesWrapper @Inject constructor(
    private val resources: Resources
) {

    fun getDrawable(@DrawableRes drawableId: Int): Drawable {
        return requireNotNull(ResourcesCompat.getDrawable(resources, drawableId, null))
    }

    fun getString(@StringRes stringId: Int): String {
        return resources.getString(stringId)
    }
}