package com.a2a.app.ui.order.order_details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PriceInfoItem(
    priceInfo: PriceInfo
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = priceInfo.tag,
            color = Color.Gray,
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Rs. ${priceInfo.price}",
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )

    }
}

@Preview
@Composable
fun PriceInfoItemPreview() {
    //PriceInfoItem()
}

data class PriceInfo(
    val tag: String,
    val price: String
)