package com.abz.testtask.ui.bottombar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.abz.testtask.R
import com.abz.testtask.navigation.LocalNavController
import com.abz.testtask.navigation.NavigationScreen
import com.abz.testtask.ui.font.Body1RegularSize16
import com.abz.testtask.ui.theme.BottomBarActiveColor
import com.abz.testtask.ui.theme.BottomBarBackgroundColor
import com.abz.testtask.ui.theme.BottomBarInactiveColor
import com.abz.testtask.ui.theme.PressedSecondaryColor

@Composable
fun MainButtonBar(screenName: BottomBarScreen, modifier: Modifier = Modifier) {

    val navController = LocalNavController.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = BottomBarBackgroundColor)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            BottomElements( // Users element
                iconSrc = R.drawable.ic_users,
                textSrc = R.string.Users,
                isActive = screenName == BottomBarScreen.USERS_SCREEN
            ) {
                navController.navigate(NavigationScreen.USERS_SCREEN.name)
            }

            BottomElements( // Sign Up element
                iconSrc = R.drawable.ic_sign_up,
                textSrc = R.string.Sign_up,
                isActive = screenName == BottomBarScreen.SIGN_UP_SCREEN
            ) {
                navController.navigate(NavigationScreen.SIGN_UP_SCREEN.name)
            }
        }
    }
}

@Composable
fun BottomElements(
    modifier: Modifier = Modifier,
    iconSrc: Int,
    textSrc: Int,
    isActive: Boolean,
    onClick: () -> Unit
) {

    val animateColor by animateColorAsState(
        if (isActive) BottomBarActiveColor else BottomBarInactiveColor,
        animationSpec = tween(200)
    )

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .clickable(
                onClick = onClick,
                indication = ripple(
                    color = PressedSecondaryColor
                ),
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Icon(painter = painterResource(iconSrc), contentDescription = null, tint = animateColor)
        Text(text = stringResource(textSrc), style = Body1RegularSize16.copy(color = animateColor))
    }
}

enum class BottomBarScreen {
    USERS_SCREEN,
    SIGN_UP_SCREEN
}