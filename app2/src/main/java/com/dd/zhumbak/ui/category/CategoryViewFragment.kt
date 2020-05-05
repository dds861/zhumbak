package com.dd.zhumbak.ui.category

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import com.carmabs.ema.core.constants.STRING_EMPTY
import com.carmabs.ema.core.state.EmaExtraData
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.dd.zhumbak.R
import com.dd.zhumbak.base.BaseToolbarsFragment
import com.dd.zhumbak.ui.main.MainToolbarsViewModel
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import kotlinx.android.synthetic.main.fragment_category.*
import org.kodein.di.generic.instance
import java.net.InetAddress

class CategoryViewFragment :
    BaseToolbarsFragment<CategoryState, CategoryViewModel, CategoryNavigator.Navigation>() {
    /**
     * Default variables
     */
    override val layoutId: Int = R.layout.fragment_category
    override val navigator: CategoryNavigator by instance()
    override val viewModelSeed: CategoryViewModel by instance()


    /**
     * Custom variables
     */
    private lateinit var vm: CategoryViewModel
    private lateinit var data: CategoryState

    /**
     * Default functions
     */
    override fun onInitializedWithToolbarsManagement(
        viewModel: CategoryViewModel,
        mainToolbarViewModel: MainToolbarsViewModel
    ) {
        vm = viewModel
        setupListener()

    }


    override fun onSingleEvent(data: EmaExtraData) {
    }

    override fun onNormal(data: CategoryState) {
        this.data = data
        tvAddress.text = data.zhumbakModel.question
        tvBranch.text = STRING_EMPTY

        onChangeNewZhumbakButtonText()
    }


    override fun onAlternative(data: EmaExtraData) {
    }

    override fun onError(error: Throwable) {
    }

    override fun onSingle(data: EmaExtraData) {
    }

    /**
     * Custom functions
     */

    /**
     * Listeners
     */
    private fun setupListener() {
        btnGenerate.setOnClickListener { onGenerateButtonClick() }

        btnShowAnswer.setOnClickListener { onShowAnswerButtonClick() }

        ivCopy.setOnClickListener { copyToClipboard() }

        ivShare.setOnClickListener { shareText() }

        ivWhatsApp.setOnClickListener { onClickWhatsApp() }
    }



    private fun onChangeNewZhumbakButtonText() {
        if (getScoreFromSharedPreferences() == 0) {
            btnGenerate.text = resources.getString(R.string.watch_ads)
        } else {
            btnGenerate.text = resources.getString(R.string.new_zhumbak)
        }
    }

    private fun onGenerateButtonClick() {

        onAnimateQuestionText(tvAddress)
        val score = getScoreFromSharedPreferences()
        if (score != null) {
            if (score > 0) {
                saveToSharedPreferences(score - 1)
                vm.onActionGenerateClick()
                mainToolbarsVm.onActionUpdateStars()
            } else {
                if (hasInternetConnected()) {
                    showRewarded()
                } else {
                    Toast.makeText(requireContext(), resources.getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show()
                }
            }
        }

    }


    private fun onShowAnswerButtonClick() {
        if (tvBranch.text.isNotEmpty()) {
            tvBranch.text = STRING_EMPTY
            btnShowAnswer.text = resources.getString(R.string.show_answer)
        } else {
            onAnimateAnswerText(tvBranch)
            tvBranch.text = data.zhumbakModel.answer
            btnShowAnswer.text = resources.getString(R.string.hide_answer)
        }
    }


    private fun getScoreFromSharedPreferences(): Int? {
        return sharedPref?.getInt(
            getString(R.string.saved_high_score_key),
            resources.getInteger(R.integer.saved_high_score)
        )
    }

    private fun saveToSharedPreferences(score: Int) {
        sharedPref ?: return
        with(sharedPref?.edit()) {
            this?.putInt(
                resources.getString(R.string.saved_high_score_key),
                score
            )
            this?.apply()
        }
    }


    private fun showRewarded() {
        if (rewardedAd.isLoaded) {
            val activityContext: Activity = requireActivity()
            val adCallback = object : RewardedAdCallback() {
                override fun onRewardedAdOpened() {
                    // Ad opened.
                }

                override fun onRewardedAdClosed() {
                    // Ad closed.
                    rewardedAd = createAndLoadRewardedAd()
                    btnGenerate.isEnabled = false
                    btnGenerate.isClickable = false
                }

                override fun onUserEarnedReward(@NonNull reward: RewardItem) {
                    // User earned reward.
                    saveToSharedPreferences(resources.getInteger(R.integer.saved_high_score))

                    mainToolbarsVm.onActionUpdateStars()
                    vm.onActionGenerateClick()
                }

                override fun onRewardedAdFailedToShow(errorCode: Int) {
                    // Ad failed to display.
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.ads_load_fail),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            rewardedAd.show(activityContext, adCallback)
        } else {
            Log.d("autolog", "The rewarded ad wasn't loaded yet.")
        }
    }

    private fun shareText() {
        onAnimateViewOnClick(ivShare)
        val text = onGetTextToSend()

        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, text)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text)
        requireContext().startActivity(
            Intent.createChooser(
                sharingIntent,
                requireContext().resources.getString(R.string.share_using)
            )
        )
    }

    private fun copyToClipboard() {
        onAnimateViewOnClick(ivCopy)

        val text = onGetTextToSend()

        val clipboard =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("Makal text", text)
        clipboard?.setPrimaryClip(clip)
        Toast.makeText(requireContext(), R.string.TextCopied, Toast.LENGTH_SHORT).show()
    }

    private fun onClickWhatsApp() {
        onAnimateViewOnClick(ivWhatsApp)
        val text = onGetTextToSend()


        val pm: PackageManager = requireContext().packageManager
        try {
            val waIntent = Intent(Intent.ACTION_SEND)
            waIntent.type = "text/plain"
            val info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp")
            waIntent.putExtra(Intent.EXTRA_TEXT, text)
            requireContext().startActivity(Intent.createChooser(waIntent, "Share with"))
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(requireContext(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun onGetTextToSend(): String {
        if (tvBranch.text.isEmpty()) {
            return "${resources.getString(R.string.zhumbak)}: ${data.zhumbakModel.question},\n"
        } else {
            return "${resources.getString(R.string.zhumbak)}: ${data.zhumbakModel.question},\n" +
                    "${resources.getString(R.string.zhauap)}: ${data.zhumbakModel.answer}"
        }
    }

    /**
     * Animations
     */

    private fun onAnimateViewOnClick(view: View) {
        YoYo.with(Techniques.FadeOut).duration(150).repeat(0).playOn(view)
        YoYo.with(Techniques.FadeIn).duration(350).repeat(0).playOn(view)
    }

    private fun onAnimateQuestionText(view: View) {
        YoYo.with(Techniques.FlipInX).duration(1000).repeat(0).playOn(view)
    }

    private fun onAnimateAnswerText(view: View) {
        YoYo.with(Techniques.BounceIn).duration(1000).repeat(0).playOn(view)
    }
}
