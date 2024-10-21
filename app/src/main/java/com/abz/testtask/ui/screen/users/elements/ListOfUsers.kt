package com.abz.testtask.ui.screen.users.elements

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.abz.testtask.response.User

@Composable
fun ListOfUsers(users: List<User>,modifier: Modifier=Modifier, isLoading:Boolean, loadUsers:()->Unit) {
    LazyColumn(modifier =  modifier.fillMaxSize()) {
        items (users) {user->
        UserCard(user=user)
        }
        item{
            LaunchedEffect(users.size) {
                loadUsers()
            }
            IndeterminateCircularIndicator(isLoading=isLoading)
        }
    }
}