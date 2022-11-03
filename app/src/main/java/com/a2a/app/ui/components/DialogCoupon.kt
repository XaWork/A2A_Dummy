package com.a2a.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.a2a.app.ui.theme.*

@Composable
fun DialogCoupon(setShowDialog: (Boolean) -> Unit) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(shape = RoundedCornerShape(LowCornerRadius), color = MaterialTheme.colors.CardBg) {
            Box(contentAlignment = Alignment.Center) {
                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(top = HighPadding),
                        text = "Coupons",
                        color = Blue700,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    val list = listOf("", "", "", "", "", "")
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MediumPadding)
                    ) {
                        items(list) { l ->
                            SingleCoupon(l)
                        }
                    }
                }
            }
        }
    }
}