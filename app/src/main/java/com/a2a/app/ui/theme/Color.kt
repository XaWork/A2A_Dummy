package com.a2a.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val Blue200 = Color(0xFF076CA3)
val Blue500 = Color(0xFF1333E1)
val Blue700 = Color(0xFF0c3c8c)

val MainLightBg = Color(0XFFe9ebf0)
val MainDarkBg = Color(0xFF28292D)

val DividerLightBg = Color(0XFF000000)
val DividerDarkBg = Color(0xFFA4A4A7)

val CardLightBg = Color(0XFFFFFFFF)
val CardDarkBg = Color(0xFF1B1B1B)

val Green500 = Color(0xFF1DBF73)
val Green700 = Color(0xFF13804D)
val Gray700 = Color(0xFFC4C4C4)
val Gray200 = Color(0xFFF4F4F4)

val HintColor = Color(0xFF969696)

val Colors.buttonBackground: Color
@Composable
get() = if(isLight) Green700 else Color.Black

val Colors.MainBgColor: Color
@Composable
get() = if(!isSystemInDarkTheme()) MainLightBg else MainDarkBg

val Colors.CardBg: Color
@Composable
get() = if(!isSystemInDarkTheme()) CardLightBg else CardDarkBg

val Colors.ButtonBg: Color
@Composable
get() = if(isLight)  Blue700 else Blue700

val Colors.DividerBg: Color
@Composable
get() = if(!isSystemInDarkTheme())  DividerLightBg else DividerDarkBg

val Colors.TopAppBarBg: Color
@Composable
get() = if(!isSystemInDarkTheme())  Blue200 else Blue700


