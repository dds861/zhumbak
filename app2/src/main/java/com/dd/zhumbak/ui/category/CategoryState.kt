package com.dd.zhumbak.ui.category

import com.carmabs.ema.core.constants.STRING_EMPTY
import com.carmabs.ema.core.state.EmaBaseState
import com.dd.domain.model.ZhumbakModel

data class CategoryState(
    val default: String = STRING_EMPTY,
    val zhumbakModel: ZhumbakModel = ZhumbakModel(),
    val listCategories: List<ZhumbakModel> = listOf()
) : EmaBaseState