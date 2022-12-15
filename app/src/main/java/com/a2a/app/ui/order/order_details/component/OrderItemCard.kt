package com.a2a.app.ui.order.order_details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a2a.app.data.model.OrderModel
import com.a2a.app.ui.theme.*

@Composable
fun OrderItemCard(
    data: OrderItemCardData
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CardCornerRadius))
            .background(color = MaterialTheme.colors.CardBg),
        elevation = CardElevation
    ) {
        Column(modifier = Modifier.padding(ScreenPadding)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Category",
                    maxLines = 2,
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(8f)
                )

                Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

                Icon(
                    painter = painterResource(id = com.a2a.app.R.drawable.pickuplocation_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(80.dp)
                        .background(color = MaterialTheme.colors.primary),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(SpaceBetweenViews))

            Text(
                text = "Weight: ${data.weight}kg",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViews))

            Text(
                text = "Rs: ${data.price}",
                modifier = Modifier
                    .background(color = MaterialTheme.colors.primary)
                    .padding(5.dp),
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun OrderItemCardPreview() {
    //OrderItemCard()
}

data class OrderItemCardData(
    val category: String,
    val subCategory: String,
    val weight: String,
    val price: String
)

fun OrderModel.Result.toOrderItemCardData(): OrderItemCardData {
    return OrderItemCardData(
        category = "",
        subCategory = "",
        weight = totalWeight,
        price = finalprice
    )
}