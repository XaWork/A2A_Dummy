package com.a2a.app.ui.book.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a2a.app.data.model.AddressListModel
import com.a2a.app.ui.components.A2AButton
import com.a2a.app.ui.theme.*

@Composable
fun AddressItem(
    address: AddressListModel.Result,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = SpaceBetweenViewsAndSubViews)
            .clip(RoundedCornerShape(CardCornerRadius))
            .background(color = MaterialTheme.colors.MainBgColor)
            .clickable { onClick() },
        elevation = CardElevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MediumPadding)
        ) {
            Text(
                text = address.address,
                fontSize = 18.sp,
                maxLines = 2,
                color = Color.DarkGray,
            )
        }
    }
}
