package com.dd.zhumbak.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.EditText
import com.carmabs.ema.android.extra.EmaReceiverModel
import com.carmabs.ema.android.extra.EmaResultModel
import com.carmabs.ema.android.ui.EmaView
import com.carmabs.ema.core.state.EmaExtraData
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.dd.zhumbak.R
import com.dd.zhumbak.base.BaseActivity
import com.dd.zhumbak.model.ToolbarModel
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.item_banner_ad_container.*
import kotlinx.android.synthetic.main.toolbar.*
import org.kodein.di.generic.instance

class MainToolbarsViewActivity : BaseActivity(),
    EmaView<HomeToolbarState, MainToolbarsViewModel, HomeNavigator.Navigation> {
    /**
     * Default variables
     */
    override val inputState: HomeToolbarState? = null
    override var previousState: HomeToolbarState? = null
    override val viewModelSeed: MainToolbarsViewModel by instance()
    override val navigator: HomeNavigator by instance()
    override val navGraph: Int = R.navigation.navigation_ema_home

    /**
     * Customs variables
     */
    lateinit var vm: MainToolbarsViewModel
    lateinit var etSearch: EditText
    private lateinit var adView: AdView
    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var sharedPref: SharedPreferences

    private
    val adSize: AdSize
        get() {
            //code from official Google Admobs
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)
            val density = outMetrics.density
            var adWidthPixels = avAdvertising.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }
            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }

    /**
     * Default functions
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel(this)

        sharedPref = applicationContext.getSharedPreferences(
            getString(R.string.saved_high_score_key),
            Context.MODE_PRIVATE
        )
        showStar()
        Log.i("autolog", "getScoreFromSharedPreferences(): " + getScoreFromSharedPreferences());

        setupMobileAds()
        setupStars()
    }


    override fun onResultReceiverInvokeEvent(emaReceiverModel: EmaReceiverModel) {
    }

    override fun onResultSetEvent(emaResultModel: EmaResultModel) {
    }

    override fun onViewModelInitialized(viewModel: MainToolbarsViewModel) {
        setupToolbar(viewModel)
    }

    override fun provideFixedToolbarTitle(): String? = null

    override fun onStateAlternative(data: EmaExtraData) {
    }

    override fun onSingleEvent(data: EmaExtraData) {
        if (etSearch.text.toString() != data.extraData.toString()) {
            etSearch.setText(data.extraData.toString())
        }
    }

    override fun onStateError(error: Throwable) {
    }

    override fun onStateNormal(data: HomeToolbarState) {
        when (data.step) {
            HomeToolbarState.HomeToolbarStateStep.UPDATE_TOOLBAR -> {
                updateToolbar(data.toolbarModel)
            }

            HomeToolbarState.HomeToolbarStateStep.SHOW_INTERSTITIAL -> {
                showInterstitial()
            }
            HomeToolbarState.HomeToolbarStateStep.SHOW_REWARDED -> {

            }
            HomeToolbarState.HomeToolbarStateStep.SHOW_STARS -> {
                showStar()
            }
        }
    }

    /**
     * Customs functions
     */
    private fun setupStars() {

    }

    private fun showStar() {
        when (getScoreFromSharedPreferences()) {
            5 -> {
                ivStar1.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_black_24dp,
                        null
                    )
                )
                ivStar2.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_black_24dp,
                        null
                    )
                )
                ivStar3.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_black_24dp,
                        null
                    )
                )
                ivStar4.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_black_24dp,
                        null
                    )
                )
                ivStar5.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_black_24dp,
                        null
                    )
                )
            }
            4 -> {
                onAnimateStars(ivStar1)
                ivStar1.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_border_black_24dp,
                        null
                    )
                )
                ivStar2.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_black_24dp,
                        null
                    )
                )
                ivStar3.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_black_24dp,
                        null
                    )
                )
                ivStar4.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_black_24dp,
                        null
                    )
                )
                ivStar5.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_black_24dp,
                        null
                    )
                )
            }
            3 -> {
                ivStar1.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_border_black_24dp,
                        null
                    )
                )

                onAnimateStars(ivStar2)
                ivStar2.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_border_black_24dp,
                        null
                    )
                )
                ivStar3.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_black_24dp,
                        null
                    )
                )
                ivStar4.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_black_24dp,
                        null
                    )
                )
                ivStar5.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_black_24dp,
                        null
                    )
                )
            }
            2 -> {
                ivStar1.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_border_black_24dp,
                        null
                    )
                )
                ivStar2.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_border_black_24dp,
                        null
                    )
                )

                onAnimateStars(ivStar3)
                ivStar3.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_border_black_24dp,
                        null
                    )
                )
                ivStar4.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_black_24dp,
                        null
                    )
                )
                ivStar5.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_black_24dp,
                        null
                    )
                )
            }
            1 -> {
                ivStar1.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_border_black_24dp,
                        null
                    )
                )
                ivStar2.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_border_black_24dp,
                        null
                    )
                )
                ivStar3.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_border_black_24dp,
                        null
                    )
                )
                onAnimateStars(ivStar4)
                ivStar4.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_border_black_24dp,
                        null
                    )
                )
                ivStar5.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_black_24dp,
                        null
                    )
                )
            }
            0 -> {
                onAnimateStars(ivStar1)
                ivStar1.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_border_black_24dp,
                        null
                    )
                )

                onAnimateStars(ivStar2)
                ivStar2.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_border_black_24dp,
                        null
                    )
                )

                onAnimateStars(ivStar3)
                ivStar3.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_border_black_24dp,
                        null
                    )
                )

                onAnimateStars(ivStar4)
                ivStar4.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_border_black_24dp,
                        null
                    )
                )

                onAnimateStars(ivStar5)
                ivStar5.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_star_border_black_24dp,
                        null
                    )
                )
            }
        }

    }

    private fun onAnimateStars(view: View) {
        YoYo.with(Techniques.Flash).duration(1000).repeat(0).playOn(view)
    }

    /**
     * AdMob
     */
    private fun setupMobileAds() {
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this)

        adView = AdView(this)
        avAdvertising.addView(adView)
        loadBanner()

        loadInterstitialAd()

    }


    private fun loadInterstitialAd() {
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = resources.getString(R.string.interstitial_ad_unit_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
                etSearch.clearFocus()
            }
        }
    }


    private fun loadBanner() {
        adView.adUnitId = resources.getString(R.string.banner_ad_unit_id)

        adView.adSize = adSize
        val adRequest = AdRequest
            .Builder()
            .build()
        adView.loadAd(adRequest)
    }


    private fun showInterstitial() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        }
    }

    /**
     * SharedPreferences
     */
    private fun getScoreFromSharedPreferences(): Int {
        return sharedPref.getInt(
            getString(R.string.saved_high_score_key),
            resources.getInteger(R.integer.saved_high_score)
        )
    }


    /**
     * Toolbar
     */

    private fun setupToolbar(viewModel: MainToolbarsViewModel) {
        vm = viewModel


    }

    private fun updateToolbar(data: ToolbarModel) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id == R.id.categoryViewFragment) {
                //Logo pressed
                true -> {
                    ivToolbarLogoOrBack.setImageDrawable(
                        resources.getDrawable(
                            R.drawable.ic_essentials,
                            null
                        )
                    )
                    ivToolbarLogoOrBack.setOnClickListener { }
                }
                //Back pressed
                false -> {
                    ivToolbarLogoOrBack.setImageDrawable(
                        resources.getDrawable(
                            R.drawable.ic_keyboard_arrow_left_black_24dp,
                            null
                        )
                    )
                    ivToolbarLogoOrBack.setOnClickListener {


                        vm.onActionBackClicked()
                    }
                }
            }
        }


        data.toolbarTitle.let {
            tvToolbarTitle.text = data.toolbarTitle
        }

        data.toolbarTitleVisibility.let {
            if (it) {
                tvToolbarTitle.visibility = View.VISIBLE
            } else {
                tvToolbarTitle.visibility = View.GONE
            }
        }

        data.toolbarLogoOrBackVisibility.let {
            if (it) {
                ivToolbarLogoOrBack.visibility = View.VISIBLE
            } else {
                ivToolbarLogoOrBack.visibility = View.GONE
            }
        }

        data.toolbarVisibility.let {
            if (it) {
                showToolbar()
                constraintToolbar.visibility = View.VISIBLE
            } else {
                hideToolbar()
                constraintToolbar.visibility = View.GONE
            }
        }

        data.searchButton?.let { searchButton ->


        }
    }
}