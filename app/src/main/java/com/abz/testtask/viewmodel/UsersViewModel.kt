package com.abz.testtask.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abz.testtask.API.UsersApi
import com.abz.testtask.internet.ConnectivityObserver
import com.abz.testtask.response.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersApi: UsersApi,
    private val connectivityObserver: ConnectivityObserver

):ViewModel() {
    val networkStatus = connectivityObserver.status
    private val  _users:MutableStateFlow<List<User>> = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> get() = _users
    var isLoading = mutableStateOf(false)
    private var currentPage = 1
    private var totalPage = 0
    init {
        loadUsers()
        connectivityObserver.start()
    }

    override fun onCleared() {
        super.onCleared()
        connectivityObserver.stop()
    }

    fun loadUsers(){
        if(currentPage<=totalPage||totalPage==0){
            viewModelScope.launch {
                while (networkStatus.value==ConnectivityObserver.Status.Undefined){
                    delay(50)
                }
                if(networkStatus.value == ConnectivityObserver.Status.Available){
                    isLoading.value=true
                    val response = usersApi.fetchUsers(page = currentPage)
                    println(response.users)
                    if(!_users.value.contains(response.users[0])){
                        totalPage = response.totalPages
                        _users.value += response.users
                        currentPage++
                    }
                    isLoading.value=false
                }
            }
        }

    }

}