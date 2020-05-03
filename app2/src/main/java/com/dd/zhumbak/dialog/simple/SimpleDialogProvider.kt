package com.dd.zhumbak.dialog.simple

import androidx.fragment.app.FragmentManager
import com.carmabs.ema.android.ui.dialog.EmaBaseDialog
import com.carmabs.ema.android.ui.dialog.EmaBaseDialogProvider


class SimpleDialogProvider constructor(fragmentManager: FragmentManager) : EmaBaseDialogProvider(fragmentManager) {
    override fun generateDialog(): EmaBaseDialog<*> =
        SimpleDialog()
}