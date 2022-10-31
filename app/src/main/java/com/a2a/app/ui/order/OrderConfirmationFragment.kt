package com.a2a.app.ui.order

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.window.OnBackInvokedCallback
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.a2a.app.R
import com.a2a.app.common.Status
import com.a2a.app.data.model.ConfirmBookingModel
import com.a2a.app.data.viewmodel.UserViewModel
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

    private val viewModel by viewModels<UserViewModel>()
    private var price: Float = 0.0f
    private var callbackCount = 0
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private lateinit var viewBinding: FragmentOrderConfirmationBinding
    private lateinit var orderConfirmationData: OrderConfirmationData
    private lateinit var confirmBookingModel: ConfirmBookingModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding = FragmentOrderConfirmationBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        // handle back pressed
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (this@OrderConfirmationFragment::runnable.isInitialized) {
                handler.removeCallbacks(runnable)
            }
            findNavController().navigate(R.id.action_global_bookFragment)
        }

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

                tvPrice.text =
                    "Rs. ${estimateBookingResponse.estimations.estimated_price}"
                tvBookingPrice.text =
                    "Rs. ${estimateBookingResponse.estimations.booking_price}"
                tvPickupCost.text =
                    "Rs. ${estimateBookingResponse.estimations.pickup_price}"
                tvPriceVideo.text =
                    "Rs. ${estimateBookingResponse.estimations.video_recording}"
                tvPricePicture.text =
                    "Rs. ${estimateBookingResponse.estimations.picture_recording}"
                tvPriceLiveGPSTracking.text =
                    "Rs. ${estimateBookingResponse.estimations.live_tracking}"
                tvPriceLiveTemperatureTracking.text =
                    "Rs. ${estimateBookingResponse.estimations.live_temparature}"
                tvItemCost.text =
                    "Rs. ${estimateBookingResponse.estimations.estimated_price}"
                tvCgst.text =
                    "Rs. ${estimateBookingResponse.estimations.estimated_price_with_gst.cgst}"
                tvIgst.text =
                    "Rs. ${estimateBookingResponse.estimations.estimated_price_with_gst.igst}"
                tvSgst.text =
                    "Rs. ${estimateBookingResponse.estimations.estimated_price_with_gst.cgst}"
                tvEstimatePriceWithGst.text =
                    "Rs. ${estimateBookingResponse.estimations.estimated_price_with_gst.estimated_price}"

                tvDeliveryType.text = "$deliveryType Delivery"

                /*val pickupDate = estimateBookingResponse.estimations[0].pickup.pickup_date.toDate(
                    "dd/MM/yyyy",
                    "yyyy-MM-dd"
                )
                val pickupTime = estimateBookingResponse.estimations[0].pickup.time*/
                if (deliveryType == "Normal")
                    tvPickupDateTime.text =
                        "$pickupDate ($pickupTime)"
                else{
                    pickupDatetitle.visibility = View.GONE
                    tvPickupDateTime.visibility = View.GONE
                }

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
                    estimateBookingResponse.estimations.estimated_price_with_gst.estimated_price.toFloat() +
                            estimateBookingResponse.estimations.live_tracking +
                            estimateBookingResponse.estimations.live_temparature +
                            estimateBookingResponse.estimations.picture_recording + 8907
                estimateBookingResponse.estimations.video_recording

                finalFee.text =
                    estimateBookingResponse.estimations.estimated_price_with_gst.estimated_price

            }
        }
    }

    private fun setToolbar() {
        with(viewBinding.incToolbar) {
            toolbar.title = "Order Confirmation"
            toolbar.setNavigationOnClickListener {
                findNavController().navigate(R.id.action_global_bookFragment)
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
            price = orderConfirmationData.estimateBookingResponse.estimations.estimated_price,
            finalPrice = orderConfirmationData.estimateBookingResponse.estimations.estimated_price,
            timeslot = "",
            videoRecording = orderConfirmationData.videoRecording,
            pictureRecording = orderConfirmationData.pictureRecording,
            liveTemparature = orderConfirmationData.liveTemperature,
            liveTracking = orderConfirmationData.liveTracking,
            sgst = orderConfirmationData.estimateBookingResponse.estimations.estimated_price_with_gst.sgst,
            cgst = orderConfirmationData.estimateBookingResponse.estimations.estimated_price_with_gst.cgst,
            igst = orderConfirmationData.estimateBookingResponse.estimations.estimated_price_with_gst.igst,
        ).observe(viewLifecycleOwner) {
            when (it) {
                is Status.Loading -> {
                    //viewUtils.showLoading(parentFragmentManager)
                    if (orderConfirmationData.deliveryType == "Normal")
                        viewUtils.showLoading(parentFragmentManager)
                    else
                        viewUtils.showLoading(
                            parentFragmentManager,
                            "Please wait",
                            "Searching for pickup boy...",
                            true
                        )
                }
                is Status.Success -> {
                    //viewUtils.stopShowingLoading()
                    confirmBookingModel = it.value

                    if (orderConfirmationData.deliveryType == "Normal") {
                        viewUtils.stopShowingLoading()
                        moveToBookingConfirmedScreen()
                    } else {
                        checkOrderStatus()
                        handler.postDelayed(Runnable {//do something
                            callbackCount += 1
                            checkOrderStatus()
                            handler.postDelayed(runnable, 10000)
                        }.also { call -> runnable = call }, 10000)
                    }
                }
                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
                }
            }
        }
    }

    private fun checkOrderStatus() {
        val orderId = confirmBookingModel.orderId
        viewModel.checkOrderStatus(orderId).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {}
                is Status.Success -> {
                    Log.e("order confirmation", "Call back count : $callbackCount")
                    if (callbackCount == 6) {
                        viewUtils.stopShowingLoading()
                        handler.removeCallbacks(runnable)
                        viewUtils
                            .showLongToast("Sorry, Pickup Boy is Not Available in selected range. Please increase the pickup Range & Try to Book Again.")
                    } else {
                        when (result.value.message) {
                            "waiting" -> {}
                            "pickup_boy_assigned" -> {
                                handler.removeCallbacks(runnable)
                                viewUtils.stopShowingLoading()
                                moveToBookingConfirmedScreen()
                            }
                        }
                    }
                }
                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
                }
            }
        }
    }

    private fun moveToBookingConfirmedScreen() {
        val confirmBooking =
            Gson().toJson(confirmBookingModel, ConfirmBookingModel::class.java)
        val action =
            OrderConfirmationFragmentDirections.actionOrderConfirmationFragmentToBookingConfrimFragment(
                confirmation = confirmBooking,
                deliveryDateTime = "${orderConfirmationData.deliveryDate} (${orderConfirmationData.deliveryTime})"
            )
        findNavController().navigate(action)
    }

    override fun onPause() {
        super.onPause()
        if (this::runnable.isInitialized)
            handler.removeCallbacks(runnable)
    }
}