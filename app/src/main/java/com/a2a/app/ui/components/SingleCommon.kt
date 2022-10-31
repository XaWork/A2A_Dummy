package com.a2a.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.a2a.app.R
import com.a2a.app.data.model.CommonModel
import com.a2a.app.ui.theme.*

//this view is common for all city, service type and category
//city, service type, category are same in design so we make some common entities
// view -> SingleCommon, model -> CommonModel

@Composable
fun SingleCommon(
    item: CommonModel,
    onClick: (task: String, item: CommonModel) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.CardBg)
            .clickable { onClick("subcategory", item) }
    ) {

        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(HighPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.file)
                    .size(Size.ORIGINAL)
                    .crossfade(true)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_error)
                    .transformations()
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(CommonImageSize)
                    .clip(RoundedCornerShape(LowCornerRadius))
            )

            Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

            Column(
                modifier = Modifier.fillMaxWidth(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.name,
                    maxLines = 1,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(MediumSpacing))

                Text(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colors.ButtonBg,
                            shape = RoundedCornerShape(100)
                        )
                        .padding(vertical = MediumPadding, horizontal = HighPadding)
                        .clickable {
                            onClick("details", item)
                        },
                    text = stringResource(id = R.string.view_details),
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                )
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            color = MaterialTheme.colors.DividerBg,
            thickness = DividerThickness
        )
    }
}

@Preview
@Composable
fun SingleCommonPreview() {
}