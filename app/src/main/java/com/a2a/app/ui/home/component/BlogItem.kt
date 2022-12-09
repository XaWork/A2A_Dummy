package com.a2a.app.ui.home.component

import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.a2a.app.toDate
import com.a2a.app.ui.theme.*

@Composable
fun BlogItem(blog: HomeModel.Result.Blog) {
    Column(
        modifier = Modifier
            .width(320.dp)
            .background(color = MaterialTheme.colors.CardBg)
            .clip(RoundedCornerShape(CardCornerRadius))
    ) {
        Text(
            text = "BLOG",
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
                .fillMaxWidth(1f)
                .padding(ScreenPadding),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(blog.image)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .transformations()
                    .build(),
                contentDescription = "",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = blog.title,
                    color = MaterialTheme.colors.primary,
                    fontSize = 14.sp,
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = Html.fromHtml(blog.description, 0).toString(),
                    fontSize = 12.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.calender),
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary
                    )

                    Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

                    Text(
                        text = blog.createdAt.toDate(),
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun BlogItemPreview() {
    // BlogItem()
}