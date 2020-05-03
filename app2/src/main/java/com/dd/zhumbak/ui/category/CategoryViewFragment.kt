package com.dd.zhumbak.ui.category

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
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

    private fun setupListener() {
        btnGenerate.setOnClickListener {


            showRewarded()
        }

        btnShowAnswer.setOnClickListener {
            tvBranch.text = data.zhumbakModel.answer
        }

        ivCopy.setOnClickListener {
            YoYo.with(Techniques.FadeOut).duration(150).repeat(0).playOn(ivCopy)
            YoYo.with(Techniques.FadeIn).duration(350).repeat(0).playOn(ivCopy)
            copyToClipboard(
                "${resources.getString(R.string.zhumbak)}: ${data.zhumbakModel.question},\n" +
                        "${resources.getString(R.string.zhauap)}: ${data.zhumbakModel.answer}"
            )
        }

        ivShare.setOnClickListener {
            YoYo.with(Techniques.FadeOut).duration(150).repeat(0).playOn(ivShare)
            YoYo.with(Techniques.FadeIn).duration(350).repeat(0).playOn(ivShare)
            shareText(
                "${resources.getString(R.string.zhumbak)}: ${data.zhumbakModel.question},\n" +
                        "${resources.getString(R.string.zhauap)}: ${data.zhumbakModel.answer}"
            )
        }

        ivWhatsApp.setOnClickListener {
            YoYo.with(Techniques.FadeOut).duration(150).repeat(0).playOn(ivWhatsApp)
            YoYo.with(Techniques.FadeIn).duration(350).repeat(0).playOn(ivWhatsApp)

            onClickWhatsApp(
                "${resources.getString(R.string.zhumbak)}: ${data.zhumbakModel.question},\n" +
                        "${resources.getString(R.string.zhauap)}: ${data.zhumbakModel.answer}"
            )
        }
    }

    private fun showRewarded() {
        if (rewardedAd.isLoaded) {
            val activityContext: Activity = requireActivity()
            val adCallback = object : RewardedAdCallback() {
                override fun onRewardedAdOpened() {
                    Log.i("autolog", "onRewardedAdOpened: ");
                    // Ad opened.
                }

                override fun onRewardedAdClosed() {
                    Log.i("autolog", "onRewardedAdClosed: ");
                    // Ad closed.
                    rewardedAd = createAndLoadRewardedAd()
                }

                override fun onUserEarnedReward(@NonNull reward: RewardItem) {
                    Log.i("autolog", "onUserEarnedReward: ");
                    // User earned reward.
                    vm.onActionGenerateClick()
                }

                override fun onRewardedAdFailedToShow(errorCode: Int) {
                    Log.i("autolog", "onRewardedAdFailedToShow: ");
                    // Ad failed to display.
                }
            }
            rewardedAd.show(activityContext, adCallback)
        } else {
            Log.d("autolog", "The rewarded ad wasn't loaded yet.")
        }
    }

    private fun shareText(text: String) {
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

    private fun copyToClipboard(text: String) {
        val clipboard =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("Makal text", text)
        clipboard?.setPrimaryClip(clip)
        Toast.makeText(requireContext(), R.string.TextCopied, Toast.LENGTH_SHORT).show()
    }

    private fun onClickWhatsApp(text: String) {
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

}
