package com.a2a.app.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.a2a.app.R
import com.a2a.app.data.model.HomeModel
import com.a2a.app.ui.theme.*

@Composable
fun CustomerItem(customer: HomeModel.Result.Client) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .background(MaterialTheme.colors.MainBgColor)
            .clip(RoundedCornerShape(CardCornerRadius))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(ScreenPadding)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(customer.file)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .transformations()
                    .build(),
                contentDescription = "",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = customer.name.uppercase(),
                color = MaterialTheme.colors.primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Customer",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )


        }
    }
}

@Preview
@Composable
fun CustomerItemPreview() {
    //   CustomerItem()
}