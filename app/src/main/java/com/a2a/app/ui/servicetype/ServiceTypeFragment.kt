package com.a2a.app.ui.servicetype

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.ItemClick
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.CommonModel
import com.a2a.app.data.model.ServiceTypeModel
import com.a2a.app.data.network.CustomApi
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentCityBinding
import com.a2a.app.databinding.FragmentServiceTypeBinding
import com.a2a.app.mappers.toCommonModel
import com.a2a.app.ui.category.CategoryFragmentDirections
import com.a2a.app.ui.common.CommonAdapter
import com.google.gson.Gson


class ServiceTypeFragment :
    BaseFragment<FragmentServiceTypeBinding, CustomViewModel, CustomRepository>(
        FragmentServiceTypeBinding::inflate
    ) {

    private lateinit var mainActivity: MainActivity
    private lateinit var serviceTypes: List<ServiceTypeModel.Result>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentServiceTypeBinding.inflate(inflater, container, false)

    override fun getViewModel() = CustomViewModel::class.java

    override fun getFragmentRepository() =
        CustomRepository(remoteDataSource.getBaseUrl().create(CustomApi::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.showToolbarAndBottomNavigation()

        if (this::serviceTypes.isInitialized)
            setData()
        else
            getServiceType()
    }

    private fun getServiceType() {
        viewModel.serviceTypes().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {
                    showLoading()
                }
                is Status.Success -> {
                    stopShowingLoading()
                    serviceTypes = result.value.result
                    setData()
                }
                is Status.Failure -> {
                    stopShowingLoading()
                }
            }
        }
    }

    private fun setData() {
        val data = ArrayList<CommonModel>()
        for (serviceType in serviceTypes) {
            data.add(serviceType.toCommonModel())
        }

        viewBinding.rvServiceType.run {
            layoutManager = LinearLayoutManager(context)
            adapter = CommonAdapter(
                context = context,
                data = data,
                itemClick = object : ItemClick {
                    override fun clickRvItem(name: String, model: Any) {
                        val category = model as CommonModel
                        when (name) {
                            "details" -> {
                                mainActivity.hideToolbarAndBottomNavigation()
                                val details = Gson().toJson(category, CommonModel::class.java)
                                val action =
                                    ServiceTypeFragmentDirections.actionGlobalViewDetailsFragment(
                                        details = details,
                                        name = "Service Type Details"
                                    )
                                findNavController().navigate(action)
                            }
                        }
                    }
                }
            )
        }
    }

}