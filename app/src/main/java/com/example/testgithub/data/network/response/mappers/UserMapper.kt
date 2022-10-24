package com.example.testgithub.data.network.response.mappers

import com.example.testgithub.data.entity.Post
import com.example.testgithub.data.entity.User
import com.example.testgithub.data.network.response.UserResponse

fun UserResponse.toEntity(posts: List<Post>) = User(
    id = this.userId ?: NO_ID,
    albumId = this.albumId ?: NO_ID,
    name = this.name ?: EMPTY_STRING,
    avatarUrl = this.avatarUrl,
    thumbnailUrl = this.thumbnailUrl,
    posts = posts
)