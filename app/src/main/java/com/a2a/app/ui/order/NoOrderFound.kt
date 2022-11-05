package com.a2a.app.ui.order

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a2a.app.R
import com.a2a.app.ui.components.A2AButton
import com.a2a.app.ui.theme.MainBgColor
import com.a2a.app.ui.theme.SpaceBetweenViewsAndSubViews

@Composable
fun NoOrderFound(onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .padding(20.dp)
            .background(MaterialTheme.colors.MainBgColor),
        contentAlignment = Alignment.Center
    ) {

        val stroke = Stroke(
            width = 2f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )

        Canvas(
            Modifier
                .fillMaxWidth()
                .matchParentSize()
                .align(Alignment.Center)
        ) {
            drawRoundRect(color = Color.Black, style = stroke)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "No item in Cart", color = Color.Black, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            Text(
                text = stringResource(id = R.string.when_you_add_items_to_cart_they_will_appear_here),
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                maxLines = 2,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            A2AButton(
                modifier = Modifier.fillMaxWidth(),
                title = "Browse Service Type",
                allCaps = false
            ) {
                onClick()
            }

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))
        }
    }
}

@Preview
@Composable
fun NoOrderFoundPreview() {
    NoOrderFound {

    }
}