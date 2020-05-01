package com.dd.conqazaqstan.sqlite.ui.category

import com.carmabs.ema.core.constants.STRING_EMPTY
import com.carmabs.ema.core.state.EmaBaseState
import com.dd.domain.model.CategoryModel

data class CategoryState(
        val default: String = STRING_EMPTY,
        val listCategories: List<CategoryModel> = listOf()
) : EmaBaseState