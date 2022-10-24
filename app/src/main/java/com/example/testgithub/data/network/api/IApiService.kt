package com.example.testgithub.data.network.api

import com.example.testgithub.data.network.response.PostResponse
import com.example.testgithub.data.network.response.UserResponse
import retrofit2.http.GET

interface IApiService {
    companion object {
        private const val PREFIX_URL = "/SharminSirajudeen/test_resources/"
    }

    // Posts
    @GET("${PREFIX_URL}posts")
    suspend fun getPosts(): List<PostResponse>

    // Users
    @GET("${PREFIX_URL}users")
    suspend fun getUsers(): List<UserResponse>
}