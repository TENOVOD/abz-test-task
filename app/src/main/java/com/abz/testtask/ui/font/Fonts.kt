package com.abz.testtask.ui.font

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.abz.testtask.R
import com.abz.testtask.ui.theme.BlackAlpha087
import com.abz.testtask.ui.theme.ErrorColor


val MainFont = FontFamily(
    Font(R.font.nunito_sans_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.nunito_sans_semibold,FontWeight.SemiBold,FontStyle.Normal)
)

val Heading1RegularSize20 = TextStyle(
    fontSize = 20.sp,
    lineHeight = 24.sp,
    fontWeight = FontWeight.W400,
    fontFamily = MainFont,
    color = BlackAlpha087
)
val Body1RegularSize16 = TextStyle(
    fontSize = 16.sp,
    lineHeight = 24.sp,
    fontWeight = FontWeight.W400,
    fontFamily = MainFont,
    color = BlackAlpha087
)
val Body2RegularSize18 = TextStyle(
    fontSize = 18.sp,
    lineHeight = 24.sp,
    fontWeight = FontWeight.W400,
    fontFamily = MainFont,
    color = BlackAlpha087
)
val ButtonTextSize18 = TextStyle(
    fontSize = 18.sp,
    lineHeight = 24.sp,
    fontFamily = MainFont,
    fontWeight = FontWeight.W400,
    color = BlackAlpha087
)
val Body3RegularSize14 = TextStyle(
    fontSize = 14.sp,
    lineHeight = 20.sp,
    fontWeight = FontWeight.W400,
    fontFamily = MainFont,
    color = BlackAlpha087
)

val TextFieldSupportingTextStyle = TextStyle(
    fontSize = 12.sp,
    lineHeight = 12.sp,
    fontWeight = FontWeight.W400,
    fontFamily = MainFont,
    color = ErrorColor
)
