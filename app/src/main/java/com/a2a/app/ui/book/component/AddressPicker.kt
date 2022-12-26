package com.a2a.app.ui.book.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a2a.app.R
import com.a2a.app.ui.theme.*

@Composable
fun AddressPicker(
    icon: Int,
    title: String,
    fullName: String,
    address: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        elevation = CardElevation,
        shape = RoundedCornerShape(CardCornerRadius),
        backgroundColor = MaterialTheme.colors.CardBg
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .background(color = backgroundColor)
                    .padding(horizontal = MediumPadding, vertical = ScreenPadding)
                    .clickable { onClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "",
                    modifier = Modifier.size(50.dp),
                    tint = Color.White
                )

                Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

                Text(
                    text = title.uppercase(),
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = MediumPadding, vertical = ScreenPadding),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = fullName,
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = address,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Preview
@Composable
fun AddressPickerPreview() {
    //AddressPicker()
}