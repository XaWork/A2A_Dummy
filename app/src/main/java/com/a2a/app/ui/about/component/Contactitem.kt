package com.a2a.app.ui.about.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a2a.app.ui.about.ContactInfo
import com.a2a.app.ui.theme.ScreenPadding
import com.a2a.app.ui.theme.SpaceBetweenViewsAndSubViews

@Composable
fun ContactItem(
    contactInfo: ContactInfo,
    onItemClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = ScreenPadding, vertical = SpaceBetweenViewsAndSubViews),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onItemClick
            ) {
                Icon(
                    painter = painterResource(id = contactInfo.icon),
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.size(24.dp)
                )
            }


            Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

            Text(
                text = contactInfo.info,
                color = Color.Black
            )
        }
        Divider()
    }
}

@Preview
@Composable
fun ContactItemPreview() {
    //ContactItem()
}