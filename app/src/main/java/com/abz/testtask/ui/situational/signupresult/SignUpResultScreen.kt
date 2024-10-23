package com.abz.testtask.ui.situational.signupresult

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abz.testtask.R
import com.abz.testtask.ui.button.PrimaryButton
import com.abz.testtask.ui.labels.HeadingElementsColumnWithAnimateScale
import com.abz.testtask.ui.labels.HeadingElementsWithAnimateScale
import com.abz.testtask.ui.theme.BackgroundColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SignUpResultScreen(
    modifier: Modifier=Modifier,
    isSuccess: Boolean,
    messages:List<String>,
    onClickSuccess: () -> Unit,
    onClickFailed: () -> Unit,
    onClose:()->Unit
) {

    var hasShowedLocal by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(Unit) {
        hasShowedLocal=false
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(
                BackgroundColor
            )
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isSuccess) {
                    SignUpSuccessAnimation(isStarted = hasShowedLocal)
                    HeadingElementsWithAnimateScale(
                        textSrc = R.string.User_successfully_registered,
                        isStarted = hasShowedLocal
                    )
                    PrimaryButton(textSrc = R.string.Got_it) {
                        onClickSuccess()
                    }
                } else {
                    SignUpFailedAnimation(isStarted = hasShowedLocal)
                    HeadingElementsColumnWithAnimateScale(
                        messages = messages,
                        isStarted = hasShowedLocal
                    )
                    PrimaryButton(textSrc = R.string.Try_again) {
                        onClickFailed()
                    }
                }

            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(onClick = onClose) {
                    Icon(painter = painterResource(R.drawable.ic_close), contentDescription = null)
                }
            }
        }

    }

}