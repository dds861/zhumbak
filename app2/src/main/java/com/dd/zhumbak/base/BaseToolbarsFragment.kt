package com.dd.zhumbak.base

import android.util.Log
import com.carmabs.ema.core.navigator.EmaNavigationState
import com.carmabs.ema.core.state.EmaBaseState
import com.dd.zhumbak.R
import com.dd.zhumbak.ui.main.MainToolbarsViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import org.kodein.di.generic.instance

abstract class BaseToolbarsFragment<S : EmaBaseState, VM : BaseToolbarsViewModel<S, NS>, NS : EmaNavigationState>
    : BaseFragment<S, VM, NS>() {
    lateinit var mainToolbarsVm: MainToolbarsViewModel
    private val mainToolbarsViewModelSeed: MainToolbarsViewModel by instance()
    lateinit var rewardedAd: RewardedAd

    override fun onInitialized(viewModel: VM) {
        (viewModel as? BaseToolbarsViewModel<*, *>)?.also {
            mainToolbarsVm = addExtraViewModel(mainToolbarsViewModelSeed, this, requireActivity())
            it.mainToolbarsVm = mainToolbarsVm
            onInitializedWithToolbarsManagement(viewModel, mainToolbarsVm)
        } ?: throw RuntimeException("The view model must be inherited from BaseToolbarsViewModel")

        loadRewardedAd()
    }

    abstract fun onInitializedWithToolbarsManagement(viewModel: VM, mainToolbarViewModel: MainToolbarsViewModel)

    private fun loadRewardedAd() {
        rewardedAd = RewardedAd(requireContext(), resources.getString(R.string.rewarded_ad_unit_id))
        val adLoadCallback = object : RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                Log.i("autolog", "onRewardedAdLoaded: ");
                // Ad successfully loaded.
            }

            override fun onRewardedAdFailedToLoad(errorCode: Int) {
                Log.i("autolog", "onRewardedAdFailedToLoad: ");
                // Ad failed to load.
            }
        }
        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)

    }

     fun createAndLoadRewardedAd(): RewardedAd {
        val rewardedAd = RewardedAd(requireContext(), resources.getString(R.string.rewarded_ad_unit_id))
        val adLoadCallback = object : RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            override fun onRewardedAdFailedToLoad(errorCode: Int) {
                // Ad failed to load.
            }
        }
        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
        return rewardedAd
    }
}