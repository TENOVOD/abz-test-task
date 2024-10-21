package com.abz.testtask.ui.topbars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abz.testtask.R
import com.abz.testtask.ui.font.Heading1RegularSize20
import com.abz.testtask.ui.theme.PrimaryYellow

@Composable
fun RequestTopBar(textSrc:Int,modifier: Modifier=Modifier){
    Row(
        modifier = modifier.fillMaxWidth().background(PrimaryYellow),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(textSrc), modifier = Modifier.padding(16.dp), style = Heading1RegularSize20)
    }
}