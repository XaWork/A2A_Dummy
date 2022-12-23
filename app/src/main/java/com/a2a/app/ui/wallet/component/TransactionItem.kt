package com.a2a.app.ui.wallet.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.a2a.app.R
import com.a2a.app.data.model.WalletTransactionModel
import com.a2a.app.ui.theme.CardCornerRadius
import com.a2a.app.ui.theme.CardElevation
import com.a2a.app.ui.theme.SpaceBetweenViewsAndSubViews

@Composable
fun TransactionItem(walletTransaction: WalletTransactionModel.Transaction) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.bullet_icon),
                contentDescription = ""
            )

            Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

            Column {
                Text(
                    text = if (walletTransaction.type == 0) "Points Earned" else "Points Redeemed",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Text(
                    text = walletTransaction.note,
                    fontSize = 12.sp,
                    color = Color.Gray,
                )
            }

            Text(
                text = "${walletTransaction.point} Points",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .align(Alignment.CenterVertically)
            )
        }
}

@Preview
@Composable
fun TransactionItemPreview() {
    //TransactionItem(walletTransaction)
}