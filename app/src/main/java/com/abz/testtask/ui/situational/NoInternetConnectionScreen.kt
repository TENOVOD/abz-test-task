package com.abz.testtask.ui.situational

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abz.testtask.R
import com.abz.testtask.ui.button.PrimaryButton
import com.abz.testtask.ui.labels.HeadingElementsWithAnimateScale
import com.abz.testtask.ui.theme.BackgroundColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NoInternetConnectionScreen(
   modifier: Modifier=Modifier,
   isStarted: Boolean,
   onClickTryAgain:()->Unit
) {

    var isFindingInternetConnection by remember {
        mutableStateOf(false)
    }

    val animationElementScale by animateFloatAsState(if(isStarted)0f else 1f, animationSpec = tween(500))

    Column(
        modifier = modifier.fillMaxSize().background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        AnimatedIconsBlock(isStarted = isStarted, isFindingInternetConnection = isFindingInternetConnection)
        Spacer(Modifier.height(24.dp))

        HeadingElementsWithAnimateScale(textSrc = R.string.There_is_no_internet_connection, isStarted = isStarted)
        Spacer(Modifier.height(24.dp))
        PrimaryButton(modifier = Modifier.scale(animationElementScale),textSrc = R.string.Try_again){
            CoroutineScope(Dispatchers.IO).launch {
                onClickTryAgain()
                isFindingInternetConnection=true
                delay(100)
                isFindingInternetConnection=false
            }
        }
    }


}


@Composable
private fun AnimatedIconsBlock(isStarted: Boolean, isFindingInternetConnection:Boolean) {

    val animateMoveElementsFromRightSide by animateDpAsState(
        if (isStarted) 1400.dp else 0.dp,
        animationSpec = tween(500)
    )
    val animateMoveElementsFromLeftSide by animateDpAsState(
        if (isStarted) -(1400).dp  else 0.dp ,
        animationSpec = tween(500)
    )

    val animationRotateDeniedIcon by animateFloatAsState(if(isFindingInternetConnection) 360f else 0f, animationSpec = tween(1000))
    val animationDeniedIconScale by animateFloatAsState(if(isFindingInternetConnection)0f else 1f, animationSpec = tween(1000))
    val animationWifiIconScale by animateFloatAsState(if(isFindingInternetConnection)0.95f else 1.05f, animationSpec = tween(1000))

    Box(modifier = Modifier
        .fillMaxWidth(0.56f)
        .aspectRatio(1f).scale(animationWifiIconScale)) {
        Box(Modifier.fillMaxSize().offset(x=animateMoveElementsFromLeftSide), contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(R.drawable.ic_blue_circle),
                contentDescription = "No internet connection icon",
                tint = Color.Unspecified
            )
            Icon(
                painter = painterResource(R.drawable.ic_wifi),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }

        Box(Modifier.fillMaxSize().offset(x=animateMoveElementsFromRightSide), contentAlignment = Alignment.TopEnd) {
            Icon(
                painter = painterResource(R.drawable.ic_denied),
                modifier = Modifier.padding(4.dp).scale(animationDeniedIconScale).size(75.dp).rotate(animationRotateDeniedIcon),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}