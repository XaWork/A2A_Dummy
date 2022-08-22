package com.a2a.app.ui.order

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.common.BaseFragment
import com.a2a.app.data.model.OrderModel
import com.a2a.app.data.network.UserApi
import com.a2a.app.data.repository.UserRepository
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.FragmentOrderDetailsBinding
import com.a2a.app.toDateObject
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.Utils
import com.google.gson.Gson


class OrderDetailsFragment :
    BaseFragment<FragmentOrderDetailsBinding, UserViewModel, UserRepository>(
        FragmentOrderDetailsBinding::inflate
    ) {

    private lateinit var order: OrderModel.Result
    private lateinit var serverTime: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
        getArgument()
        setData()

        with(viewBinding.incContentOrderDetails){
            tvTrackOrder.setOnClickListener {
                if (order.status?.contentEquals("delivery_boy_started")!!) {

                } else {
                    showTrackDialog()
                }
            }
        }
    }

    private fun setToolbar() {
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getArgument() {
        val args : OrderDetailsFragmentArgs by navArgs()
        order = Gson().fromJson(args.orderDetails, OrderModel.Result::class.java)
        serverTime = args.serverTime.toString()
    }

    private fun setData(){
        val serverTimeDate = serverTime.toDateObject("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val createdAt = order.createdDate.toDateObject("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val diffHours: Long = (serverTimeDate.time-createdAt.time) / (60 * 60 * 1000)

        with(viewBinding.incContentOrderDetails){
            if(diffHours<4 && !order.status.contentEquals("cancel")){
                btnCancelOrder.visibility = View.VISIBLE
            }else{
                btnCancelOrder.visibility = View.GONE
            }

            if(order.status.contentEquals("cancel")){
                tvTrackOrder.visibility = View.GONE
            }else{
                tvTrackOrder.visibility = View.VISIBLE
            }

            //set checkout adapter
            rvCheckOutCart.apply {
                layoutManager = LinearLayoutManager(context)
                /*adapter = CheckoutAdapter(
                    context = context,
                    order.price
                )*/
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

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOrderDetailsBinding.inflate(inflater, container, false)

    override fun getViewModel() = UserViewModel::class.java

    override fun getFragmentRepository() = UserRepository(
        remoteDataSource.getBaseUrl().create(
            UserApi::class.java
        )
    )
}

