package com.a2a.app.ui.components

import android.text.BoringLayout
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun HeadingText(
    text: String,
    color: Color = Color.Black,
    allCaps: Boolean = false
) {
    Text(
        text = if (allCaps) text.uppercase() else text,
        color = color,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ShortDescriptionText(
    text: String,
    color: Color = Color.Black,
    maxLines: Int = 3,
    fontSize: TextUnit = 14.sp
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}
