package com.example.testgithub.data.network.error.parser

import com.example.testgithub.data.network.error.ServerException
import com.example.testgithub.data.network.error.ServerNotAuthException
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException
import java.lang.Exception

class NetworkParserErrorImpl(private val retrofit: Retrofit): INetworkParserError {
    override fun transformExceptionHttp(cause: Throwable): Throwable {
        if (cause is HttpException) {
            val statusCode = cause.code()
            val responseBody = cause.response()?.errorBody()

            responseBody?.let {
                return when (statusCode) {
                    401 -> ServerNotAuthException()
                    400, 403, 404, 409, 422, 500, 503 -> {
                        getServerException(responseBody)
                    }
                    else -> cause
                }
            }
        }
        return cause
    }

    private fun getServerException(responseBody: ResponseBody): ServerException {
        val errorResponse = getErrorBody(responseBody)

        return if (errorResponse != null) {
            try {
                ServerException(
                    error = errorResponse.error,
                    errorDescription = errorResponse.error_description
                )
            } catch (e: Exception) {
                ServerException()
            }
        } else {
            ServerException()
        }
    }

    private fun getErrorBody(responseBody: ResponseBody): ErrorResponse? {
        return try {
            val errorConverter = retrofit.responseBodyConverter<ErrorResponse>(
                ErrorResponse::class.java, arrayOfNulls<Annotation>(0))
            try {
                errorConverter.convert(responseBody)
            } catch (exception: IOException) {
                null
            }
        } catch (exception: Exception) {
            null
        }
    }
}