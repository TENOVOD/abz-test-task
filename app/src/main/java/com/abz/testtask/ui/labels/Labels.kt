package com.abz.testtask.ui.labels

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import com.abz.testtask.ui.font.Heading1RegularSize20

@Composable
fun HeadingElementsWithAnimateScale(textSrc:Int,isStarted:Boolean){
    val animationElementScale by animateFloatAsState(if(isStarted)0f else 1f, animationSpec = tween(500))
    Text(text = stringResource(textSrc), modifier = Modifier.scale(animationElementScale), style = Heading1RegularSize20)
}
