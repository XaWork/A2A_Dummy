package com.a2a.app.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun A2ATextPlaceHolder(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(text = text)
}