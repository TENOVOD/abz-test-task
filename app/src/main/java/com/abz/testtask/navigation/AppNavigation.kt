package com.abz.testtask.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abz.testtask.ui.screen.signup.SignUpScreen
import com.abz.testtask.ui.screen.users.UsersScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(
            modifier = Modifier.background(Color.White),
            navController = navController,
            startDestination = NavigationScreen.USERS_SCREEN.name
        ) {

            composable(
                route = NavigationScreen.USERS_SCREEN.name,
                enterTransition = {
                    when (initialState.destination.route) {
                        NavigationScreen.SIGN_UP_SCREEN.name->
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(800)
                            )
                        else->null
                    }},
                exitTransition = {
                    when (targetState.destination.route) {
                        NavigationScreen.SIGN_UP_SCREEN.name ->
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(800)
                            )

                        else -> null
                    }
                }
            ) {
                UsersScreen()
            }
            composable(
                route = NavigationScreen.SIGN_UP_SCREEN.name,
                enterTransition = {
                    when (initialState.destination.route) {
                        NavigationScreen.USERS_SCREEN.name->
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(800)
                            )
                        else->null
                    }},
                exitTransition = {
                    when (targetState.destination.route) {
                        NavigationScreen.USERS_SCREEN.name ->
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(800)
                            )

                        else -> null
                    }
                }
            ) {
                SignUpScreen()
            }

        }
    }
}


enum class NavigationScreen {
    USERS_SCREEN,
    SIGN_UP_SCREEN
}