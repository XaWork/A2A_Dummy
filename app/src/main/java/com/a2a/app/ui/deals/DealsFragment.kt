package com.a2a.app.ui.deals

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.Status
import com.a2a.app.data.network.CustomApi
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentCityBinding
import com.a2a.app.databinding.FragmentDealsBinding
import com.a2a.app.ui.common.CommonAdapter


class DealsFragment :
    BaseFragment<FragmentDealsBinding, CustomViewModel, CustomRepository>(FragmentDealsBinding::inflate) {

    private lateinit var mainActivity: MainActivity

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

        viewBinding.errorLayout.visibility = View.VISIBLE
        //getDeals()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDealsBinding.inflate(inflater, container, false)

    override fun getViewModel() = CustomViewModel::class.java

    override fun getFragmentRepository() =
        CustomRepository(remoteDataSource.getBaseUrl().create(CustomApi::class.java))

    private fun getDeals() {
        viewModel.offerDeal()
        viewModel.offerDeal.observe(viewLifecycleOwner){result ->
            when(result){
                is Status.Loading -> {
                    showLoading()
                }
                is Status.Success -> {
                    stopShowingLoading()
                    if(result.value.result.isEmpty())
                        viewBinding.errorLayout.visibility = View.VISIBLE
                    else
                        setData()

                }
                is Status.Failure -> {
                    stopShowingLoading()
                }
            }
        }
    }

    private fun setData() {
        with(viewBinding){
            rvDeals.visibility = View.VISIBLE
            viewBinding.rvDeals.run {
                layoutManager = LinearLayoutManager(context)
                //adapter = CommonAdapter(context)
            }
        }
    }
}