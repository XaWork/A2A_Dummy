package com.a2a.app.ui.membership

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.Status
import com.a2a.app.data.model.WalletDataModel
import com.a2a.app.data.network.UserApi
import com.a2a.app.data.repository.UserRepository
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.data.viewmodel.UserViewModel1
import com.a2a.app.databinding.FragmentBulkOrderBinding
import com.a2a.app.databinding.FragmentMyPlanBinding
import com.a2a.app.toDate
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyPlanFragment : Fragment(R.layout.fragment_my_plan) {

    private lateinit var allPlans: WalletDataModel
    private val viewModel by viewModels<UserViewModel1>()
    private lateinit var viewBinding: FragmentMyPlanBinding

    @Inject
    lateinit var appUtils: AppUtils
    @Inject
    lateinit var viewUtils: ViewUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMyPlanBinding.bind(view)

        setToolbar()

        viewBinding.planContainer.visibility = View.GONE

        if (this::allPlans.isInitialized)
            setData()
        else
            getAllPlans()
    }

    private fun setToolbar() {
        viewBinding.incToolbar.toolbar.title = "My Plans"
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }


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

    private fun setData() {
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
        }
    }
}