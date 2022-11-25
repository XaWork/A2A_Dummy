package com.a2a.app.ui.home

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.a2a.app.ui.theme.CardElevation

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    items: List<BottomNavItem>,
    onItemClick: (BottomNavItem) -> Unit
) {
    BottomNavigation(
        modifier = modifier,
        elevation = CardElevation
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = false,
                onClick = { onItemClick(item) },
                icon = {
                    
                }
            )
        }
    }
}