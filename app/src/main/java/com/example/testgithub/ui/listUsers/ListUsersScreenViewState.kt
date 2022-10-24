package com.example.testgithub.ui.listUsers

import com.example.testgithub.data.entity.User

data class ListUsersScreenViewState(
    val isFistLoader: Boolean = false,
    val users: List<User> = emptyList()
) {
    companion object {
        val Empty = ListUsersScreenViewState()
    }
}