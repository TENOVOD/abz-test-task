package com.abz.testtask.ui.screen.users.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abz.testtask.response.User
import com.abz.testtask.ui.theme.SpacerBetweenCards

@Composable
fun ListOfUsers(users: List<User>,modifier: Modifier=Modifier, isLoading:Boolean,clickOnCard:(User)->Unit, loadUsers:()->Unit) {

    LazyColumn(modifier =  modifier.fillMaxSize()) {
        items (users) {user->
        UserCard(user=user, clickOnCard=clickOnCard)
        Spacer(modifier=modifier.padding(horizontal = 32.dp).padding(start = 50.dp).fillMaxWidth().height(1.dp).background(color = SpacerBetweenCards))
        }
        item{
            LaunchedEffect(users.size) {
                loadUsers()
            }
            IndeterminateCircularIndicator(isLoading=isLoading)
        }
    }
}