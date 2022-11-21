package com.a2a.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun A2AAutoCompleteTextField(
    // text: String,
    modifier: Modifier = Modifier,
    label: String,
    list: List<String>,
    onSelect: (String) -> Unit
) {

    var text by remember {
        mutableStateOf("")
    }
    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    val interceptionSource = remember {
        MutableInteractionSource()
    }

    //label field
    Column(
        modifier = modifier
            .clickable(
                interactionSource = interceptionSource,
                indication = null,
                onClick = {
                    expanded = false
                }
            )
    ) {
        Column {
            Row {
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        expanded = true
                    },
                    label = { Text(text = label) },
                    maxLines = 1,
                    modifier =Modifier
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = icon,
                                contentDescription = ""
                            )
                        }
                    },
                )
            }

            AnimatedVisibility(visible = expanded) {
                Card(
                    modifier = Modifier
                        .width(textFieldSize.width.dp),
                    elevation = 10.dp
                ) {
                    LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) {
                        if (text.isNotEmpty()) {
                            items(
                                list.filter {
                                    it.lowercase()
                                        .contains(text.lowercase()) || it.lowercase()
                                        .contains("others")
                                }.sorted()
                            ) {
                                AutoCompleteItem(title = it) { title ->
                                    text = title
                                    expanded = false
                                }
                            }
                        }else{
                            items(list.sorted()) {
                                AutoCompleteItem(title = it) { title ->
                                    text = title
                                    expanded = false
                                    onSelect(title)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AutoCompleteItem(
    title: String,
    onSelect: (String) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onSelect(title)
        }
        .padding(10.dp)
    ) {
        Text(text = title)
    }
}