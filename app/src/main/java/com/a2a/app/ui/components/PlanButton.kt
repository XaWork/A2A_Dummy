package com.a2a.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PlanButton(
    title: String,
    onClick: () -> Unit
) {
    Text(
        text = "Buy Now",
        color = Color.Black,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(5.dp)
            .background(Color.White)
            .padding(5.dp)
            .clip(RoundedCornerShape(50.dp))
            .clickable { onClick() }
    )
}