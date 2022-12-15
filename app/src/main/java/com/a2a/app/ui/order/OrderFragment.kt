package com.a2a.app.ui.order

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.common.Status
import com.a2a.app.data.model.OrderModel
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.FragmentOrderBinding
import com.a2a.app.ui.components.A2AButton
import com.a2a.app.ui.components.A2ATopAppBar
import com.a2a.app.ui.theme.A2ATheme
import com.a2a.app.ui.theme.MainBgColor
import com.a2a.app.ui.theme.ScreenPadding
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderFragment : Fragment(R.layout.fragment_order) {

    private lateinit var orderList: List<OrderModel.Result>
    private lateinit var order: OrderModel
    private lateinit var viewBinding: FragmentOrderBinding
    private val viewModel by viewModels<UserViewModel>()

    @Inject
    lateinit var viewUtils: ViewUtils

    @Inject
    lateinit var appUtils: AppUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentOrderBinding.bind(view)

        if (this::orderList.isInitialized)
            setData()
        else
            getOrders()

    }

    private fun getOrders() {
        viewModel.getMyOrders(
            userId = AppUtils(context!!).getUser()!!.id,
            page = "0",
            perPage = "10"
        ).observe(viewLifecycleOwner) {
            when (it) {
                is Status.Loading -> viewUtils.showLoading(parentFragmentManager)
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    order = it.value
                    orderList = it.value.result
                    setData()
                }
                is Status.Failure -> viewUtils.stopShowingLoading()
            }
        }
    }

    private fun setData() {
        viewBinding.orderComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                A2ATheme {
                    OrderScreen()
                }
            }
        }
    }

    private fun moveToOrderDetailsScreen(ord: OrderModel.Result) {
        val orderDetails =
            Gson().toJson(ord, OrderModel.Result::class.java)
        val serverTime = order.serverTime
        val action =
            OrderFragmentDirections.actionOrderFragmentToOrderDetailsFragment(
                orderDetails = orderDetails,
                serverTime = serverTime
            )
        findNavController().navigate(action)
    }

    private fun moveToServiceTypeScreen() {
        findNavController().navigate(R.id.action_global_serviceTypeFragment)
    }

    @Composable
    fun OrderScreen() {
        Scaffold(topBar = {
            A2ATopAppBar(title = "My Booking") {
                findNavController().popBackStack()
            }
        }, content = {
            ContentOrder()
        })
    }

    @Composable
    fun ContentOrder() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.MainBgColor)
                .padding(ScreenPadding),
            contentAlignment = Alignment.Center
        ) {
            if (orderList.isEmpty())
                NoOrderFound {
                    moveToServiceTypeScreen()
                }
            else {
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)) {
                    items(orderList) { order ->
                        SingleOrder(order = order, onClick = {
                            moveToOrderDetailsScreen(order)
                        })
                    }
                }
            }

            A2AButton(
                title = "Done",
                allCaps = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(
                        Alignment.BottomCenter
                    )
            ) {
                findNavController().navigate(R.id.action_global_homeFragment)
            }
        }
    }
}

