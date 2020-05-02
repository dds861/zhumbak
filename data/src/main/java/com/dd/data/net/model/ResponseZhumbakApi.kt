package com.dd.data.net.model

import com.carmabs.ema.core.constants.INT_ZERO
import com.carmabs.ema.core.constants.STRING_EMPTY

data class ResponseZhumbakApi(
    val id: Int = INT_ZERO,
    val question: String = STRING_EMPTY,
    val answer: String = STRING_EMPTY
)