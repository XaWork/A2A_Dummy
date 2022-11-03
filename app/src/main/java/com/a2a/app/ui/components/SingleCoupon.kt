package com.a2a.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a2a.app.ui.theme.*

@Composable
fun SingleCoupon(l : String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LowPadding)
            .border(
                width = DividerThickness,
                shape = RoundedCornerShape(CardCornerRadius),
                color = MaterialTheme.colors.DividerBg,
            )
            .background(color = MaterialTheme.colors.CardBg)
            .padding(LowPadding)
    ) {
        Text(
            text = "T2P_300",
            color = Color.Black,
            modifier = Modifier.clip(RoundedCornerShape(1.dp))
        )

        Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

        Text(
            text = stringResource(id = com.a2a.app.R.string.lorem_short),
            color = Color.Gray,
            modifier = Modifier.clip(RoundedCornerShape(1.dp))
        )
    }
}

@Preview
@Composable
fun SingleCouponPreview() {
    SingleCoupon("")
}