package com.dd.conqazaqstan.sqlite.dialog.loading

import androidx.fragment.app.FragmentManager
import com.carmabs.ema.android.ui.dialog.EmaBaseDialog
import com.carmabs.ema.android.ui.dialog.EmaBaseDialogProvider


class LoadingDialogProvider constructor(fragmentManager: FragmentManager) : EmaBaseDialogProvider(fragmentManager)
{
    override fun generateDialog(): EmaBaseDialog<*> = LoadingDialog()
}