package com.abz.testtask.ui.screen.signup

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abz.testtask.R
import com.abz.testtask.ui.font.Body1RegularSize16
import com.abz.testtask.ui.font.Body3RegularSize14
import com.abz.testtask.ui.font.Heading1RegularSize20
import com.abz.testtask.ui.font.TextFieldSupportingTextStyle
import com.abz.testtask.ui.theme.BlackAlpha087
import com.abz.testtask.ui.theme.ErrorFieldColor
import com.abz.testtask.ui.theme.FocusedFieldColor
import com.abz.testtask.ui.theme.PressedSecondaryColor
import com.abz.testtask.ui.theme.UnfocusedFieldColor


@Composable
fun FilledFormTextField(
    value: String,
    isError: Boolean,
    supportingErrorTextSrc: Int,
    labelText: Int,
    supportingTextSrc: Int = 0,
    onValueChange: (String) -> Unit
) {
    var isFocusable by remember {
        mutableStateOf(false)
    }
    val borderColor by animateColorAsState(
        if (isError) {
            ErrorFieldColor
        } else {
            if (isFocusable) {
                FocusedFieldColor
            } else {
                UnfocusedFieldColor
            }
        }, animationSpec = tween(300)
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(borderColor, RoundedCornerShape(4.dp))
                .padding(1.dp)
                .onFocusChanged {
                    isFocusable = it.isFocused
                },
            value = value,
            onValueChange = onValueChange,
            textStyle = Body1RegularSize16,
            label = {
                Text(text = stringResource(labelText))
            },
            shape = RoundedCornerShape(3.dp),
            singleLine = true,
            isError = isError,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedLabelColor = FocusedFieldColor,
                unfocusedLabelColor = UnfocusedFieldColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                cursorColor = BlackAlpha087,
                unfocusedTextColor = BlackAlpha087,
                focusedTextColor = BlackAlpha087,
                errorLabelColor = ErrorFieldColor,
                errorCursorColor = ErrorFieldColor
            )
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 4.dp)
        ) {
            if (isError) {
                Text(
                    text = stringResource(supportingErrorTextSrc),
                    style = TextFieldSupportingTextStyle.copy(color = ErrorFieldColor)
                )
            } else {
                if (supportingTextSrc != 0) {
                    Text(
                        text = stringResource(supportingTextSrc),
                        style = TextFieldSupportingTextStyle.copy(color = borderColor)
                    )
                } else {
                    Text(text = " ", style = Body3RegularSize14.copy(color = borderColor))
                }

            }
        }
        Spacer(Modifier.height(10.dp))

    }

}

@Composable
fun UploadImageTextField(
    value: String,
    isError: Boolean,
    supportingErrorTextSrc: Int,
    labelText: Int,
    buttonTextSrc: Int,
    onOpenImage: () -> Unit
) {
    val borderColor by animateColorAsState(
        if (isError) {
            ErrorFieldColor
        } else {
            UnfocusedFieldColor

        }, animationSpec = tween(300)
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(borderColor, RoundedCornerShape(4.dp))
                .padding(1.dp),
            value = value,
            readOnly = true,
            enabled = false,
            onValueChange = { },
            textStyle = Body1RegularSize16,
            label = {
                Text(text = stringResource(labelText))
            },
            trailingIcon = {
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .clickable(
                        onClick = onOpenImage,
                        indication = ripple(
                            color = PressedSecondaryColor
                        ),
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    .padding(16.dp)) {
                    Text(
                        text = stringResource(buttonTextSrc),
                        style = Body1RegularSize16.copy(color = FocusedFieldColor)
                    )
                }
            },
            shape = RoundedCornerShape(3.dp),
            singleLine = true,
            isError = isError,
            colors = TextFieldDefaults.colors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedLabelColor = FocusedFieldColor,
                unfocusedLabelColor = UnfocusedFieldColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                disabledContainerColor = Color.White,
                cursorColor = BlackAlpha087,
                unfocusedTextColor = BlackAlpha087,
                focusedTextColor = BlackAlpha087,
                errorLabelColor = ErrorFieldColor,
                errorCursorColor = ErrorFieldColor
            )
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 4.dp)
        ) {
            if (isError) {
                Text(
                    text = stringResource(supportingErrorTextSrc),
                    style = TextFieldSupportingTextStyle.copy(color = ErrorFieldColor)
                )
            } else {
                Text(text = " ", style = Body3RegularSize14.copy(color = borderColor))
            }
        }
        Spacer(Modifier.height(10.dp))

    }
}