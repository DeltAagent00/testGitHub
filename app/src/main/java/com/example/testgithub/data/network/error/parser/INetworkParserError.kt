package com.example.testgithub.data.network.error.parser

interface INetworkParserError {
    fun transformExceptionHttp(cause: Throwable): Throwable
}