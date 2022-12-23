package com.a2a.app.ui.wallet.component

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.a2a.app.R
import com.a2a.app.ui.components.A2AButton
import com.a2a.app.ui.components.A2ATextField
import com.a2a.app.ui.theme.CardBg
import com.a2a.app.ui.theme.CardCornerRadius
import com.a2a.app.ui.theme.ScreenPadding
import com.a2a.app.ui.theme.SpaceBetweenViewsAndSubViews

@Composable
fun WalletRechargeDialog(
    showDialog: (Boolean) -> Unit,
    onRechargeConfirm: (Int) -> Unit
) {
    var amount by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Dialog(onDismissRequest = { showDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(CardCornerRadius),
            color = MaterialTheme.colors.CardBg
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(ScreenPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.wallet_recharge),
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))
                A2ATextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = stringResource(
                        id = R.string.enter_amount
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
                Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    A2AButton(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(100.dp)),
                        title = stringResource(id = R.string.recharge),
                        allCaps = false
                    ) {
                        if (amount.isEmpty())
                            Toast.makeText(context, "Enter amount", Toast.LENGTH_SHORT)
                                .show()
                        else {
                            onRechargeConfirm(amount.toInt())
                            showDialog(false)
                        }
                    }

                    Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

                    A2AButton(
                        modifier = Modifier
                            .weight(1f)
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(100.dp)
                            ),
                        title = stringResource(id = R.string.alert_cancel),
                        allCaps = false,
                        backgroundColor = Color.White,
                        textColor = Color.Gray
                    ) {
                        showDialog(false)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun WalletRechargePreview() {
    WalletRechargeDialog(showDialog = {}) {

    }
}