package com.abz.testtask.ui.situational.signupresult

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abz.testtask.R


@Composable
fun SignUpFailedAnimation(isStarted: Boolean){
    val animateMoveElementsFromRightSide by animateDpAsState(
        if (isStarted) 1400.dp else 0.dp,
        animationSpec = tween(800)
    )
    val animateMoveElementsFromLeftSide by animateDpAsState(
        if (isStarted) -(1400).dp  else 0.dp ,
        animationSpec = tween(800)
    )
    val animateMoveElementsFromRightSideWithDelay by animateDpAsState(
        if (isStarted) 1400.dp  else 0.dp ,
        animationSpec = tween(800,50)
    )
    val animationSuccesIconScale by animateFloatAsState(if(isStarted)0f else 1f, animationSpec = tween(1000, delayMillis = 400))

    Box(modifier = Modifier
        .fillMaxWidth(0.56f)
        .aspectRatio(1f)
    ){
        Box(modifier = Modifier.fillMaxSize().offset(x=animateMoveElementsFromLeftSide), contentAlignment = Alignment.Center){
            Image(painter = painterResource(R.drawable.ic_blue_circle), contentDescription = null, modifier = Modifier.fillMaxSize())
        }
        Box(modifier = Modifier.fillMaxSize().offset(x=animateMoveElementsFromRightSideWithDelay).padding(start = 10.dp), contentAlignment = Alignment.CenterStart){
            Image(painter = painterResource(R.drawable.ic_arrow_right), contentDescription = null, modifier = Modifier.fillMaxSize(0.35f).rotate(180f))
        }
        Box(modifier = Modifier.fillMaxSize().offset(x=animateMoveElementsFromRightSide), contentAlignment = Alignment.Center){
            Image(painter = painterResource(R.drawable.ic_failed_form_with_2_arrows), contentDescription = null, modifier = Modifier.fillMaxSize(0.82f))
        }
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.ic_denied),
                modifier = Modifier.scale(animationSuccesIconScale).fillMaxSize(0.35f),
                contentDescription = null,

                )
        }
    }

}


@Composable
fun SignUpSuccessAnimation(isStarted: Boolean){
    val animateMoveElementsFromRightSide by animateDpAsState(
        if (isStarted) 1400.dp else 0.dp,
        animationSpec = tween(800)
    )
    val animateMoveElementsFromLeftSide by animateDpAsState(
        if (isStarted) -(1400).dp  else 0.dp ,
        animationSpec = tween(800)
    )
    val animateMoveElementsFromLeftSideWithDelay by animateDpAsState(
        if (isStarted) -(1400).dp  else 0.dp ,
        animationSpec = tween(800,50)
    )
    val animationSuccesIconScale by animateFloatAsState(if(isStarted)0f else 1f, animationSpec = tween(1000, delayMillis = 400))

    Box(modifier = Modifier
        .fillMaxWidth(0.56f)
        .aspectRatio(1f)
    ){
        Box(modifier = Modifier.fillMaxSize().offset(x=animateMoveElementsFromRightSide), contentAlignment = Alignment.Center){
            Image(painter = painterResource(R.drawable.ic_blue_circle), contentDescription = null, modifier = Modifier.fillMaxSize())
        }
        Box(modifier = Modifier.fillMaxSize().offset(x=animateMoveElementsFromLeftSideWithDelay).padding(end = 10.dp), contentAlignment = Alignment.CenterEnd){
            Image(painter = painterResource(R.drawable.ic_arrow_right), contentDescription = null, modifier = Modifier.fillMaxSize(0.33f))
        }
        Box(modifier = Modifier.fillMaxSize().offset(x=animateMoveElementsFromLeftSide), contentAlignment = Alignment.Center){
            Image(painter = painterResource(R.drawable.ic_success_form_with_2_arrows), contentDescription = null, modifier = Modifier.fillMaxSize(0.8f))
        }
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.ic_success),
                modifier = Modifier.padding(4.dp).scale(animationSuccesIconScale),
                contentDescription = null,

            )
        }
    }

}