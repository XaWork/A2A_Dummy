package com.a2a.app.ui.book.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.a2a.app.R
import com.a2a.app.ui.components.A2ADateTimeTextField
import com.a2a.app.ui.components.A2ADropDown
import com.a2a.app.ui.theme.SpaceBetweenViews
import com.a2a.app.ui.theme.SpaceBetweenViewsAndSubViews

@Composable
fun DateTimeSelector(
    heading: String,
) {
    Column {
        Text(
            text = stringResource(id = R.string.select_pickup_option),
            fontSize = 14.sp,
            color = Color.Black
        )
        Divider()
        Spacer(modifier = Modifier.height(SpaceBetweenViews))
        Row(modifier = Modifier.fillMaxWidth()) {
            A2ADateTimeTextField(
                leadingIcon = R.drawable.ic_calendar,
                placeholder = "viewModel.state.value.pickupDate"
            ) {}
            Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))
            A2ADropDown(
                value = "viewModel.state.value.timeSlot",
                label = "",
                onValueChange = { "viewModel.onEvent(BookEvent.PickupDateChanged(it))" },
                list = emptyList()
            )
        }
    }
}

@Preview(device = Devices.NEXUS_5, showSystemUi = true)
@Composable
fun DateTimeSelectorPreview() {
    //DateTimeSelector(heading = "")
    Simpleview()
}

@Composable
fun Simpleview() {
    Text(text = "Hello world")
}