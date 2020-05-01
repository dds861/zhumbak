package com.dd.conqazaqstan.sqlite.ui.search

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carmabs.ema.core.state.EmaExtraData
import com.dd.conqazaqstan.sqlite.R
import com.dd.conqazaqstan.sqlite.base.BaseToolbarsFragment
import com.dd.conqazaqstan.sqlite.ui.main.MainToolbarsViewModel
import com.dd.data.utils.KeyboardUtils
import kotlinx.android.synthetic.main.fragment_search.*
import org.kodein.di.generic.instance

class SearchViewFragment
    : BaseToolbarsFragment<SearchState, SearchViewModel, SearchNavigator.Navigation>() {
    /**
     * Default variables
     */
    override val layoutId: Int = R.layout.fragment_search
    override val navigator: SearchNavigator by instance()
    override val viewModelSeed: SearchViewModel by instance()

    /**
     * Custom variables
     */
    private lateinit var vm: SearchViewModel

    /**
     * Default functions
     */
    override fun onInitializedWithToolbarsManagement(viewModel: SearchViewModel, mainToolbarViewModel: MainToolbarsViewModel) {
        vm = viewModel
        setupRecycler()
    }

    override fun onSingleEvent(data: EmaExtraData) {
    }

    override fun onNormal(data: SearchState) {
        loadRecyclerViews(data)
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
        rvSearch.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }

    private fun loadRecyclerViews(data: SearchState) {
        rvSearch.adapter = data.listMakals.toMutableList().let { mutableList ->
            SearchAdapter(context = requireContext(), listItems = mutableList, adapterType = data.adapterType) { makalModel ->
                when (data.adapterType) {
                    SearchState.AdapterType.HINT -> {
                        makalModel.address?.let { vm.onActionFilterToolbarMakalsByQueryText(it) }
                        activity?.let { KeyboardUtils.hideKeyboard(it, view) }
                    }

                    SearchState.AdapterType.MAKALS -> {
                    }
                }
            }
        }
    }
}

