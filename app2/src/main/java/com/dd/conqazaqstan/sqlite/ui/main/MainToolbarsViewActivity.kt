package com.dd.conqazaqstan.sqlite.ui.main

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import com.carmabs.ema.android.extra.EmaReceiverModel
import com.carmabs.ema.android.extra.EmaResultModel
import com.carmabs.ema.android.ui.EmaView
import com.carmabs.ema.core.state.EmaExtraData
import com.dd.conqazaqstan.sqlite.R
import com.dd.conqazaqstan.sqlite.base.BaseActivity
import com.dd.conqazaqstan.sqlite.model.ToolbarModel
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.item_banner_ad_container.*
import kotlinx.android.synthetic.main.toolbar.*
import org.kodein.di.generic.instance

class MainToolbarsViewActivity : BaseActivity(), EmaView<HomeToolbarState, MainToolbarsViewModel, HomeNavigator.Navigation> {
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
    private val adSize: AdSize
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

        setupMobileAds()
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
        }
    }

    /**
     * Customs functions
     */
    private fun setupMobileAds() {
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this)

        adView = AdView(this)
        avAdvertising.addView(adView)
        loadBanner()

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

    private fun showInterstitial() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
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

    private fun setupToolbar(viewModel: MainToolbarsViewModel) {
        vm = viewModel

        ivToolbarSearch.setOnSearchClickListener(View.OnClickListener {
            viewModel.onActionSearchClick()
        })
        etSearch = ivToolbarSearch.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
        etSearch.hint = resources.getString(R.string.hint_search)
        etSearch.setHintTextColor(Color.LTGRAY)
        etSearch.setTextColor(Color.WHITE)
    }

    private fun updateToolbar(data: ToolbarModel) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id == R.id.categoryViewFragment) {
                //Logo pressed
                true -> {
                    ivToolbarLogoOrBack.setImageDrawable(resources.getDrawable(R.drawable.ic_customer_service, null))
                    ivToolbarLogoOrBack.setOnClickListener { }
                }
                //Back pressed
                false -> {
                    ivToolbarLogoOrBack.setImageDrawable(resources.getDrawable(R.drawable.ic_keyboard_arrow_left_black_24dp, null))
                    ivToolbarLogoOrBack.setOnClickListener {
                        //hide/show searchView on Back press
                        ivToolbarSearch.onActionViewCollapsed()

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
            ivToolbarSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchButton.setOnQueryTextFocusChangeListener?.invoke(query.toLowerCase())
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    searchButton.setOnQueryTextFocusChangeListener?.invoke(newText.toLowerCase())
                    return false
                }
            })



            if (searchButton.visibility) {
                ivToolbarSearch.visibility = View.VISIBLE
            } else {
                ivToolbarSearch.visibility = View.GONE
            }
        }
    }
}