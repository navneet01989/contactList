package com.e.app.api

import com.e.app.models.Model
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GetData {
    companion object {
        val BASE_URL = "https://reqres.in"
    }
    @Headers("Content-Type: application/json")
    @GET("/api/users/")
    fun getContacts(@Query("page") page: String) : Call<Model.RetroCrypto>
}