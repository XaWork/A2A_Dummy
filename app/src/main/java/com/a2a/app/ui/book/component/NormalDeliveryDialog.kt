package com.a2a.app.ui.book.component

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.a2a.app.R
import com.a2a.app.databinding.DialogNormalDeliveryBinding
import com.a2a.app.ui.book.BookEvent
import com.a2a.app.ui.book.BookViewModel
import com.a2a.app.ui.components.A2ADateTimeTextField
import com.a2a.app.ui.components.A2ADropDown
import com.a2a.app.ui.components.A2ATextField
import com.a2a.app.ui.theme.*

@Composable
fun NormalDeliveryDialog(
    showDialog: Boolean = false,
    onDialogValueChange: () -> Unit,
    //viewModel: BookViewModel
) {

    Dialog(onDismissRequest = { onDialogValueChange() }) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(CardCornerRadius),
            color = MaterialTheme.colors.CardBg
        ) {
            Column(modifier = Modifier.padding(ScreenPadding)) {
                Text(
                    text = stringResource(id = R.string.booking),
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            }
        }
    }
}

@Preview
@Composable
fun DialogNormalDeliveryPreview() {
    NormalDeliveryDialog(onDialogValueChange = { /*TODO*/ })
}