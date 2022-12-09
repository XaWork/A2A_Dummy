package com.a2a.app.ui.home.component

import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.a2a.app.R
import com.a2a.app.data.model.HomeModel
import com.a2a.app.ui.theme.SpaceBetweenViewsAndSubViews
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun SliderView(
    state: PagerState,
    modifier: Modifier = Modifier,
    sliderImages: List<HomeModel.Result.Slider>
) {
    var imageUrl by remember {
        mutableStateOf("")
    }

    HorizontalPager(
        state = state,
        count = sliderImages.size
    ) { page ->
        imageUrl = sliderImages[page].file

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .transformations()
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
        }
    }
}

@Composable
fun DotIndicators(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = SpaceBetweenViewsAndSubViews),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colors.primaryVariant)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(color = Color.LightGray)
                )
            }

            if (index != totalDots - 1)
                Spacer(modifier = Modifier.padding(horizontal = 5.dp))
        }
    }
}

@Preview
@Composable
fun SliderViewPreview() {
    // SliderView(sliderImages = mutableListOf())
    DotIndicators(totalDots = 5, selectedIndex = 1)
}