package com.dd.conqazaqstan.sqlite.ui.search

import android.app.Activity
import androidx.navigation.NavController
import com.carmabs.ema.core.navigator.EmaNavigationState
import com.dd.conqazaqstan.sqlite.base.BaseNavigator

class SearchNavigator(
        override val navController: NavController,
        private val activity: Activity)
    : BaseNavigator<SearchNavigator.Navigation>() {
    sealed class Navigation : EmaNavigationState {

    }
}