package com.abhi.inc.orufy_interview_assignment

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

data class UploadHistoryRequest(
    val history: List<URLHistory>,
    val deviceId: String = "android_device",
    val timestamp: String = java.text.SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        java.util.Locale.getDefault()
    ).format(java.util.Date())
)

data class UploadHistoryResponse(
    val status: String? = null,
    val message: String? = null,
    val count: Int? = null
)

interface ApiService {
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @POST("/upload-history")
    suspend fun uploadHistory(@Body request: UploadHistoryRequest): Response<UploadHistoryResponse>
}

object RetrofitClient {
    private const val BASE_URL = "https://orufy-history.free.beeceptor.com"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}