package com.dd.zhumbak.dialog.loading

import android.view.View
import com.carmabs.ema.android.ui.dialog.EmaBaseDialog
import com.dd.zhumbak.R
import kotlinx.android.synthetic.main.dialog_loading.view.*


class LoadingDialog : EmaBaseDialog<LoadingDialogData>() {

    override val layoutId: Int = R.layout.dialog_loading

    override fun setupData(data: LoadingDialogData, view: View) {
        view.tvDialogLoadingTitle.text = data.title
        view.tvDialogLoadingMessage.text = data.message

        isCancelable = false
    }
}