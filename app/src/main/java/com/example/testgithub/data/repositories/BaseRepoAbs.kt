package com.example.testgithub.data.repositories

import com.example.testgithub.data.network.error.parser.INetworkParserError
import com.example.testgithub.data.network.Result


abstract class BaseRepoAbs (private val networkParserError: INetworkParserError) {
    suspend fun <T: Any> safeApiCall(call: suspend () -> T): Result<T> {

        val result: Result<T> = safeApiResult(call)

        return when(result) {
            is Result.Success ->
                result
            is Result.Error -> {
                Result.Error(networkParserError.transformExceptionHttp(result.exception))
            }
        }
    }

    private suspend fun <T: Any> safeApiResult(call: suspend () -> T): Result<T> {
        return try {
            Result.Success(call.invoke())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}