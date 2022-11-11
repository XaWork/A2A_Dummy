package com.a2a.app.ui.address

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a2a.app.data.model.AddressListModel
import com.a2a.app.ui.components.A2AButton
import com.a2a.app.ui.theme.*

@Composable
fun SingleAddress(
    address: AddressListModel.Result,
    onClick:(String)-> Unit
) {
    Card(
        modifier = Modifier
            .padding(bottom = SpaceBetweenViewsAndSubViews)
            .fillMaxWidth()
            .clip(RoundedCornerShape(CardCornerRadius))
            .background(color = MaterialTheme.colors.CardBg),
        elevation = CardElevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MediumPadding)
        ) {
            Text(text = address.address, fontSize = 18.sp, maxLines = 2, color = Color.DarkGray)

            Spacer(modifier = Modifier.height(SpaceBetweenViews))

            Row(modifier = Modifier.fillMaxWidth()) {
                A2AButton(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(50.dp)),
                    title = "Delete",
                    allCaps = false
                ) {
                    onClick("delete")
                }

                Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

                A2AButton(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(50.dp)),
                    title = "Edit",
                    allCaps = false
                ) {
                    onClick("edit")
                }
            }
        }
    }
}

@Preview
@Composable
fun SingleAddressPreview() {
    //SingleAddress()
}