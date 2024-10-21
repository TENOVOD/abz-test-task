package com.abz.testtask.ui.screen.users.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.abz.testtask.R
import com.abz.testtask.response.User
import com.abz.testtask.ui.font.Body2RegularSize18
import com.abz.testtask.ui.font.Body3RegularSize14
import com.abz.testtask.ui.theme.BlackAlpha060


@Composable
fun UserCard(user: User) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 24.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column {
                Box(modifier = Modifier.size(50.dp)
                    .clip(
                        CircleShape
                    ), contentAlignment = Alignment.Center){
                    Box(contentAlignment = Alignment.Center){
                        Icon(painter = painterResource(R.drawable.ic_grey_circle_with_black_border), contentDescription = null, modifier = Modifier.fillMaxSize(), tint = Color.Unspecified)
                        Icon(painter = painterResource(R.drawable.ic_blue_person), contentDescription = null, modifier = Modifier.fillMaxSize(0.6f), tint = Color.Unspecified)
                    }

                    AsyncImage(
                        model = user.photo,
                        contentDescription = "User image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }
            Column {
                //name
                Text(text = user.name, style = Body2RegularSize18)
                Spacer(Modifier.height(4.dp))
                //Position
                Text(text = user.position, style = Body3RegularSize14.copy(color = BlackAlpha060))
                Spacer(Modifier.height(8.dp))
                //email
                Text(text = user.email, style = Body3RegularSize14, maxLines = 1, overflow = TextOverflow.Ellipsis)
                //phone number
                Text(text = user.phone, style = Body3RegularSize14)
            }
        }
    }
}