package com.abz.testtask.ui.screen.users.elements

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abz.testtask.R
import com.abz.testtask.ui.labels.HeadingElementsWithAnimateScale


@Composable
fun NoUsersBlock(isStarted:Boolean){

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        AnimateNoUsersPicture(isStarted)
        HeadingElementsWithAnimateScale(textSrc = R.string.There_are_no_users_yet, isStarted = isStarted)
    }
}

@Composable
private fun AnimateNoUsersPicture(isActive:Boolean){
    val animateCircleScale by animateFloatAsState(if(isActive) 0f else 1f, animationSpec = tween(300))
    val animateMainPersonScale by animateFloatAsState(if(isActive) 0f else 1f, animationSpec = tween(500))
    val animateSecondPersonScale by animateFloatAsState(if(isActive) 0f else 1f, animationSpec = tween(500, delayMillis = 100))

    Box(modifier = Modifier.fillMaxWidth(0.56f).aspectRatio(1f)){

        Box(modifier = Modifier.fillMaxSize()){
            Image(painter = painterResource(R.drawable.ic_grey_circle_with_black_border), contentDescription = null, modifier = Modifier.fillMaxSize().scale(animateCircleScale))
        }
        Box(modifier = Modifier.fillMaxSize().offset(y = -(57).dp)){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart){
                Image(painter = painterResource(R.drawable.ic_blue_person), contentDescription = null, modifier = Modifier.fillMaxSize(0.43f).scale(animateSecondPersonScale))
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd){
                Image(painter = painterResource(R.drawable.ic_blue_person), contentDescription = null, modifier = Modifier.fillMaxSize(0.43f).scale(animateSecondPersonScale))
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
                Image(painter = painterResource(R.drawable.ic_blue_person), contentDescription = null, modifier = Modifier.fillMaxSize(0.5f).scale(animateMainPersonScale))
            }
        }

    }
}