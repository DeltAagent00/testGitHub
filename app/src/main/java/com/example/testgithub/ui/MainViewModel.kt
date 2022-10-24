package com.example.testgithub.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testgithub.data.entity.User
import com.example.testgithub.data.network.Result
import com.example.testgithub.data.repositories.user.IUserRepo
import com.example.testgithub.ui.listUsers.ListUsersScreenViewState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val userRepo: IUserRepo
): ViewModel() {
    private val _errorMessageFlow = MutableSharedFlow<Throwable>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val errorMessageFlow: SharedFlow<Throwable> = _errorMessageFlow

    private val isFirstLoadingFlow = MutableStateFlow(true)
    private val usersFlow = MutableStateFlow<List<User>>(emptyList())

    val state = combine(
        isFirstLoadingFlow,
        usersFlow
    ) { isFirstLoading, users ->
        ListUsersScreenViewState(
            isFistLoader = isFirstLoading,
            users = users
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ListUsersScreenViewState.Empty
    )

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            val result = userRepo.getUsers()
            when(result) {
                is Result.Error -> showError(result.exception)
                is Result.Success -> usersFlow.value = result.data
            }
            isFirstLoadingFlow.value = false
        }
    }

    private fun showError(error: Throwable) {
        _errorMessageFlow.tryEmit(error)
    }
}