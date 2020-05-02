package com.dd.data.repository

import com.dd.data.net.API
import com.dd.data.net.model.toDomainModel
import com.dd.domain.model.RequestZhumbakModel
import com.dd.domain.model.ResponseZhumbakModel
import com.dd.domain.repository.Repository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiLdaRepository() : Repository {

    private lateinit var api: API

    /**-------------------------------------------------------------------------------------------*/
    /**                                                                                           */
    /**                                  RETROFIT SERVICES                                        */
    /**                                                                                           */
    /**-------------------------------------------------------------------------------------------*/

    private var retrofit: Retrofit = createRetrofit()
        set(value) {
            field = value
            api = field.create(API::class.java)
        }

    init {
        retrofit = createRetrofit()
    }


    private fun createRetrofit(token: String? = null): Retrofit {

        return Retrofit.Builder()
                .baseUrl("https://gist.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    /**-------------------------------------------------------------------------------------------*/
    /**                                                                                           */
    /**                                IMPLEMENTATIONS                                            */
    /**                                                                                           */
    /**-------------------------------------------------------------------------------------------*/


    override suspend fun getZhumbak(requestZhumbakModel: RequestZhumbakModel): ResponseZhumbakModel {
        return ResponseZhumbakModel()
    }
}