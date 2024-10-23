package com.abz.testtask.ui.labels

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abz.testtask.ui.font.Heading1RegularSize20

@Composable
fun HeadingElementsWithAnimateScale(textSrc:Int,isStarted:Boolean){
    val animationElementScale by animateFloatAsState(if(isStarted)0f else 1f, animationSpec = tween(500))
    Text(text = stringResource(textSrc), modifier = Modifier.scale(animationElementScale), style = Heading1RegularSize20)
}

@Composable
fun HeadingElementsColumnWithAnimateScale(messages:List<String>,isStarted:Boolean){
    val animationElementScale by animateFloatAsState(if(isStarted)0f else 1f, animationSpec = tween(500))

    Column(modifier = Modifier.scale(animationElementScale).padding(horizontal =  40.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        messages.forEach{ message->
            Text(text = message.replace('.',' '), modifier = Modifier.scale(animationElementScale), style = Heading1RegularSize20, textAlign = TextAlign.Center)
        }
    }

}

