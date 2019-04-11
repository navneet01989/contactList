package com.e.app.api

import com.e.app.RetroCrypto
import io.reactivex.Observable
import retrofit2.http.GET

interface GetData {
    @GET("/api/users")
    fun getContacts() : Observable<List<RetroCrypto>>
}