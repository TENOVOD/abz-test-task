package com.abz.testtask.ui.bottomsheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abz.testtask.R
import com.abz.testtask.ui.font.Body1RegularSize16
import com.abz.testtask.ui.theme.BlackAlpha048
import com.abz.testtask.ui.theme.PressedSecondaryColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPhotoBottomSheet(isActive:Boolean, sheetState: SheetState, openCamera:()->Unit, openGallery:()->Unit, onDismiss:()->Unit){
    if (isActive){
        ModalBottomSheet(onDismissRequest =  onDismiss, sheetState = sheetState, dragHandle = {
            BottomSheetDefaults.DragHandle(color = BlackAlpha048 )
        }) {
            Column(modifier =  Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(R.string.Choose_how_you_want_to_add_a_photo), style = Body1RegularSize16.copy(color = BlackAlpha048))
                Spacer(Modifier.height(40.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    AddPhotoElement(iconSrc = R.drawable.ic_camera, titleSrc = R.string.Camera, onClick = openCamera)
                    AddPhotoElement(iconSrc = R.drawable.ic_gallery, titleSrc = R.string.Gallery, onClick = openGallery)
                }
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun AddPhotoElement(iconSrc:Int,titleSrc:Int, onClick:()->Unit){
    Box(modifier = Modifier.clickable(
        onClick = onClick,
        indication = ripple(
            color = PressedSecondaryColor
        ),
        interactionSource = remember { MutableInteractionSource() }
    )){
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(painter = painterResource(iconSrc), contentDescription = "Camera", tint = Color.Unspecified)
            Text(text = stringResource(titleSrc), style = Body1RegularSize16)
        }
    }
}