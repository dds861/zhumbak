package com.dd.conqazaqstan.sqlite.ui.search

import com.carmabs.ema.core.constants.INT_ZERO
import com.carmabs.ema.core.constants.STRING_EMPTY
import com.carmabs.ema.core.state.EmaBaseState
import com.dd.domain.model.MakalModel

data class SearchState(
        val default: String = STRING_EMPTY,
        val categoryId: Int = INT_ZERO,
        val listMakals: List<MakalModel> = listOf(),
        val categoryTitle: String = STRING_EMPTY,
        val adapterType: AdapterType = AdapterType.HINT
) : EmaBaseState {
    enum class AdapterType {
        HINT,
        MAKALS,
    }
}