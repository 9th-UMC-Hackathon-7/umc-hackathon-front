package com.example.umc_hackathon_9.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

class ProjectApi {

    interface AuthApi {
        @POST("/v1/api/auth/login")
        suspend fun login(
            @Body body: ProjectModels.LoginRequest
        ): Response<ProjectModels.LoginResponse>

        @POST("v1/api/auth/signup")
        suspend fun signUp(
            @Body body: ProjectModels.SignUpRequest
        ): Response<ProjectModels.SignUpResponse>
    }


    interface RoomApi {

        @GET("v1/api/room")
        suspend fun getMainPage(
            @Header("Authorization") authorization: String // "Bearer 액세스토큰"
        ): Response<ProjectModels.MainPageResponse>
    }


}