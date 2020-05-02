package com.dd.conqazaqstan.sqlite.ui.category

import android.app.Activity
import androidx.navigation.NavController
import com.carmabs.ema.core.navigator.EmaNavigationState
import com.dd.conqazaqstan.sqlite.base.BaseNavigator

class CategoryNavigator(
    override val navController: NavController,
    private val activity: Activity
) : BaseNavigator<CategoryNavigator.Navigation>() {
    sealed class Navigation : EmaNavigationState {

    }

}