package com.a2a.app.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a2a.app.R
import com.a2a.app.ui.theme.CardElevation
import com.a2a.app.ui.theme.SpaceBetweenViewsAndSubViews
import com.a2a.app.ui.theme.TopAppBarBg

@Composable
fun A2AMainAppBar(
    onNavigationItemClick: () -> Unit,
    onNavigateToWallet: () -> Unit,
    onNavigateToBook: () -> Unit,
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))
                Column {
                    Text(text = "A2A", color = Color.White, fontSize = 16.sp)
                    Text(
                        text = "Intercity Logistics Service",
                        color = Color.White,
                        fontSize = 12.sp
                    )

                }
            }
        },
        backgroundColor = MaterialTheme.colors.TopAppBarBg,
        contentColor = Color.White,
        elevation = CardElevation,
        navigationIcon = {
            IconButton(onClick = onNavigationItemClick) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "")
            }
        },
        actions = {
            IconButton(onClick = { onNavigateToBook() }) {
                Icon(
                    painter = painterResource(id = R.drawable.home_app_bar_icon1),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(onClick = { onNavigateToWallet() }) {
                Icon(
                    painter = painterResource(id = R.drawable.wallet),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    )
}