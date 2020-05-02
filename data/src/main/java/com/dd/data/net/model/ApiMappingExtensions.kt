package com.dd.data.net.model


import com.dd.domain.model.ResponseZhumbakModel
import com.dd.domain.model.ZhumbakModel


fun ResponseZhumbakApi.toDomainModel(): ResponseZhumbakModel = ResponseZhumbakModel(
    zhumbakModel = ZhumbakModel(
        id = this.id,
        question = this.question,
        answer = this.answer
    )
)


