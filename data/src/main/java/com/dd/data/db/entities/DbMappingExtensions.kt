package com.dd.data.db.entities

import com.dd.domain.model.ResponseZhumbakModel
import com.dd.domain.model.ZhumbakModel

fun ZhumbakModel.toDomainModel(): ResponseZhumbakModel {
    return ResponseZhumbakModel(
        zhumbakModel = ZhumbakModel(
            id = this.id,
            question = this.question.replace("\\\\n".toRegex(), "\n"),
            answer = this.answer.replace("\\\\n".toRegex(), "\n")
        )
    )
}
