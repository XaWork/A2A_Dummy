package com.a2a.app.ui.order.order_details.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a2a.app.ui.theme.LowPadding
import com.a2a.app.ui.theme.MediumPadding

@Composable
fun OrderService(service: OrderServiceData, onClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .padding(vertical = LowPadding)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (service.enable) MaterialTheme.colors.primary else Color.Gray
            )
            .clickable { if (service.enable) onClick(service.name) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = service.name,
            textAlign = TextAlign.Center,
            color = if (service.enable) MaterialTheme.colors.primary else Color.Gray,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MediumPadding)
        )
    }
}

@Preview
@Composable
fun OrderServicePreview() {

}

data class OrderServiceData(
    val id: String,
    val name: String,
    val enable: Boolean
)