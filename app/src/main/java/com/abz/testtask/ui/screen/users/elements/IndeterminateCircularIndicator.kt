package com.abz.testtask.ui.screen.users.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abz.testtask.ui.theme.SecondaryBlue

@Composable
fun IndeterminateCircularIndicator(isLoading:Boolean) {

    if (!isLoading) return
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter){
        CircularProgressIndicator(
            modifier = Modifier.width(48.dp).padding(bottom = 24.dp),
            color = SecondaryBlue,
            trackColor = Color.Unspecified,
        )
    }

}