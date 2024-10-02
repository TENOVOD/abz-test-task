package com.abz.testtask.ui.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abz.testtask.ui.font.Body2RegularSize18
import com.abz.testtask.ui.font.ButtonTextSize18
import com.abz.testtask.ui.theme.BlackAlpha087
import com.abz.testtask.ui.theme.NormalPrimaryColor
import com.abz.testtask.ui.theme.PressedPrimaryColor

@Composable
fun PrimaryButton(modifier: Modifier=Modifier,textSrc:Int,isPressed:Boolean, onClick:()->Unit){

    val animatedButtonColor by animateColorAsState(if (isPressed) PressedPrimaryColor else NormalPrimaryColor)

    Button(onClick = onClick, modifier = modifier.clip(RoundedCornerShape(24.dp)), colors = ButtonDefaults.buttonColors(containerColor = animatedButtonColor, contentColor = BlackAlpha087)) {
        Text(text = stringResource(textSrc), style = ButtonTextSize18, modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp))
    }
}