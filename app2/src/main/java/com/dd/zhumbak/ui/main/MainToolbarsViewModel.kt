package com.dd.zhumbak.ui.main

import com.carmabs.ema.core.state.EmaExtraData
import com.dd.zhumbak.base.BaseViewModel
import com.dd.zhumbak.model.ToolbarModel

class MainToolbarsViewModel : BaseViewModel<HomeToolbarState, HomeNavigator.Navigation>() {
    /**
     * Default variables
     */
    override val initialViewState: HomeToolbarState =
        HomeToolbarState()

    /**
     * Default functions
     */
    override fun onStartFirstTime(statePreloaded: Boolean) {
    }

    override fun onResultListenerSetup() {
        addOnResultReceived {
            (it.data as? Pair<*, *>)?.also { pair ->
                notifySingleEvent(EmaExtraData(extraData = pair))
            }
        }
    }

    /**
     * Custom functions
     */
    fun onActionUpdateToolbar(
        update: Boolean = true,
        updateToolbar: (ToolbarModel) -> ToolbarModel
    ) {
        checkDataState {
            if (update)
                updateToNormalState {
                    copy(
                        toolbarModel = updateToolbar.invoke(it.toolbarModel),
                        step = HomeToolbarState.HomeToolbarStateStep.UPDATE_TOOLBAR
                    )
                }
            else
                updateDataState {
                    copy(
                        toolbarModel = updateToolbar.invoke(it.toolbarModel),
                        step = HomeToolbarState.HomeToolbarStateStep.UPDATE_TOOLBAR
                    )
                }
        }
    }

    fun onActionSearchViewText(searchViewText: String) {
        notifySingleEvent(EmaExtraData(extraData = searchViewText))
    }

    fun onActionBackClicked() {
        navigate(HomeNavigator.Navigation.Back)
    }


    fun onActionShowInterstitialAd() {
        checkDataState {
            updateToNormalState {
                copy(
                    step = HomeToolbarState.HomeToolbarStateStep.SHOW_INTERSTITIAL
                )
            }
        }
    }

    fun onActionShowRewardedAd() {
        checkDataState {
            updateToNormalState {
                copy(
                    step = HomeToolbarState.HomeToolbarStateStep.SHOW_REWARDED
                )
            }
        }
    }
    fun onActionUpdateStars() {
        checkDataState {
            updateToNormalState {
                copy(
                    step = HomeToolbarState.HomeToolbarStateStep.SHOW_STARS
                )
            }
        }
    }
}