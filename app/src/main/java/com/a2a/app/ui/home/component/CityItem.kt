package com.a2a.app.ui.home.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.a2a.app.R
import com.a2a.app.data.model.HomeModel

@Composable
fun CityItem(city: HomeModel.Result.City) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(city.file)
                .crossfade(true)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .transformations()
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(50.dp).clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = city.name,
            textAlign = TextAlign.Center,
            color = Color.Black,
            maxLines = 1,
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
fun CityItemPreview() {
   // CityItem()
}