package com.dd.domain.model

import com.carmabs.ema.core.constants.INT_ZERO
import com.carmabs.ema.core.constants.STRING_EMPTY
import com.carmabs.ema.core.state.EmaModel

data class ResponseZhumbakModel(
    val zhumbakModel: ZhumbakModel = ZhumbakModel()
)

data class ZhumbakModel(
    val id: Int = INT_ZERO,
    val question: String = STRING_EMPTY,
    val answer: String = STRING_EMPTY
) : EmaModel
