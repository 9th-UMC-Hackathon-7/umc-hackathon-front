package com.example.umc_hackathon_9

import com.example.umc_hackathon_9.network.ApiClient
import com.example.umc_hackathon_9.network.ProjectModels
import retrofit2.HttpException
import java.io.IOException

class AuthRepository {

    // 🔐 로그인
    suspend fun login(
        loginId: String,
        password: String
    ): Result<ProjectModels.LoginResponse> {
        return try {
            val response = ApiClient.authApi.login(
                ProjectModels.LoginRequest(
                    loginId = loginId,
                    password = password
                )
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(IllegalStateException("응답 body가 비어있어요."))
                }
            } else {
                // 400, 404, 500 등 HTTP 에러
                Result.failure(HttpException(response))
            }
        } catch (e: IOException) {
            // 네트워크 에러 (인터넷 끊김 등)
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 🧾 회원가입
    suspend fun signUp(
        name: String,
        loginId: String,
        password: String,
        avatar: String,
        gender: String,
        mbti: String,
        intonation: String
    ): Result<ProjectModels.SignUpResponse> {
        return try {
            val response = ApiClient.authApi.signUp(
                ProjectModels.SignUpRequest(
                    name = name,
                    loginId = loginId,
                    password = password,
                    avatar = avatar,
                    gender = gender,
                    mbti = mbti,
                    intonation = intonation
                )
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(IllegalStateException("응답 body가 비어있어요."))
                }
            } else {
                // 400, 409, 500 등 HTTP 에러
                Result.failure(HttpException(response))
            }
        } catch (e: IOException) {
            // 네트워크 에러
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        } as Result<ProjectModels.SignUpResponse>
    }
}
