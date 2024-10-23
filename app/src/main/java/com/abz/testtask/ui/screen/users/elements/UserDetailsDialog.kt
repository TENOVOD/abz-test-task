package com.abz.testtask.ui.screen.users.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.abz.testtask.R
import com.abz.testtask.response.User
import com.abz.testtask.ui.font.Body3RegularSize14
import com.abz.testtask.ui.font.Heading1RegularSize20
import com.abz.testtask.ui.theme.BackgroundColor
import com.abz.testtask.ui.theme.BlackAlpha060

@Composable
fun UserDetailsDialog(isActive: Boolean, user: User, onDismiss: () -> Unit) {

    AnimatedVisibility(isActive) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .background(color = BackgroundColor, shape = RoundedCornerShape(16.dp))
            ) {
                Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = user.photo,
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(
                                CircleShape
                            )
                    )
                    Spacer(Modifier.height(20.dp))
                    Text(text = user.name, style = Heading1RegularSize20)
                    Spacer(Modifier.height(8.dp))
                    Text(text = user.position, style = Body3RegularSize14.copy(color = BlackAlpha060))
                }

                Box(modifier = Modifier.fillMaxWidth()) {
                    CloseButton(onClose = onDismiss)
                }
            }
        }
    }
}


@Composable
private fun CloseButton(onClose: () -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = onClose) {
            Icon(painter = painterResource(R.drawable.ic_close), contentDescription = null)
        }
    }
}