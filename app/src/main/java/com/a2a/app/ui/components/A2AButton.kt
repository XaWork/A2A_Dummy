package com.a2a.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a2a.app.ui.theme.Blue200
import com.a2a.app.ui.theme.Blue500

@Composable
fun A2AButton(
    modifier: Modifier = Modifier,
    title: String = "A2A",
    allCaps: Boolean = true,
    textColor: Color = Color.White,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .background(Blue200)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = modifier.padding(vertical = 15.dp),
            text = if (allCaps) title.uppercase() else title,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun A2AButtonPreview() {
    A2AButton(modifier = Modifier.fillMaxWidth()) {}
}