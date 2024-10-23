package com.abz.testtask.ui.screen.users


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.hilt.navigation.compose.hiltViewModel
import com.abz.testtask.R
import com.abz.testtask.internet.ConnectivityObserver
import com.abz.testtask.response.User
import com.abz.testtask.ui.bottombar.BottomBarScreen
import com.abz.testtask.ui.bottombar.MainButtonBar
import com.abz.testtask.ui.screen.users.elements.ListOfUsers
import com.abz.testtask.ui.screen.users.elements.NoUsersBlock
import com.abz.testtask.ui.screen.users.elements.UserDetailsDialog
import com.abz.testtask.ui.situational.NoInternetConnectionScreen
import com.abz.testtask.ui.theme.BackgroundColor
import com.abz.testtask.ui.topbars.RequestTopBar
import com.abz.testtask.viewmodel.UsersViewModel

@Composable
fun UsersScreen(
    viewModel: UsersViewModel = hiltViewModel()
) {
    val networkStatus by viewModel.networkStatus.collectAsState()
    val usersList by viewModel.users.collectAsState()
    val isLoading by viewModel.isLoading

    // For Starting Animation
    var isStarted by remember {
        mutableStateOf(true)
    }
    var hasInternetConnection by remember {
        mutableStateOf(false)
    }
    var hasShowNoInternetConnectionScreen by remember {
        mutableStateOf(false)
    }

    // Animation
    val showNoInternetConnectionWithAnim by animateFloatAsState(
        if (hasShowNoInternetConnectionScreen) 1f else 0f,
        animationSpec = tween(200)
    )
    val hideMainContentWithAnim by animateFloatAsState(
        if (hasShowNoInternetConnectionScreen) 0f else 1f,
        animationSpec = tween(200)
    )

    val showListWithAnimation by animateFloatAsState(
        if (usersList.isEmpty()) 0f else 1f,
        animationSpec = tween(600)
    )

    //Users details card
    var isActiveUsersDetailsDialog by remember {
        mutableStateOf(false)
    }
    var openedUser by remember {
        mutableStateOf<User?>(null)
    }

    //Starting Animation
    LaunchedEffect(Unit) {
        isStarted = false
    }

    LaunchedEffect(usersList.size) {
        if (usersList.isEmpty()) {
            isStarted = false
        } else {
            isStarted = true
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            isStarted = true
        }
    }


    /*
       Listening networkStatus
       When network unavailable -> show NoInternetScreen
       When network available -> hide NoInternetScreen
    */
    LaunchedEffect(networkStatus) {
        if (networkStatus == ConnectivityObserver.Status.Unavailable) {
            hasInternetConnection = true
            hasShowNoInternetConnectionScreen = true
        } else {
            hasInternetConnection = false
        }
    }


    Box(
        Modifier.fillMaxSize()
    ) {

        //Main Screen
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()

                .alpha(hideMainContentWithAnim),
            topBar = {
                RequestTopBar(textSrc = R.string.Working_with_GET_request)
            },
            bottomBar = {
                MainButtonBar(screenName = BottomBarScreen.USERS_SCREEN)
            }

        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(
                        BackgroundColor
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Show no users with animation when list is empty
                if (usersList.isEmpty()) {
                    NoUsersBlock(isStarted = isStarted)
                }
                //Column with users
                ListOfUsers(
                    usersList,
                    modifier = Modifier.scale(showListWithAnimation),
                    isLoading = isLoading,
                    clickOnCard = {
                        openedUser=it
                        isActiveUsersDetailsDialog=true
                    }
                ) {
                    viewModel.loadUsers()
                }
            }
        }


        /*
            When internet is unavailable -> show this screen
        */
        NoInternetConnectionScreen(
            modifier = Modifier.alpha(showNoInternetConnectionWithAnim),
            isStarted = !hasShowNoInternetConnectionScreen
        ) {
            viewModel.loadUsers()
            if (!hasInternetConnection) {
                hasShowNoInternetConnectionScreen = false
            }
        }
    }
    openedUser?.let {
        UserDetailsDialog(isActive = isActiveUsersDetailsDialog, user = it){
        isActiveUsersDetailsDialog=false
    }
    }


}