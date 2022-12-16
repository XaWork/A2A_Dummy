package com.a2a.app.ui.membership.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.a2a.app.R
import com.a2a.app.ui.components.A2AButton
import com.a2a.app.ui.theme.CardBg
import com.a2a.app.ui.theme.MediumPadding
import com.a2a.app.ui.theme.SpaceBetweenViewsAndSubViews

@Composable
fun MembershipPlanItem() {
    /*Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.CardBg)
            .clip(RectangleShape),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "SILVER",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(2f)
                .background(color = MaterialTheme.colors.primary)
        )

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
                    append(stringResource(id = R.string.lorem))
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
                        append(stringResource(id = R.string.lorem))
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
                    text = "Validity\n365 Days",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(2f)
                )
            }
        }

        Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

        Column(
            modifier = Modifier
                .weight(2f)
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

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (planType, info, description, benefits, validity, plan, buy) = createRefs()
        val startGuideline = createGuidelineFromStart(0.25f)
        val endGuideline = createGuidelineFromEnd(0.75f)

        Text(
            text = "SILVER",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(color = MaterialTheme.colors.primary)
                .constrainAs(planType) {
                    top.linkTo(parent.top)
                    end.linkTo(startGuideline)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
        )

        Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

        Column(
            modifier = Modifier.constrainAs(info) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
            },
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
                    append(stringResource(id = R.string.lorem))
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
                        append(stringResource(id = R.string.lorem))
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
                    text = "Validity\n365 Days",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(2f)
                )
            }
        }

        Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))

        /*Column(
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
        }*/
    }
}

@Preview
@Composable
fun MembershipPlanItemPreview() {
    MembershipPlanItem()
}