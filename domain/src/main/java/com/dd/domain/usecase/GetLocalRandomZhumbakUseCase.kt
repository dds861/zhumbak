package com.dd.domain.usecase

import com.carmabs.ema.core.usecase.EmaUseCase
import com.dd.domain.model.ResponseZhumbakModel
import com.dd.domain.repository.LocalStorageRepository

class GetLocalRandomZhumbakUseCase(private val repository: LocalStorageRepository)
    : EmaUseCase<Unit, ResponseZhumbakModel>() {
    override suspend fun useCaseFunction(input: Unit): ResponseZhumbakModel {
        return repository.getRandomZhumbak()
    }
}