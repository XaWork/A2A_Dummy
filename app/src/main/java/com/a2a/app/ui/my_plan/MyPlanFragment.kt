package com.a2a.app.ui.my_plan

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.common.Status
import com.a2a.app.data.model.WalletDataModel
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.FragmentMyPlanBinding
import com.a2a.app.toDate
import com.a2a.app.ui.common.ErrorLayout
import com.a2a.app.ui.components.A2ATopAppBar
import com.a2a.app.ui.my_plan.components.MyPlanItem
import com.a2a.app.ui.theme.A2ATheme
import com.a2a.app.ui.theme.MainBgColor
import com.a2a.app.ui.theme.ScreenPadding
import com.a2a.app.ui.theme.SpaceBetweenViewsAndSubViews
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyPlanFragment : Fragment(R.layout.fragment_my_plan) {

    private lateinit var allPlans: WalletDataModel
    private val viewModel by viewModels<UserViewModel>()
    private lateinit var viewBinding: FragmentMyPlanBinding

    @Inject
    lateinit var appUtils: AppUtils

    @Inject
    lateinit var viewUtils: ViewUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMyPlanBinding.bind(view)

        //setToolbar()

        // viewBinding.planContainer.visibility = View.GONE

        if (this::allPlans.isInitialized)
            setData()
        else
            getAllPlans()
    }

    /*private fun setToolbar() {
        viewBinding.incToolbar.toolbar.title = "My Plans"
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }*/


    private fun getAllPlans() {
        val userId = appUtils.getUser()!!.id

        viewModel.getWalletData(userId).observe(viewLifecycleOwner) {
            when (it) {
                is Status.Loading -> {
                    viewUtils.showLoading(parentFragmentManager)
                }
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    allPlans = it.value
                    setData()
                }
                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
                }
            }
        }
    }

    private fun setData() {/*
        with(viewBinding) {
            if (allPlans.plan != null && allPlans.plan!!.planName.isNotEmpty()) {
                errorLayout.visibility = View.GONE
                planContainer.visibility = View.VISIBLE
                run {
                    allPlans.run {
                        usageText.text = customerPoint.toString()
                        expiryDate.text = plan!!.expDate?.toDate("MMMM dd, yyyy")
                        benefitsContent.text = plan.benefits
                        planName.text = plan.planName
                        planPrice.text = plan.itemPrice
                    }

                    upgrade.setOnClickListener {
                        findNavController().navigate(R.id.action_global_memberShipFragment)
                    }
                }
            } else {
                errorLayout.visibility = View.VISIBLE
                planContainer.visibility = View.GONE
            }
        }*/
        viewBinding.myPlanComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                A2ATheme {
                    MyPlanScreen()
                }
            }
        }
    }

    //--------------------------------- Compose UI -------------------------------------------------
    @Composable
    fun MyPlanScreen() {
        Scaffold(topBar = {
            A2ATopAppBar(title = "My Plans") {
                findNavController().popBackStack()
            }
        }, content = {
            Surface(
                color = MaterialTheme.colors.MainBgColor
            ) {
                if (allPlans.plan != null && allPlans.plan!!.planName.isNotEmpty())
                    ContentMyPlan()
                else
                    ShowError()
            }
        })
    }

    @Composable
    fun ContentMyPlan() {
        Column(
            modifier = Modifier
                .padding(ScreenPadding)
                .fillMaxSize()
        ) {
            MyPlanItem(allPlans, onUpgradeClick = {
                findNavController().navigate(R.id.action_global_memberShipFragment)
            })
        }
    }

    @Composable
    fun ShowError() {
        Box(
            modifier = Modifier
                .padding(ScreenPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colors.MainBgColor),
            contentAlignment = Alignment.Center
        ) {
            ErrorLayout(
                title = stringResource(id = R.string.no_plans),
                description = stringResource(id = R.string.no_purchased_plan),
                showButton = false
            ) {

            }
        }
    }
}