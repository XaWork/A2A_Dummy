package com.a2a.app.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a2a.app.R
import com.a2a.app.ui.theme.LowPadding
import com.a2a.app.ui.theme.MediumPadding
import com.a2a.app.ui.theme.SpaceBetweenViewsAndSubViews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.navigation_drawer_background),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.primary)
                .padding(all = LowPadding)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

            Text(text = "", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun DrawerBody(
    items: List<NavigationDrawerItem>,
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    state: ScaffoldState,
    onItemClick: (NavigationDrawerItem) -> Unit
) {
    LazyColumn(modifier) {
        items(items = items) { item ->
            DrawerBodyItem(item = item, onItemClick = {
                scope.launch {
                    state.drawerState.close()
                    onItemClick(item)
                }
            })
        }
    }
}

@Composable
fun DrawerBodyItem(item: NavigationDrawerItem, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(all = MediumPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.icon),
            contentDescription = "",
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(color = Color.Black)
        )
        Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

        Text(text = item.title, modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
fun DrawerHeaderPreview() {
    DrawerHeader()
}