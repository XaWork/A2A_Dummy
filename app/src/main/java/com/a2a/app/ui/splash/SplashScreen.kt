package com.a2a.app.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.a2a.app.R

@Composable
fun SplashScreen() {
    Image(
        painter = painterResource(id = R.drawable.splash_screen),
        contentDescription = "splash screen",
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}