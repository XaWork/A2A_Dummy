package com.a2a.app.ui.order

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.R
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.OrderModel
import com.a2a.app.data.model.tIGST
import com.a2a.app.data.model.tSGST
import com.a2a.app.data.network.UserApi
import com.a2a.app.data.repository.UserRepository
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.data.viewmodel.UserViewModel1
import com.a2a.app.databinding.FragmentOrderBinding
import com.a2a.app.databinding.FragmentOrderDetailsBinding
import com.a2a.app.toDate
import com.a2a.app.toDateObject
import com.a2a.app.toSummary
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.Utils
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
    private val viewModel by viewModels<UserViewModel1>()

    @Inject
    lateinit var appUtils: AppUtils

    @Inject
    lateinit var viewUtils: ViewUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentOrderDetailsBinding.bind(view)

        getArgument()
        setToolbar()

        /* with(viewBinding.incContentOrderDetails) {
             tvTrackOrder.setOnClickListener {
                 if (order.status?.contentEquals("delivery_boy_started")!!) {

                 } else {
                     showTrackDialog()
                 }
             }
         }*/
    }

    private fun setToolbar() {
        viewBinding.incToolbar.toolbar.title = "Order : ${order.orderid}"
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getArgument() {
        val args: OrderDetailsFragmentArgs by navArgs()
        Log.d("order details", "${args.orderDetails}")
        order = Gson().fromJson(args.orderDetails, OrderModel.Result::class.java)
        serverTime = args.serverTime.toString()
        setData()

    }

    private fun setData() {
        try {
            val serverTimeDate = serverTime.toDateObject("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val createdAt = order.createdDate.toDateObject("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val diffHours: Long = (serverTimeDate.time - createdAt.time) / (60 * 60 * 1000)

            with(viewBinding.incContentOrderDetails) {
                /*if (diffHours < 4 && !order.status.contentEquals("cancel")) {
                    btnCancelOrder.visibility = View.VISIBLE
                } else {
                    btnCancelOrder.visibility = View.GONE
                }

                if (order.status.contentEquals("cancel")) {
                    tvTrackOrder.visibility = View.GONE
                } else {
                    tvTrackOrder.visibility = View.VISIBLE
                }*/

                //set service
                val serviceList = ArrayList<String>()
                //if (!order.status.contentEquals("cancel"))
                serviceList.add("Track Order")
                serviceList.add("Live Temperature: ")
                serviceList.add("View Video Recording of Pickup")
                serviceList.add("View Video Recording of Delivery")
                serviceList.add("View Photo of Pickup")
                serviceList.add("View Photo of Delivery")

                if (serviceList.isEmpty())
                    rvServices.visibility = View.GONE
                else
                    setServices(serviceList)

                //set category, subcategory and price
                //tvTitle.text = order.category
                tvWeight.text = "Weight: ${order.totalWeight}Kg"
                tvTotalItemCost.text = "Rs ${order.finalprice}"

                deliveryDate.text =
                    "Delivery Date: ${order.deliveryDate.toDate("dd-MM-yyyy")} ${order.deliveryType}"

                //update shipping info
                //totalShippingCost = order.totalShippingPrice!!.toFloat()
                //totalPackagingCost = order.totalPackingPrice!!.toFloat()
                //tvTotalItemCostValue.text = getString(R.string.Rs_double, order.price.toDouble())

                dFee.text = getString(R.string.Rs_double, totalShippingCost)
                packingPrice.text = "Rs  $totalPackagingCost"

                if ((order.tIGST().toFloat() != 0f)) {
                    //llIGSTLayout.visibility = View.VISIBLE
                    //tvIGSTCost.text = "Rs ${order.tIGST().toFloat()}"
                } else {
                    //llSGSTLayout.visibility = View.VISIBLE
                    //llCGSTLayout.visibility = View.VISIBLE
                    //tvSGSTCost.text = getString(R.string.Rs_double, order.tSGST().toFloat())
                    //tvCGSTCost.text = getString(R.string.Rs_double, order.tIGST().toFloat())
                }

                tvTotal.text = order.finalprice


                //update shipping address
                val userAddress = buildString {
                    appendln(order.user.address)
                    //appendln(order.user.address2)
                    appendln(order.user.city)
                    appendln(order.user.state)
                    //append("Pin: ${order.user}")
                }
                tvShippingAddress.text = userAddress.trim()
                tvShippingAddressEdit.visibility = View.GONE

            }

            getOrderUpdate()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setServices(serviceList: ArrayList<String>) {
        viewBinding.incContentOrderDetails.rvServices.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = OrderServiceAdapter(
                data = serviceList,
                context = context,
                itemClick = object : RvItemClick {
                    override fun clickWithPosition(name: String, position: Int) {
                        when (name) {
                            "Track Order" -> {
                                val action =
                                    OrderDetailsFragmentDirections.actionOrderDetailsFragmentToTrackLocationFragment(
                                        order = Gson().toJson(order, OrderModel.Result::class.java)
                                    )
                                findNavController().navigate(action)
                            }
                        }
                    }
                }
            )
        }
    }

    private fun getOrderUpdate() {
        viewModel.orderUpdate(order.id).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {
                    viewUtils.showLoading(parentFragmentManager)
                }
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    with(viewBinding.incContentOrderDetails) {
                        if (!result.value.updates.isNullOrEmpty()) {

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
                    }
                }
                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
                }
            }
        }
    }

    private fun showTrackDialog() {
        /* var dialog: Dialog? = null
         val handleButtonClick = View.OnClickListener {
             dialog?.dismiss()
         }
         dialog = Utils.showDialog(
             context!!,
             AppUtils(context!!).appData,
             Utils.COMPANION_TRACK,
             handleButtonClick
         )
         dialog.show()*/
    }
}

