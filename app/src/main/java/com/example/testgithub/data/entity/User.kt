package com.example.testgithub.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int,
    val albumId: Int,
    val name: String,
    val avatarUrl: String?,
    val thumbnailUrl: String?,
    val posts: List<Post>
): Parcelable
