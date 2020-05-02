package com.dd.domain.repository

import com.dd.domain.model.ResponseZhumbakModel

interface LocalStorageRepository {

    fun getRandomZhumbak(): ResponseZhumbakModel
}