package com.a2a.app.ui.membership

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.R
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.AllPlanModel
import com.a2a.app.data.network.CustomApi
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.FragmentBulkOrderBinding
import com.a2a.app.databinding.FragmentMemberShipBinding
import com.a2a.app.utils.AppUtils


class MemberShipFragment :
    BaseFragment<FragmentMemberShipBinding, CustomViewModel, CustomRepository>(
        FragmentMemberShipBinding::inflate
    ) {

    private lateinit var allPlans: List<AllPlanModel.Result>
    private lateinit var userViewModels: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModels = getUserViewModel()
        setToolbar()
        getPlans()
    }

    private fun setToolbar() {
        viewBinding.incToolbar.toolbar.title = "Membership Plans"
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getPlans() {
        viewModel.allPlans().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {
                    showLoading()
                }
                is Status.Success -> {
                    stopShowingLoading()
                    allPlans = result.value.result
                    setData()
                }
                is Status.Failure -> {
                    stopShowingLoading()
                }
            }
        }
    }

    private fun setData() {
        if (allPlans.isEmpty()) {
            viewBinding.errorLayout.visibility = View.VISIBLE
        } else {
            with(viewBinding) {
                errorLayout.visibility = View.GONE
                rvPlans.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = PlanAdapter(
                        context = context,
                        data = allPlans,
                        itemClick = object : RvItemClick {
                            override fun clickWithPosition(name: String, position: Int) {
                                assignPlan(allPlans[position]._id)
                            }
                        }
                    )
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
                    showLoading()
                }
                is Status.Success -> {
                    stopShowingLoading()
                    toast(result.value.message)
                    findNavController().navigate(R.id.action_global_myPlanFragment)
                }
                is Status.Failure -> {
                    stopShowingLoading()
                }
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMemberShipBinding.inflate(inflater, container, false)

    override fun getViewModel() = CustomViewModel::class.java

    override fun getFragmentRepository() =
        CustomRepository(remoteDataSource.getBaseUrl().create(CustomApi::class.java))
}