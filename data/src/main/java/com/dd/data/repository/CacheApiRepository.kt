package com.dd.data.repository

import com.dd.domain.model.RequestZhumbakModel
import com.dd.domain.model.ResponseZhumbakModel
import com.dd.domain.repository.Repository

class CacheApiRepository(private val repository: Repository) : Repository {


    override suspend fun getZhumbak(requestZhumbakModel: RequestZhumbakModel): ResponseZhumbakModel {
        return repository.getZhumbak(requestZhumbakModel)
    }
}

