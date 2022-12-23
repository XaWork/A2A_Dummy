package com.a2a.app.ui.my_plan.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a2a.app.data.model.WalletDataModel
import com.a2a.app.toDate
import com.a2a.app.ui.components.HeadingText
import com.a2a.app.ui.components.PlanButton
import com.a2a.app.ui.components.ShortDescriptionText
import com.a2a.app.ui.theme.*

@Composable
fun MyPlanItem(
    allPlans: WalletDataModel,
    onUpgradeClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(CardCornerRadius))
            .background(color = MaterialTheme.colors.CardBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .background(color = MaterialTheme.colors.primary),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "${allPlans.plan!!.planName}\n " +
                        "${stringResource(id = com.a2a.app.R.string.Rs)}${allPlans.plan.planPrice}",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "OR",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            PlanButton(title = "Upgrade") {
                onUpgradeClick()
            }
        }

        Column(
            modifier = Modifier
                .weight(2f)
                .padding(LowPadding)
        ) {
            HeadingText(text = "usage", allCaps = true)

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            ShortDescriptionText(text = allPlans.customerPoint.toString())

            Spacer(modifier = Modifier.height(SpaceBetweenViews))

            Divider()

            Spacer(modifier = Modifier.height(SpaceBetweenViews))

            HeadingText(text = "Expiry Date")

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            ShortDescriptionText(
                text = allPlans.plan!!.expDate!!.toDate("MMMM dd, yyyy"),
                fontSize = 10.sp
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViews))

            Divider()

            Spacer(modifier = Modifier.height(SpaceBetweenViews))

            HeadingText(text = "Benefits")

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            ShortDescriptionText(
                text = allPlans.plan.benefits,
                fontSize = 10.sp
            )

        }
    }
}

@Preview
@Composable
fun MyPlanItemPreview() {
    // MyPlanItem(allPlans)
}