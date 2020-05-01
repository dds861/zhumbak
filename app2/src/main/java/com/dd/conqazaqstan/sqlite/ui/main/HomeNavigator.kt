package com.dd.conqazaqstan.sqlite.ui.main

import android.app.Activity
import androidx.navigation.NavController
import com.carmabs.ema.android.navigation.EmaNavigator
import com.carmabs.ema.core.navigator.EmaBaseNavigator
import com.carmabs.ema.core.navigator.EmaNavigationState
import com.dd.conqazaqstan.sqlite.R

class HomeNavigator(override val navController: NavController, val activity: Activity) : EmaNavigator<HomeNavigator.Navigation> {
    sealed class Navigation : EmaNavigationState {
        object Back : Navigation() {
            override fun navigateWith(navigator: EmaBaseNavigator<out EmaNavigationState>) {
                val nav = navigator as HomeNavigator
                nav.toBack()
            }
        }

        object Search : Navigation() {
            override fun navigateWith(navigator: EmaBaseNavigator<out EmaNavigationState>) {
                val nav = navigator as HomeNavigator
                nav.toSearch()
            }
        }
    }

    private fun toBack() {
        if (!navigateBack())
            activity.finish()
    }

    private fun toSearch() {
        navigateWithAction(R.id.action_global_searchViewFragment,
                null)
    }
}