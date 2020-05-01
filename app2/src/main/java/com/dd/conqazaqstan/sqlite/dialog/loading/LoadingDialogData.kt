package com.dd.conqazaqstan.sqlite.dialog.loading

import com.carmabs.ema.core.dialog.EmaDialogData

data class LoadingDialogData(
        val title: String = "",
        val message: String = "",
        override val proportionWidth: Float? = 7/10f,
        override val proportionHeight: Float? = null) : EmaDialogData