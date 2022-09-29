package com.a2a.app.ui.deals

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.OfferDealModel
import com.a2a.app.data.network.CustomApi
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentCityBinding
import com.a2a.app.databinding.FragmentDealsBinding
import com.a2a.app.ui.common.CommonAdapter


class DealsFragment :
    BaseFragment<FragmentDealsBinding, CustomViewModel, CustomRepository>(FragmentDealsBinding::inflate) {

    private lateinit var mainActivity: MainActivity
    private lateinit var offerDeals: OfferDealModel

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
        //getDeals()
        setData()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDealsBinding.inflate(inflater, container, false)

    override fun getViewModel() = CustomViewModel::class.java

    override fun getFragmentRepository() =
        CustomRepository(remoteDataSource.getBaseUrl().create(CustomApi::class.java))

    private fun setData() {
        val offerAndDeals = listOf(
            DealModel("Coupon of the day", R.drawable.coupons)
        )

        with(viewBinding) {
            viewBinding.rvDeals.run {
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
            }
        }
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
        viewModel.offerDeal.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {
                    showLoading()
                }
                is Status.Success -> {
                    stopShowingLoading()
                    offerDeals = result.value
                    showCouponDialog()
                }
                is Status.Failure -> {
                    stopShowingLoading()
                }
            }
        }
    }
}