package com.abz.testtask.ui.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abz.testtask.ui.font.ButtonTextSize18
import com.abz.testtask.ui.theme.DisabledPrimaryColor
import com.abz.testtask.ui.theme.NormalPrimaryColor
import com.abz.testtask.ui.theme.PressedPrimaryColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    textSrc: Int,
    isDisabled:Boolean = false,
    onClick: () -> Unit
) {
    var isPressed by remember {
        mutableStateOf(false)
    }

    val animateColor by animateColorAsState(
        if(isPressed){
            PressedPrimaryColor
        }else  if (isDisabled){
            DisabledPrimaryColor
        }else {
            NormalPrimaryColor
        },
        animationSpec = tween(200)
    )

    Box(modifier = modifier
        .background(
            color = animateColor,
            shape = RoundedCornerShape(24.dp)
        )
        .clip(RoundedCornerShape(24.dp))
        .clickable(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    onClick()
                    isPressed = true
                    delay(150)
                    isPressed=false
                }
            },
            indication = ripple(
                color = animateColor
            ),
            interactionSource = remember { MutableInteractionSource() }
        ).padding(vertical = 12.dp)
        .width(140.dp),

        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(textSrc),
            style = ButtonTextSize18,
        )
    }

    
}