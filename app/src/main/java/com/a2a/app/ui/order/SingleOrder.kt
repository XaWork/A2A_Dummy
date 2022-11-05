package com.a2a.app.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.a2a.app.data.model.OrderModel
import com.a2a.app.toDate
import com.a2a.app.ui.theme.*
import java.util.*

@Composable
fun SingleOrder(order: OrderModel.Result, onClick: (order: OrderModel.Result) -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = MaterialTheme.colors.CardBg)
            .padding(start = ScreenPadding, end = ScreenPadding, top = ScreenPadding)
            .clickable { onClick(order) }
    ) {
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            Text(text = order.createdDate.toDate(), fontSize = 18.sp, color = Color.Black)

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            Text(text = order.orderid, fontSize = 14.sp, color = Color.Black)

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            Text(
                text = order.status.replace("_", " ")
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                fontSize = 16.sp,
                color = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))
        }

        Icon(
            imageVector = Icons.Filled.ArrowForwardIos,
            contentDescription = "",
            modifier = Modifier.align(Alignment.CenterEnd),
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomEnd),
            thickness = DividerThickness,
            color = MaterialTheme.colors.DividerBg
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SingleOrderPreview() {
    //SingleOrder()
}