package com.dd.zhumbak.ui.category

import android.app.Activity
import androidx.navigation.NavController
import com.carmabs.ema.core.navigator.EmaNavigationState
import com.dd.zhumbak.base.BaseNavigator

class CategoryNavigator(
    override val navController: NavController,
    private val activity: Activity
) : BaseNavigator<CategoryNavigator.Navigation>() {
    sealed class Navigation : EmaNavigationState {

    }

}