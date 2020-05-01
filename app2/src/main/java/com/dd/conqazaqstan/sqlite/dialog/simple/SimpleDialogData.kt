package com.dd.conqazaqstan.sqlite.dialog.simple

import android.graphics.drawable.Drawable
import com.carmabs.ema.core.dialog.EmaDialogData


data class SimpleDialogData(
        val title: String = "",
        val message: String = "",
        val accept: String = "",
        val cancel: String = "",
        val showCross: Boolean = true,
        val image: Drawable? = null,
        override val proportionWidth: Float? = 7/10f,
        override val proportionHeight: Float? = null) : EmaDialogData