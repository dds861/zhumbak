package com.dd.zhumbak.base

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.carmabs.ema.core.navigator.EmaNavigationState
import com.carmabs.ema.core.state.EmaBaseState
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.dd.zhumbak.R
import com.dd.zhumbak.ui.main.MainToolbarsViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.android.synthetic.main.fragment_category.*
import org.kodein.di.generic.instance

abstract class BaseToolbarsFragment<S : EmaBaseState, VM : BaseToolbarsViewModel<S, NS>, NS : EmaNavigationState>
    : BaseFragment<S, VM, NS>() {
    lateinit var mainToolbarsVm: MainToolbarsViewModel
    private val mainToolbarsViewModelSeed: MainToolbarsViewModel by instance()
    lateinit var rewardedAd: RewardedAd
    lateinit var sharedPref: SharedPreferences

    override fun onInitialized(viewModel: VM) {
        (viewModel as? BaseToolbarsViewModel<*, *>)?.also {
            mainToolbarsVm = addExtraViewModel(mainToolbarsViewModelSeed, this, requireActivity())
            it.mainToolbarsVm = mainToolbarsVm
            onInitializedWithToolbarsManagement(viewModel, mainToolbarsVm)
        } ?: throw RuntimeException("The view model must be inherited from BaseToolbarsViewModel")

        loadRewardedAd()
        activity?.getSharedPreferences(
            getString(R.string.saved_high_score_key), Context.MODE_PRIVATE
        ).let {
            if (it != null) {
                sharedPref = it
            }
        }
    }

    abstract fun onInitializedWithToolbarsManagement(
        viewModel: VM,
        mainToolbarViewModel: MainToolbarsViewModel
    )

    private fun loadRewardedAd() {
        rewardedAd = RewardedAd(requireContext(), resources.getString(R.string.REWARDED_AD_UNIT_ID))
        val adLoadCallback = object : RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                Log.i("autolog", "onRewardedAdLoaded: ");
                btnGenerate.isEnabled = true
                btnGenerate.isClickable = true
                YoYo.with(Techniques.Bounce).duration(1000).repeat(0).playOn(btnGenerate)
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
        val rewardedAd =
            RewardedAd(requireContext(), resources.getString(R.string.REWARDED_AD_UNIT_ID))
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