package com.andreskaminker.iuvohelp.modules

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

interface fabButtonHelpers {
    fun setFabDrawable(@DrawableRes drawable: Int)
    fun setFabColor(@ColorRes color: Int)
    fun setFabClickListener(clickListener: () -> Unit)
}