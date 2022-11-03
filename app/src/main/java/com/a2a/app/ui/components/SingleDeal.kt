package com.a2a.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.a2a.app.R
import com.a2a.app.ui.deals.DealModel
import com.a2a.app.ui.theme.*

@Composable
fun SingleDeal(
    item: DealModel,
    onClick: (name: String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.CardBg)
            .padding(HighPadding)
            .clip(RoundedCornerShape(CardCornerRadius))
            .clickable { onClick(item.name) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("")
                .size(Size.ORIGINAL)
                .crossfade(true)
                .placeholder(item.file)
                .error(item.file)
                .transformations()
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(CommonImageSize)
                .clip(RoundedCornerShape(LowCornerRadius))
        )

        Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

        Text(
            text = item.name,
            maxLines = 1,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 16.dp)
                .border(
                    width = DividerThickness,
                    color = MaterialTheme.colors.DividerBg,
                    shape = RoundedCornerShape(
                        LowCornerRadius
                    )
                )
                .padding(vertical = 8.dp)
        )
    }
}

@Preview
@Composable
fun SingleDealPreview() {

}