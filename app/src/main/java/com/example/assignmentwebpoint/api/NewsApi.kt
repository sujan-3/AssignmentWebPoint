package com.example.assignmentwebpoint.api

import android.util.ArrayMap
import com.example.assignmentwebpoint.news.NewsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by Sujan Rai on 11/05/2023.
 * 
 *
 *
 */
interface NewsApi {

    @GET("/api/v1/search")
    suspend fun listNews(@QueryMap requestParams: ArrayMap<String, Any>): NewsResponse

    @GET("/api/v1/search")
    suspend fun queryNews(@QueryMap query: ArrayMap<String, String>): Response<NewsResponse>

}