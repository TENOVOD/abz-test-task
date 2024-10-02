package com.abz.testtask.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abz.testtask.R
import com.abz.testtask.ui.font.Heading1RegularSize20
import com.abz.testtask.ui.theme.PrimaryYellow


@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PrimaryYellow),
        contentAlignment = Alignment.Center
    ) {
        LoadingLogoWithTextColumn()
    }
}


@Composable
fun LoadingLogoWithTextColumn() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LoadingIcon(iconRes = R.drawable.ic_loading_icon)
        Text(text = stringResource(R.string.app_name), style = Heading1RegularSize20)
    }
}


@Composable
fun LoadingIcon(iconRes: Int) {
    Icon(
        painter = painterResource(iconRes),
        modifier = Modifier.height(65.dp),
        contentDescription = "Loading Icon",
        tint = Color.Unspecified
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoadingScreen()
}