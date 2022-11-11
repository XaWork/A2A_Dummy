package com.a2a.app.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun TextFieldPlaceHolder(text: String) {
    Text(text = text, color = Color.Gray, fontSize = 14.sp)
}