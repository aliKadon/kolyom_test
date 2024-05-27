package com.example.kolyom_test.data

import com.example.kolyom_test.model.ResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface CountryApi {
    @GET("v1/CMS/Countries")
    suspend fun getCountries() : Response<ResponseModel>
}