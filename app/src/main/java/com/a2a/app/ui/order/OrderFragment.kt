package com.a2a.app.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.R
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.CityModel
import com.a2a.app.data.model.OrderModel
import com.a2a.app.data.network.UserApi
import com.a2a.app.data.repository.UserRepository
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.FragmentOrderBinding
import com.a2a.app.databinding.StateEmptyBinding
import com.a2a.app.utils.AppUtils
import com.google.gson.Gson

class OrderFragment :
    BaseFragment<FragmentOrderBinding, UserViewModel, UserRepository>(FragmentOrderBinding::inflate) {

    private lateinit var orderList: List<OrderModel.Result>
    private lateinit var order: OrderModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()

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
        )

        viewModel.myOrders.observe(viewLifecycleOwner) {
            when (it) {
                is Status.Loading -> showLoading()
                is Status.Success -> {
                    stopShowingLoading()
                    order =it.value
                    orderList = it.value.result
                    setData()
                }
                is Status.Failure -> stopShowingLoading()
            }
        }
    }

    private fun setData() {
        if(orderList.isEmpty())
            showEmpty(getString(R.string.no_orders), getString(R.string.no_order_desc))
        else
            viewBinding.contentMyOrders.rvOrders.run{
                layoutManager = LinearLayoutManager(context)
                 adapter = OrderAdapter(
                     data = orderList,
                     context =context,
                     object: RvItemClick {
                         override fun clickWithPosition(name: String, position: Int) {
                            val orderDetails = Gson().toJson(orderList[position], OrderModel.Result::class.java)
                             val serverTime = order.serverTime
                             val action = OrderFragmentDirections.actionOrderFragmentToOrderDetailsFragment(
                                 orderDetails = orderDetails,
                                 serverTime = serverTime
                             )
                             findNavController().navigate(action)
                         }
                     }
                 )
            }
    }

    private fun setToolbar() {
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun showEmpty(title: String, description: String) {
        val emptyStateBinding: StateEmptyBinding = viewBinding.emptyState
        with(emptyStateBinding) {
            tvEmptyStateTitle.text = title
            tvEmptyStateDescription.text = description
            llEmptyStateLayout.visibility = View.VISIBLE
            bEmptyStateAction.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOrderBinding.inflate(inflater, container, false)

    override fun getViewModel() = UserViewModel::class.java

    override fun getFragmentRepository() = UserRepository(
        remoteDataSource.getBaseUrl().create(
            UserApi::class.java
        )
    )
}

