package com.dd.domain.repository

import com.dd.domain.model.RequestZhumbakModel
import com.dd.domain.model.ResponseZhumbakModel


interface Repository {


    suspend fun getZhumbak(requestZhumbakModel: RequestZhumbakModel): ResponseZhumbakModel
}