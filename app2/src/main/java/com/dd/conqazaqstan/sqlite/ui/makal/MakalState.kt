package com.dd.conqazaqstan.sqlite.ui.makal

import com.carmabs.ema.core.constants.INT_ZERO
import com.carmabs.ema.core.constants.STRING_EMPTY
import com.carmabs.ema.core.state.EmaBaseState
import com.dd.domain.model.MakalModel

data class MakalState(
        val default: String = STRING_EMPTY,
        val categoryId: Int = INT_ZERO,
        val listMakals: List<MakalModel> = listOf(),
        val categoryTitle: String = STRING_EMPTY
) : EmaBaseState