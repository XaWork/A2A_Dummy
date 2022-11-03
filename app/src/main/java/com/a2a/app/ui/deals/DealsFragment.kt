package com.a2a.app.ui.deals

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.OfferDealModel
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentDealsBinding
import com.a2a.app.ui.components.DialogCoupon
import com.a2a.app.ui.components.SingleDeal
import com.a2a.app.ui.theme.MainBgColor
import com.a2a.app.ui.theme.ScreenPadding
import com.a2a.app.utils.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DealsFragment : Fragment(R.layout.fragment_deals) {

    private lateinit var mainActivity: MainActivity
    private lateinit var offerDeals: OfferDealModel

    private val viewModel by viewModels<CustomViewModel>()
    private lateinit var viewBinding: FragmentDealsBinding
    private val offerAndDeals = listOf(
        DealModel("Coupon of the day", R.drawable.coupons)
    )

    @Inject
    lateinit var viewUtils: ViewUtils

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onResume() {
        super.onResume()
        mainActivity.showToolbarAndBottomNavigation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentDealsBinding.bind(view)
        //getDeals()
        setData()
    }

    private fun setData() {

        viewBinding.dealComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                DealsScreen()
            }
        }
        /* viewBinding.rvDeals.run {
             layoutManager = LinearLayoutManager(context)
             adapter = DealAdapter(offerAndDeals, context, object : RvItemClick {
                 override fun clickWithPosition(name: String, position: Int) {}
                 override fun clickWithView(name: String, position: Int, view: View) {
                     when (name) {
                         "Coupon of the day" -> {
                             getDeals()
                         }
                     }
                 }
             })
         }*/
    }

    private fun showCouponDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.coupon_items)
        val rvCoupon = dialog.findViewById<RecyclerView>(R.id.rvCoupon)
        rvCoupon.run {
            layoutManager = LinearLayoutManager(context)
            adapter = CouponAdapter(context)
        }

        dialog.setCancelable(true)
        dialog.show()
    }

    private fun getDeals() {
        viewModel.offerDeal()
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Status.Loading -> {
                        viewUtils.showLoading(parentFragmentManager)
                    }
                    is Status.Success -> {
                        viewUtils.stopShowingLoading()
                        offerDeals = result.value
                        //showCouponDialog()
                    }
                    is Status.Failure -> {
                        viewUtils.stopShowingLoading()
                    }
                }
            }
    }

    @Composable
    fun DealsScreen() {

        var showDialog by remember { mutableStateOf(false) }

        if (showDialog)
            DialogCoupon(setShowDialog = { showDialog = it })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.MainBgColor)
                .padding(ScreenPadding)
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(offerAndDeals) { deal ->
                    SingleDeal(deal) { name ->
                        when (name) {
                            "Coupon of the day" -> {
                                getDeals()
                                showDialog = true
                            }
                        }
                    }
                }
            }
        }
    }
}