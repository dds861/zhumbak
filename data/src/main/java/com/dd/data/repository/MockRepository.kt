package com.dd.data.repository


import com.dd.domain.model.RequestZhumbakModel
import com.dd.domain.model.ResponseZhumbakModel
import com.dd.domain.model.ZhumbakModel
import com.dd.domain.repository.Repository


class MockRepository : Repository {


    override suspend fun getZhumbak(requestZhumbakModel: RequestZhumbakModel): ResponseZhumbakModel {
        return ResponseZhumbakModel(
            zhumbakModel = ZhumbakModel(
                question = "Any Question?",
                answer = "Any Answer."
            )
        )
    }
}