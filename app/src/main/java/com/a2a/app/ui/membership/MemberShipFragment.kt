package com.a2a.app.ui.membership

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.AllPlanModel
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.FragmentMemberShipBinding
import com.a2a.app.ui.components.A2ATopAppBar
import com.a2a.app.ui.membership.component.MembershipPlanItem
import com.a2a.app.ui.theme.*
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class MemberShipFragment : Fragment(R.layout.fragment_member_ship), PaymentResultListener {

    private lateinit var allPlans: List<AllPlanModel.Result>
    private lateinit var assignedPlan: AllPlanModel.Result
    private val userViewModels by viewModels<UserViewModel>()
    private val viewModel by viewModels<CustomViewModel>()
    private lateinit var viewBinding: FragmentMemberShipBinding
    private lateinit var mainActivity: MainActivity

    @Inject
    lateinit var viewUtils: ViewUtils

    @Inject
    lateinit var appUtils: AppUtils

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMemberShipBinding.bind(view)
        Checkout.preload(context)
    }

    private fun getPlans() {
        viewModel.allPlans().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {
                    viewUtils.showLoading(parentFragmentManager)
                }
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    allPlans = result.value.result
                    //setData()
                    viewBinding.membershipComposeView.apply {
                        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                        setContent {
                            A2ATheme {
                                MembershipScreen()
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

    private fun assignPlan(planId: String) {
        val userId = AppUtils(context!!).getUser()!!.id

        userViewModels.assignPlan(
            userId = userId,
            planId = planId
        ).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {
                    viewUtils.showLoading(parentFragmentManager)
                }
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    viewUtils.showShortToast(result.value.message)
                    findNavController().navigate(R.id.action_global_myPlanFragment)
                }
                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
                }
            }
        }
    }

    private fun buyPlan() {
        val co = Checkout()
        val user = appUtils.getUser()
        val amount = assignedPlan.price
        try {
            val options = JSONObject();
            options.put("name", "A2A")
            options.put("description", "A2A Buy Membership Plan")
            options.put("currency", "INR")
            options.put("amount", (amount * 100))

            val preFill = JSONObject();
            preFill.put("email", user?.email)
            preFill.put("contact", user?.mobile)
            options.put("prefill", preFill)
            co.open(mainActivity, options)
        } catch (e: Exception) {
            viewUtils.showShortToast("Error in payment:  + ${e.message}")
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        viewUtils.showShortToast("Recharge successfully")
        assignPlan(assignedPlan._id)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        viewUtils.showShortToast("Payment failed, try again!")
    }

    override fun onResume() {
        super.onResume()
        //mainActivity.hideToolbarAndBottomNavigation()
        getPlans()
    }


    //----------------------------------- Compose UI -----------------------------------------------
    @Composable
    fun MembershipScreen() {
        Scaffold(topBar = {
            A2ATopAppBar(title = "Membership Plan") {
                findNavController().popBackStack()
            }
        }, content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.MainBgColor
            ) {
                ContentMembership()
            }
        })
    }

    @Composable
    fun ContentMembership() {
        LazyColumn(
            contentPadding = PaddingValues(ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
        ) {
            items(allPlans) { plan ->
                MembershipPlanItem(plan = plan) {
                    assignedPlan = plan
                    buyPlan()
                }
            }
        }
    }
}