package com.example.umc_hackathon_9

import com.example.umc_hackathon_9.network.ApiClient
import com.example.umc_hackathon_9.network.ProjectModels
import retrofit2.HttpException
import java.io.IOException

class RoomRepository {

    suspend fun getMainPage(accessToken: String): Result<ProjectModels.MainPageResponse> {
        return try {
            val response = ApiClient.roomApi.getMainPage(
                authorization = "Bearer $accessToken"
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(IllegalStateException("응답 body가 비어있어요."))
                }
            } else {
                Result.failure(HttpException(response))
            }

        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}