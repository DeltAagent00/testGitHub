package com.example.testgithub.data.repositories.user

import com.example.testgithub.data.entity.User
import com.example.testgithub.data.network.Result

interface IUserRepo {
    suspend fun getUsers(): Result<List<User>>
}