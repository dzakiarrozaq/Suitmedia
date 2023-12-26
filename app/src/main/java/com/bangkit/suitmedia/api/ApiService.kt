package com.bangkit.suitmedia.api

import com.bangkit.suitmedia.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {
    @GET("api/users")
    fun getUser(
        @QueryMap parameters: HashMap<String, String>
    ): Call<UserResponse>
}