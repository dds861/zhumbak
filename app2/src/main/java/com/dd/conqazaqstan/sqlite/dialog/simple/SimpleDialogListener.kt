package com.dd.conqazaqstan.sqlite.dialog.simple

import com.carmabs.ema.core.dialog.EmaDialogListener

interface SimpleDialogListener : EmaDialogListener {
    fun onCancelClicked()
    fun onConfirmClicked()

}