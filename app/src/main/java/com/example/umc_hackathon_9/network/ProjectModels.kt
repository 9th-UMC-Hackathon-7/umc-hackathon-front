package com.example.umc_hackathon_9.network

class ProjectModels {

    data class LoginRequest(
        val loginId: String,
        val password: String
    )

    data class LoginSuccess(
        val accessToken: String,
        val refreshToken: String,
        val userId: Int,
        val createdAt: String,
        val updatedAt: String
    )

    data class LoginError(
        val errorCode: String,
        val reason: String,
        val data: Any?
    )

    data class LoginResponse(
        val resultType: String,
        val success: LoginSuccess?,
        val error: LoginError?
    )

    data class SignUpRequest(
        val name: String,
        val loginId: String,
        val password: String,
        val avatar: String,
        val gender: String,
        val mbti: String,
        val intonation: String
    )

    data class LoginErrorResponse(
        val errorCode: String,
        val reason: String,
        val data: Any?
    )

    data class SignUpResponse(
        val resultType: String,
        val success: SignUpSuccessResponse?,
        val error: LoginErrorResponse?      // 에러 구조가 같아서 재사용
    )

    data class SignUpSuccessResponse(
        val userId: Long,
        val email: String,
        val name: String,
        val avatar: String,
        val mbti: String,
        val gender: String,
        val intonation: String
    )

    data class MainPageResponse(
        val resultType: String,
        val success: MainPageSuccessResponse?,
        val error: LoginErrorResponse?   // 로그인에서 쓰던 에러 구조 재사용 가능
    )

    data class MainPageSuccessResponse(
        val name: String,
        val rooms: List<RoomItemResponse>
    )

    data class RoomItemResponse(
        val id: Long,
        val title: String,
        val body: String,
        val gauge: Int,
        val reliefGauge: Int
    )

}