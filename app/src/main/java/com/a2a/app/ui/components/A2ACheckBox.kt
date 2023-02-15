package com.a2a.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun A2ACheckbox(
    enabled: Boolean,
    checked: Boolean,
    onCheckedChange: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, enabled = enabled, onCheckedChange = { onCheckedChange() })
        Text(text = title, color = if (enabled) Color.Black else Color.LightGray)
    }
}

@Preview
@Composable
fun A2ACheckBoxPreview() {
    //A2ACheckbox(checked = true, onCheckedChange = { /*TODO*/ }, title = "Checkbox")
}