package com.a2a.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a2a.app.R
import com.a2a.app.common.Status
import com.a2a.app.data.model.AddressListModel
import com.a2a.app.ui.book.component.AddressItem
import com.a2a.app.ui.theme.CardBg
import com.a2a.app.ui.theme.ScreenPadding
import com.a2a.app.ui.theme.SpaceBetweenViewsAndSubViews

@Composable
fun A2ABottomSheetDialog(
    addresses: Status<AddressListModel>,
    onAddressChange: (String) -> Unit,
    onClose: () -> Unit
) {
    var addressList by remember {
        mutableStateOf<List<AddressListModel.Result>>(emptyList())
    }
    var loading by remember {
        mutableStateOf(false)
    }

    when (addresses) {
        is Status.Loading -> {
            loading = true
        }
        is Status.Success -> {
            addressList = addresses.value.result
        }
        is Status.Failure -> {

        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = MaterialTheme.colors.CardBg)
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(ScreenPadding)
                .align(Alignment.TopCenter)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.select_address))
                IconButton(
                    onClick = onClose,
                ) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = "Cancel "
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.height(500.dp),
                verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
            ) {
                if (addressList.isNotEmpty())
                    items(addressList) { address ->
                        AddressItem(
                            address = address,
                            onClick = { onAddressChange(address.address) })
                    }
            }
        }

        if (loading)
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        if (addressList.isNotEmpty())
            Text(
                text = stringResource(id = R.string.no_address_saved),
                modifier = Modifier.align(Alignment.Center)
            )

        A2AButton(
            title = stringResource(id = R.string.add_new_address),
            allCaps = false,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {}
    }
}

@Preview
@Composable
fun A2ABottomSheetPreview() {
    //A2ABottomSheetDialog()
}