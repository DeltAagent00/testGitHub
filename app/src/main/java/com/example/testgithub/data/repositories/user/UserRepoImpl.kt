package com.example.testgithub.data.repositories.user

import com.example.testgithub.data.entity.Post
import com.example.testgithub.data.entity.User
import com.example.testgithub.data.network.Result
import com.example.testgithub.data.network.api.IApiService
import com.example.testgithub.data.network.error.NetworkNotAvailableException
import com.example.testgithub.data.network.error.parser.INetworkParserError
import com.example.testgithub.data.network.response.mappers.toEntity
import com.example.testgithub.data.network.utils.INetworkUtils
import com.example.testgithub.data.repositories.BaseRepoAbs
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class UserRepoImpl(
    private val api: IApiService,
    private val networkUtils: INetworkUtils,
    networkParserError: INetworkParserError
): BaseRepoAbs(networkParserError), IUserRepo {
    override suspend fun getUsers(): Result<List<User>> {
        if (networkUtils.isInternetAvailable()) {
            return coroutineScope {
                val users = async { safeApiCall { api.getUsers() } }
                val posts = async { safeApiCall { api.getPosts() } }

                val resultUser = users.await()
                val resultPosts = posts.await()

                val groupingPosts = when(resultPosts) {
                    is Result.Error -> emptyMap()
                    is Result.Success -> {
                        val mapResult = HashMap<Int, HashSet<Post>>()

                        resultPosts.data
                            .filter { it.userId != null }
                            .forEach {
                                val post: Post = it.toEntity()

                                if (mapResult.containsKey(post.userId)) {
                                    mapResult[post.userId]?.add(post)
                                } else {
                                    val listPost = HashSet<Post>()
                                    listPost.add(post)

                                    mapResult[post.userId] = listPost
                                }
                            }
                        mapResult
                    }
                }

               when(resultUser) {
                    is Result.Error -> resultUser
                    is Result.Success -> {
                        Result.Success(
                            resultUser.data.map {
                                it.toEntity(groupingPosts[it.userId]?.toList() ?: emptyList())
                            }
                        )
                    }
                }
            }
        } else {
            return Result.Error(NetworkNotAvailableException())
        }
    }
}