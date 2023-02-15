package com.a2a.app.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun A2ADateTimeTextField(
    modifier: Modifier = Modifier,
    leadingIcon: Int,
    placeholder: String,
    onClick: () -> Unit
) {
    OutlinedTextField(
        value = "",
        onValueChange = { onClick() },
        placeholder = { A2ATextPlaceHolder(text = placeholder) },
        leadingIcon = {
            Icon(painter = painterResource(id = leadingIcon), contentDescription = "")
        },
        readOnly = true,

    )
}