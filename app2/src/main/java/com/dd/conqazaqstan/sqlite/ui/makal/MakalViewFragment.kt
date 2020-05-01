package com.dd.conqazaqstan.sqlite.ui.makal

import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carmabs.ema.core.constants.STRING_EMPTY
import com.carmabs.ema.core.state.EmaExtraData
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.dd.conqazaqstan.sqlite.R
import com.dd.conqazaqstan.sqlite.base.BaseToolbarsFragment
import com.dd.conqazaqstan.sqlite.ui.main.MainToolbarsViewModel
import kotlinx.android.synthetic.main.fragment_makal.*
import kotlinx.android.synthetic.main.fragment_makal.et_search
import org.kodein.di.generic.instance

class MakalViewFragment
    : BaseToolbarsFragment<MakalState, MakalViewModel, MakalNavigator.Navigation>() {
    /**
     * Default variables
     */
    override val layoutId: Int = R.layout.fragment_makal
    override val navigator: MakalNavigator by instance()
    override val viewModelSeed: MakalViewModel by instance()

    /**
     * Custom variables
     */
    private lateinit var vm: MakalViewModel

    /**
     * Default functions
     */
    override fun onInitializedWithToolbarsManagement(viewModel: MakalViewModel, mainToolbarViewModel: MainToolbarsViewModel) {
        vm = viewModel
        setupRecycler()
    }

    override fun onSingleEvent(data: EmaExtraData) {
    }

    override fun onNormal(data: MakalState) {
        loadRecyclerViews(data)
        setupListeners(data)
    }

    private fun setupListeners(data: MakalState) {
        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                vm.onActionSearch(editable.toString())
            }
        })

        iv_makal_clear_search.setOnClickListener {
            YoYo.with(Techniques.FadeOut).duration(150).repeat(0).playOn(it)
            YoYo.with(Techniques.FadeIn).duration(350).repeat(0).playOn(it)

            et_search.setText(STRING_EMPTY)
        }
    }

    override fun onAlternative(data: EmaExtraData) {
    }

    override fun onError(error: Throwable) {}

    override fun onSingle(data: EmaExtraData) {
    }

    /**
     * Custom functions
     */
    private fun setupRecycler() {
        rvMakals.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }

    private fun loadRecyclerViews(data: MakalState) {
        rvMakals.adapter = data.listMakals.toMutableList().let { it ->
            MakalAdapter(requireContext(), listItems = it) {
                viewModelSeed.onActionItemClicked(it)
            }
        }
    }


}

