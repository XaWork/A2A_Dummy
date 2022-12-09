package com.a2a.app.ui.home.component

import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.a2a.app.R
import com.a2a.app.data.model.HomeModel
import com.a2a.app.ui.theme.*

@Composable
fun TestimonialItem(testimonial: HomeModel.Result.Testimonial) {
    Column(
        modifier = Modifier
            .width(320.dp)
            .background(color = MaterialTheme.colors.CardBg)
            .clip(RoundedCornerShape(CardCornerRadius))
    ) {
        Text(
            text = "TESTIMONIAL",
            modifier = Modifier
                .background(color = MaterialTheme.colors.primary)
                .padding(horizontal = HighPadding, vertical = LowPadding)
                .align(Alignment.End),
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

        Row(
            modifier = Modifier
                .padding(ScreenPadding)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(testimonial.image)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .transformations()
                    .build(),
                contentDescription = "",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

            Column {
                Text(
                    text = testimonial.name,
                    color = MaterialTheme.colors.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                val testimonialText: String =
                    Html.fromHtml(testimonial.message, 0).toString()
                Text(
                    text = testimonialText,
                    fontSize = 12.sp,
                    color = Color.Black,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
fun TestimonialItemPreview() {
    //TestimonialItem()
}