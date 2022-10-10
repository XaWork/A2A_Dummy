package com.a2a.app.ui.order

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.a2a.app.R
import com.a2a.app.common.Status
import com.a2a.app.data.model.ConfirmBookingModel
import com.a2a.app.data.viewmodel.UserViewModel1
import com.a2a.app.databinding.FragmentOrderConfirmationBinding
import com.a2a.app.toDate
import com.a2a.app.ui.book.OrderConfirmationData
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class OrderConfirmationFragment : Fragment(R.layout.fragment_order_confirmation) {

    @Inject
    lateinit var appUtils: AppUtils

    @Inject
    lateinit var viewUtils: ViewUtils

    private val viewModel by viewModels<UserViewModel1>()
    private var price: Float = 0.0f
    private lateinit var viewBinding: FragmentOrderConfirmationBinding
    private lateinit var orderConfirmationData: OrderConfirmationData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentOrderConfirmationBinding.bind(view)

        getArgument()
        setToolbar()

        //set data
        with(viewBinding.incContentOrderDetails) {
            tvConfirm.setOnClickListener {
                confirmBooking()
            }
        }
    }

    private fun getArgument() {
        val args: OrderConfirmationFragmentArgs by navArgs()
        orderConfirmationData =
            Gson().fromJson(args.orderConfirmationData, OrderConfirmationData::class.java)
        setData()
    }

    private fun setData() {
        with(viewBinding.incContentOrderDetails) {
            orderConfirmationData.run {
                tvCatAndSubCatName.text =
                    "Category: ${category.name}, ${subCategory.name.uppercase()}"
                tvWeight.text = "Weight: ${weight}kg"


                tvItemCost.text =
                    "Rs. ${estimateBookingResponse.estimations[0].pickup.estimated_price}"
                tvCgst.text =
                    "Rs. ${estimateBookingResponse.estimations[0].pickup.estimated_price_with_gst.cgst}"
                tvIgst.text =
                    "Rs. ${estimateBookingResponse.estimations[0].pickup.estimated_price_with_gst.igst}"
                tvSgst.text =
                    "Rs. ${estimateBookingResponse.estimations[0].pickup.estimated_price_with_gst.cgst}"

                tvDeliveryType.text = "$deliveryType Delivery"

                /*val pickupDate = estimateBookingResponse.estimations[0].pickup.pickup_date.toDate(
                    "dd/MM/yyyy",
                    "yyyy-MM-dd"
                )
                val pickupTime = estimateBookingResponse.estimations[0].pickup.time*/
                tvPickupDateTime.text =
                    "$pickupDate ($pickupTime)"

                /*val deliveryDate = estimateBookingResponse.estimations[0].delivery.delivery_date.toDate(
                    "dd/MM/yyyy",
                    "yyyy-MM-dd"
                )
                val deliveryTime = estimateBookingResponse.estimations[0].delivery.delivery_slot*/

                Log.e(
                    "Order Confirmation",
                    "delivery date : $deliveryDate\n delivery time : $deliveryTime"
                )

                tvDeliveryDateTime.text =
                    "$deliveryDate ($deliveryTime)"

                price =
                    estimateBookingResponse.estimations[0].pickup.estimated_price_with_gst.estimated_price

                finalFee.text = price.toString()

            }
        }
    }

    private fun setToolbar() {
        with(viewBinding.incToolbar) {
            toolbar.title = "Order Confirmation"
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun confirmBooking() {
        val sdfDate = SimpleDateFormat("MM/dd/yyyy")
        val sdfTime = SimpleDateFormat("HH:mm")
        val calendar = Calendar.getInstance()
        val currentDate = sdfDate.format(calendar.time)
        val currentTime = sdfTime.format(calendar.time)

        val userId = AppUtils(context!!).getUser()?.id
        viewModel.confirmBooking(
            userId = userId!!,
            pickupAddress = "61baeddf53355a0009697687",
            destinationAddress = "61ae19f4630eda331431616d",
            category = orderConfirmationData.category.id,
            subCategory = orderConfirmationData.subCategory.id,
            pickupRange = orderConfirmationData.pickupRange,
            weight = orderConfirmationData.weight,
            width = orderConfirmationData.width,
            height = orderConfirmationData.height,
            length = orderConfirmationData.length,
            pickupType = orderConfirmationData.deliveryType,
            deliveryType = orderConfirmationData.deliveryType,
            scheduleTime = when (orderConfirmationData.deliveryTime) {
                "Normal" -> {
                    orderConfirmationData.pickupTime
                }
                else -> {
                    currentTime
                }
            },
            scheduleDate = when (orderConfirmationData.deliveryType) {
                "Normal" -> {
                    orderConfirmationData.pickupDate.toDate("yyyy-MM-dd", "dd/MM/yyyy")
                }
                else -> {
                    currentDate
                }
            },
            price = orderConfirmationData.estimateBookingResponse.estimations[0].pickup.estimated_price.toString(),
            finalPrice = orderConfirmationData.estimateBookingResponse.estimations[0].pickup.estimated_price.toString(),
            timeslot = orderConfirmationData.estimateBookingResponse.estimations[0].pickup.time,
            videoRecording = orderConfirmationData.videoRecording,
            pictureRecording = orderConfirmationData.pictureRecording,
            liveTemparature = orderConfirmationData.liveTemperature,
            liveTracking = orderConfirmationData.liveTracking
        ).observe(viewLifecycleOwner) {
            when (it) {
                is Status.Loading -> {
                    viewUtils.showLoading(parentFragmentManager)
                }
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    if (it.value.status == "success") {
                        val stringResponse =
                            Gson().toJson(it.value, ConfirmBookingModel::class.java)
                        val action =
                            OrderConfirmationFragmentDirections.actionOrderConfirmationFragmentToBookingConfrimFragment(
                                stringResponse
                            )
                        findNavController().navigate(action)
                    }
                }
                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
                }
            }
        }
    }
}