package com.dd.conqazaqstan.sqlite.ui.category

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carmabs.ema.core.state.EmaExtraData
import com.dd.conqazaqstan.sqlite.R
import com.dd.conqazaqstan.sqlite.base.BaseToolbarsFragment
import com.dd.conqazaqstan.sqlite.ui.main.MainToolbarsViewModel
import kotlinx.android.synthetic.main.fragment_category.*
import org.kodein.di.generic.instance

class CategoryViewFragment : BaseToolbarsFragment<CategoryState, CategoryViewModel, CategoryNavigator.Navigation>() {
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

    /**
     * Default functions
     */
    override fun onInitializedWithToolbarsManagement(viewModel: CategoryViewModel, mainToolbarViewModel: MainToolbarsViewModel) {
        vm = viewModel
        setupRecycler()
    }

    override fun onSingleEvent(data: EmaExtraData) {
    }

    override fun onNormal(data: CategoryState) {
        loadRecyclerViews(data)
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
    private fun setupRecycler() {
        rvCategory.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }

    private fun loadRecyclerViews(data: CategoryState) {
        rvCategory.adapter = data.listCategories.toMutableList().let { it ->
            CategoryAdapter(listItems = it) {
                vm.onActionCategoryClick(it)
            }
        }
    }
}
