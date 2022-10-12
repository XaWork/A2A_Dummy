package com.a2a.app.ui.servicetype

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.common.ItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.CommonModel
import com.a2a.app.data.model.ServiceTypeModel
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentServiceTypeBinding
import com.a2a.app.mappers.toCommonModel
import com.a2a.app.ui.common.CommonAdapter
import com.a2a.app.utils.ViewUtils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ServiceTypeFragment : Fragment(R.layout.fragment_service_type) {

    private lateinit var mainActivity: MainActivity

    private val viewModel by viewModels<CustomViewModel>()
    private lateinit var viewBinding: FragmentServiceTypeBinding

    @Inject
    lateinit var viewUtils: ViewUtils
    private lateinit var serviceTypes: List<ServiceTypeModel.Result>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentServiceTypeBinding.bind(view)
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
                    viewUtils.showLoading(parentFragmentManager)
                }
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    serviceTypes = result.value.result
                    setData()
                }
                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
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
                data = data.sortedBy { it.name } as MutableList<CommonModel>,
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