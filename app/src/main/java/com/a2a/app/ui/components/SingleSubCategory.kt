package com.a2a.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.a2a.app.R
import com.a2a.app.data.model.AllSubCategoryModel
import com.a2a.app.ui.theme.*

@OptIn(ExperimentalUnitApi::class)
@Composable
fun SingleSubCategory(subCategory: AllSubCategoryModel.Result) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LowPadding)
            .clip(RoundedCornerShape(CardCornerRadius)),
        elevation = CardElevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.CardBg)
                .padding(LowPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(CardCornerRadius)),
                elevation = CardElevation
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(subCategory.file)
                        .build(),
                    contentDescription = "",
                    placeholder = painterResource(id = R.drawable.image_placeholder),
                    error = painterResource(id = R.drawable.image_placeholder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(LowPadding)
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            Text(text = subCategory.name, color = Color.Red)

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            Text(
                text = subCategory.description,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                letterSpacing = TextUnit(1.2F, TextUnitType(1.2.toLong())),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun SingleSubCategoryPreview() {
    SingleSubCategory(
        AllSubCategoryModel.Result(
            1,
            ", ",
            "",
            1,
            "Description",
            "",
            "",
            "",
            "Sub category name",
            "",
            "",
            listOf(""),
            "",
            3
        )
    )
}