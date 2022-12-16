package com.a2a.app.ui.order

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.R
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.OrderModel
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.FragmentOrderDetailsBinding
import com.a2a.app.toDate
import com.a2a.app.toDateObject
import com.a2a.app.toSummary
import com.a2a.app.ui.components.A2ATopAppBar
import com.a2a.app.ui.order.order_details.component.*
import com.a2a.app.ui.theme.*
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderDetailsFragment : Fragment(R.layout.fragment_order_details) {

    private lateinit var order: OrderModel.Result
    private lateinit var serverTime: String
    private var totalShippingCost: Float = 0f
    private var totalPackagingCost: Float = 0f
    private lateinit var viewBinding: FragmentOrderDetailsBinding
    private val viewModel by viewModels<UserViewModel>()

    @Inject
    lateinit var appUtils: AppUtils

    @Inject
    lateinit var viewUtils: ViewUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentOrderDetailsBinding.bind(view)

        getArgument()
    }

    private fun getArgument() {
        val args: OrderDetailsFragmentArgs by navArgs()
        Log.d("order details", "${args.orderDetails}")
        order = Gson().fromJson(args.orderDetails, OrderModel.Result::class.java)
        serverTime = args.serverTime.toString()

        viewBinding.orderDetailComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                A2ATheme {
                    OrderDetailsScreen()
                }
            }
        }

    }

    private fun getOrderUpdate() {
        viewModel.orderUpdate(order.id).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {
                    viewUtils.showLoading(parentFragmentManager)
                }
                is Status.Success -> {
                    viewUtils.stopShowingLoading()/*
                    with(viewBinding.incContentOrderDetails) {
                        if (result.value.updates.isNotEmpty()) {

                            updateTitle.visibility = View.VISIBLE
                            rvUpdates.visibility = View.VISIBLE
                            val items = result.value.updates.mapIndexed { index, item ->
                                when (index) {
                                    0 -> {
                                        Updates(item.toSummary(), UpdateType.ENTRY)
                                    }
                                    result.value.updates.size - 1 -> {
                                        Updates(item.toSummary(), UpdateType.EXIT)
                                    }
                                    else -> {
                                        Updates(item.toSummary(), UpdateType.INTERMEDIATE)
                                    }
                                }
                            }
                            rvUpdates.apply {
                                layoutManager = LinearLayoutManager(context!!)
                                adapter = OrderUpdateAdapter(context, items)
                            }
                        } else {
                            updateTitle.visibility = View.GONE
                            rvUpdates.visibility = View.GONE
                        }
                    }*/
                }
                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
                }
            }
        }
    }

    //----------------------------------- Compose UI ----------------------------------------------

    @Composable
    fun OrderDetailsScreen() {
        Scaffold(
            topBar = {
                A2ATopAppBar(title = "Order ${order.orderid}") {
                    findNavController().popBackStack()
                }
            },
            content = {
                Surface(
                    color = MaterialTheme.colors.MainBgColor,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    ContentOrderDetails()
                }
            }
        )
    }

    @Composable
    fun ContentOrderDetails() {
        LazyColumn(modifier = Modifier.padding(ScreenPadding)) {
            item {
                OrderItemCard(order.toOrderItemCardData())

                Spacer(modifier = Modifier.height(SpaceBetweenViews))

                Divider()

                Spacer(modifier = Modifier.height(SpaceBetweenViews))

                Text(
                    text = "Delivery Date: ${order.deliveryDate.toDate()}",
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(SpaceBetweenViews))
            }

            items(priceList()) { item ->
                PriceInfoItem(priceInfo = item)
            }

            item {
                Spacer(modifier = Modifier.height(SpaceBetweenViews))
                Divider()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = SpaceBetweenViews),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Final Total: ",
                        style = MaterialTheme.typography.h6,
                        color = Color.Black
                    )
                    Text(
                        text = "Rs. ${finalCost()}",
                        style = MaterialTheme.typography.h6,
                        color = Color.Black
                    )
                }

                Divider()

                Spacer(modifier = Modifier.height(SpaceBetweenViews))

                Text(
                    text = "Updates",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(SpaceBetweenViews))
            }

            items(orderServiceList()) { service ->
                OrderService(service, onClick = { serviceName ->
                    when (serviceName) {
                        "live_temperature" -> {}
                        "pickup_video" -> {}
                        "delivery_video" -> {}
                        "pickup_photo" -> {}
                        "delivery_photo" -> {}
                    }
                })
            }

            item {
                Spacer(modifier = Modifier.height(SpaceBetweenViews))

                Text(
                    text = "Shipping To",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(SpaceBetweenViews))

                Text(
                    text = "${order.user.address}\n ${order.user.city} ${order.user.state} ${order.user.zipcode} \n${order.user.country}",
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .clip(RoundedCornerShape(CardCornerRadius))
                        .padding(ScreenPadding)
                )
            }
        }
    }

    private fun priceList(): List<PriceInfo> {
        return listOf(
            PriceInfo(
                tag = getString(R.string.price),
                price = order.finalprice
            ),
            PriceInfo(
                tag = getString(R.string.video_recording_pd),
                price = "0"
            ),
            PriceInfo(
                tag = getString(R.string.picture_pd),
                price = "0"
            ),
            PriceInfo(
                tag = getString(R.string.live_gps_tracking),
                price = "0"
            ),
            PriceInfo(
                tag = getString(R.string.live_temperature_tracking),
                price = "0"
            ),
            PriceInfo(
                tag = getString(R.string.delivery_charges),
                price = order.totalShippingPrice
            ),
            PriceInfo(
                tag = getString(R.string.packaging_fee),
                price = order.totalPackingPrice
            ),
            PriceInfo(
                tag = getString(R.string.cgst),
                price = order.cgst
            ),
            PriceInfo(
                tag = getString(R.string.sgst),
                price = order.sgst
            ),
            PriceInfo(
                tag = getString(R.string.igst),
                price = order.igst
            ),
            PriceInfo(
                tag = getString(R.string.discount),
                price = "0"
            ),
            PriceInfo(
                tag = getString(R.string.subscription_discount),
                price = "0"
            ),
            PriceInfo(
                tag = getString(R.string.wallet_discount),
                price = order.walletDiscount
            ),
        )
    }

    private fun finalCost(): Float {
        return order.run {
            finalprice.toFloat() +
                    cgst.toFloat() +
                    igst.toFloat() +
                    sgst.toFloat() +
                    walletDiscount.toFloat() +
                    totalShippingCost +
                    totalPackagingCost
        }
    }

    private fun orderServiceList(): List<OrderServiceData> {
        val serviceList = ArrayList<OrderServiceData>()
        serviceList.add(
            OrderServiceData(
                id = "track_order",
                name = "Track Order",
                enable = order.liveTracking == "Y"
            )
        )
        serviceList.add(
            OrderServiceData(
                id = "live_temperature",
                name = "Live Temperature: ${order.temparature}",
                enable = order.liveTemparature == "Y"
            )
        )
        serviceList.add(
            OrderServiceData(
                id = "pickup_video",
                name = "View Video Recording of Pickup",
                enable = order.videoRecording == "Y"
            )
        )
        serviceList.add(
            OrderServiceData(
                id = "delivery_video",
                name = "View Video Recording of Delivery",
                enable = order.videoRecording == "Y"
            )
        )
        serviceList.add(
            OrderServiceData(
                id = "pickup_photo",
                name = "View Photo of Pickup",
                enable = order.pictureRecording == "Y"
            )
        )
        serviceList.add(
            OrderServiceData(
                id = "delivery_photo",
                name = "View Photo of Delivery",
                enable = order.pictureRecording == "Y"
            )
        )

        return serviceList
    }
}