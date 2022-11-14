package com.a2a.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.a2a.app.ui.theme.SpaceBetweenViewsAndSubViews

@Composable
fun A2ARadioButton(label: String, addressType: String, onRadioButtonSelected: (String) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = label == addressType, onClick = { onRadioButtonSelected(label) })

        Text(text = label)
    }

    Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))
}

@Preview
@Composable
fun A2ARadioButtonPreview() {
    A2ARadioButton(label = "Home", addressType = "", onRadioButtonSelected = {})
}