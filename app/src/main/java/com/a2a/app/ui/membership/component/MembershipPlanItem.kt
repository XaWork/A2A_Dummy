package com.a2a.app.ui.membership.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.a2a.app.R
import com.a2a.app.data.model.AllPlanModel
import com.a2a.app.ui.components.A2AButton
import com.a2a.app.ui.theme.CardBg
import com.a2a.app.ui.theme.CardCornerRadius
import com.a2a.app.ui.theme.MediumPadding
import com.a2a.app.ui.theme.SpaceBetweenViewsAndSubViews
import kotlinx.coroutines.NonDisposableHandle.parent

@Composable
fun MembershipPlanItem(
    plan: AllPlanModel.Result,
    onBuyNowClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(color = MaterialTheme.colors.CardBg)
            .clip(RectangleShape),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .background(color = MaterialTheme.colors.primary),
            contentAlignment = Center
        ) {
            Text(
                text = plan.name.uppercase(),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

        Column(
            modifier = Modifier.weight(4f),
            verticalArrangement = Arrangement.Center
        ) {
            val description: AnnotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("Description\n")
                }

                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontSize = 10.sp,
                    )
                ) {
                    append(plan.description)
                }
            }

            Text(
                text = description,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            Row {
                val benefits: AnnotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Benefits\n")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontSize = 10.sp,

                            )
                    ) {
                        var benefitCities = ""
                        plan.city.forEach { benefitCities += "${it.name}, " }
                        append(benefitCities)
                    }
                }

                Text(
                    text = benefits,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(3f)
                )

                Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

                Text(
                    text = "Validity\n${plan.day}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(2f)
                )
            }
        }

        Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(2f)
                .background(Color.Gray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val planPriceInfo: AnnotatedString = buildAnnotatedString {
                append("Plan\n")
                withStyle(
                    style = SpanStyle(
                        fontSize = 18.sp
                    )
                ) {
                    append("Rs ${plan.price}")
                }
            }

            Text(
                text = planPriceInfo,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            Text(
                text = "Buy Now",
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(5.dp)
                    .background(Color.White)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .clickable { onBuyNowClick() }
            )
        }
    }

    /*ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (planType, info, buy) = createRefs()
        val startGuideline = createGuidelineFromStart(0.25f)
        val endGuideline = createGuidelineFromEnd(0.25f)

        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colors.primary)
                .constrainAs(planType) {
                    top.linkTo(parent.top)
                    end.linkTo(startGuideline)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }, contentAlignment = Alignment.Center
        ) {
            Text(
                text = "SILVER",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }

        //Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

        Column(
            modifier = Modifier.constrainAs(info) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
            },
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Description",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = stringResource(id = R.string.lorem),
                maxLines = 3,
                color = Color.Gray,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

             Row {
                 val benefits: AnnotatedString = buildAnnotatedString {
                     withStyle(
                         style = SpanStyle(
                             color = Color.Gray,
                             fontWeight = FontWeight.Bold,
                         )
                     ) {
                         append("Benefits\n")
                     }

                     withStyle(
                         style = SpanStyle(
                             color = Color.Black,
                             fontSize = 10.sp,

                             )
                     ) {
                         append(stringResource(id = R.string.lorem))
                     }
                 }

            Text(
                text = "Benefits",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = stringResource(id = R.string.lorem),
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
            )

            Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

              Text(
                  text = "Validity\n365 Days",
                  color = Color.Black,
                  fontWeight = FontWeight.Bold,
              )
          }
        }

        //Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

        Column(
            modifier = Modifier
                .constrainAs(buy) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(endGuideline)
                }
                .background(Color.Gray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val plan: AnnotatedString = buildAnnotatedString {
                append("Plan\n")
                withStyle(
                    style = SpanStyle(
                        fontSize = 18.sp
                    )
                ) {
                    append("Rs 2000")
                }
            }

            Text(
                text = plan,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            A2AButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                title = "Buy Now",
                allCaps = false,
                textColor = Color.Black
            ) {

            }
        }
    }*/
}

@Preview
@Composable
fun MembershipPlanItemPreview() {
    //MembershipPlanItem()
    //Example()
}

@Preview
@Composable
fun ExamplePreview() {
    // Example()
}

@Composable
fun Example() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

        val (text, info, text3) = createRefs()
        val startGuideline = createGuidelineFromStart(0.25f)
        val endGuideline = createGuidelineFromEnd(0.25f)

        Text(
            text = "text",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(startGuideline)
                }
                .background(color = MaterialTheme.colors.primary)
        )

        Text(
            text = stringResource(id = R.string.lorem),
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .wrapContentWidth()
                .constrainAs(info) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                }
                .background(color = Color.Red)
        )

        Text(
            text = "info",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(text3) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(endGuideline)
                    end.linkTo(parent.end)
                }
                .background(color = Color.Black)
        )

    }
}