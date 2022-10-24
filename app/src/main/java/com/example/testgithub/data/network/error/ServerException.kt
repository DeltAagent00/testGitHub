package com.example.testgithub.data.network.error

import java.lang.RuntimeException

class ServerException(
    val error: String? = null,
    val errorDescription: String? = null
): RuntimeException(error)