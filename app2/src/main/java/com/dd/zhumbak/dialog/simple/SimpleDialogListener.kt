package com.dd.zhumbak.dialog.simple

import com.carmabs.ema.core.dialog.EmaDialogListener

interface SimpleDialogListener : EmaDialogListener {
    fun onCancelClicked()
    fun onConfirmClicked()

}