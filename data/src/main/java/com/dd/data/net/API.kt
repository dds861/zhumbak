package com.dd.data.net

import com.dd.data.net.model.RequestZhumbakApi
import com.dd.data.net.model.ResponseZhumbakApi
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface API {

    @Headers(
            "Content-Type: application/x-www-form-urlencoded",
            "Accept-Charset: utf-8"
    )

    @POST("")
    fun getMakal(@Body request: RequestZhumbakApi): Deferred<ResponseZhumbakApi>
}

