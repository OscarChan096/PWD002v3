package com.oskar.pwd002v2.retrofit

import com.oskar.pwd002v2.obj.dataPass
import com.oskar.pwd002v2.obj.typicode
import kotlinx.coroutines.flow.StateFlow
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("title/{title}")
    suspend fun getPWDbyId(@Path("title") title: String): List<dataPass>
}