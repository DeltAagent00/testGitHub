package com.example.testgithub.data.network.response.mappers

import com.example.testgithub.data.entity.Post
import com.example.testgithub.data.network.response.PostResponse

fun PostResponse.toEntity() = Post(
    id = this.id ?: NO_ID,
    userId = this.userId ?: NO_ID,
    title = this.title ?: EMPTY_STRING,
    body = this.body ?: EMPTY_STRING
)