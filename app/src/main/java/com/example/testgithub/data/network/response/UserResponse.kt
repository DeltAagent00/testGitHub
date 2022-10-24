package com.example.testgithub.data.network.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("userId")
    val userId: Int? = null,
    @SerializedName("albumId")
    val albumId: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("url")
    val avatarUrl: String? = null,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String? = null
)
