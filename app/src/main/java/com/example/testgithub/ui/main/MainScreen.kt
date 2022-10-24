package com.example.testgithub.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.testgithub.data.entity.User
import com.example.testgithub.ui.detail.DetailScreen
import com.example.testgithub.ui.detail.KEY_USER_ARG
import com.example.testgithub.ui.listUsers.ListUsersScreen
import com.example.testgithub.ui.listUsers.ListUsersScreenViewState
import com.example.testgithub.ui.route.Route
import com.google.gson.Gson

@Composable
fun MainScreen(
    viewState: ListUsersScreenViewState,
    gson: Gson
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.MainScreen.value) {
        composable(Route.MainScreen.value) {
            ListUsersScreen(
                navigation = navController,
                viewState  = viewState,
                gson = gson
            )
        }
        composable(
            route = Route.DetailScreen.value.plus("/{$KEY_USER_ARG}"),
            arguments = listOf(
                navArgument(KEY_USER_ARG) {
                    type = NavType.StringType
                }
            )
        ) {
            val userJson = it.arguments?.getString(KEY_USER_ARG)
            val user = try {
                gson.fromJson(userJson, User::class.java)
            } catch (e: Exception) {
                println(e)
                null
            }

            DetailScreen(
                navigation = navController,
                user = user
            )
        }
    }
}

