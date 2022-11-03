package com.a2a.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a2a.app.R
import com.a2a.app.ui.theme.CardElevation
import com.a2a.app.ui.theme.SpaceBetweenViewsAndSubViews
import com.a2a.app.ui.theme.TopAppBarBg

@Composable
fun A2ATopAppBar(
    title: String = "A2A",
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))
                Text(text = title, color = Color.White, fontSize = 14.sp)
            }
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(painter = painterResource(id = R.drawable.ic_back_1), contentDescription = "")
            }
            //Image(painter = painterResource(id = R.drawable.ic_back_1), contentDescription = "")
        },
        backgroundColor = MaterialTheme.colors.TopAppBarBg,
        contentColor = Color.White,
        elevation = CardElevation
    )
}

@Preview
@Composable
fun A2ATopAppBarPreview() {
    A2ATopAppBar() {

    }
}