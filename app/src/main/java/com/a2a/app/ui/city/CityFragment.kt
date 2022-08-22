package com.a2a.app.ui.city

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.BR
import com.a2a.app.MainActivity
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.CityModel
import com.a2a.app.data.model.CommonModel
import com.a2a.app.data.network.CustomApi
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentCityBinding
import com.a2a.app.ui.category.CategoryFragmentDirections
import com.a2a.app.ui.common.CommonAdapter

class CityFragment : BaseFragment<
        FragmentCityBinding,
        CustomViewModel,
        CustomRepository>(FragmentCityBinding::inflate) {

    private lateinit var mainActivity: MainActivity
    private lateinit var allCities: CityModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.showToolbarAndBottomNavigation()

        //setData()
        //get all cities
        if (this::allCities.isInitialized)
            setData()
        else
            getAllCities()
    }

    private fun getAllCities() {
        viewModel.getAllCities()
        viewModel.allCities.observe(viewLifecycleOwner) {
            when (it) {
                is Status.Loading -> {
                    showLoading()
                }

                is Status.Success -> {
                    stopShowingLoading()
                    allCities = it.value
                    setData()
                }

                is Status.Failure -> {
                    stopShowingLoading()
                }
            }
        }
    }

    private fun setData() {
        val cityList = ArrayList<CommonModel>()
        //cityList.add(CommonModel("1", "", "customer name", "", "", ""))
        for (city in allCities.result) {
            city.run {
                cityList.add(
                    CommonModel(
                        id, file, name, description, slug, subHeading
                    )
                )
            }
        }
       // viewBinding.setVariable(BR.dataList, cityList.toList())
        viewBinding.rvCity.run {
            layoutManager = LinearLayoutManager(context)
            adapter = CommonAdapter(cityList, context, object : RvItemClick {
                override fun clickWithPosition(name: String, position: Int) {
                   /* mainActivity.hideToolbarAndBottomNavigation()
                    val categoryId = cityList[position].id
                    val action =
                        CategoryFragmentDirections.actionCategoryFragmentToSubCategoryFragment(
                            categoryId
                        )
                    findNavController().navigate(action)*/
                }
            })
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCityBinding.inflate(inflater, container, false)

    override fun getViewModel() = CustomViewModel::class.java

    override fun getFragmentRepository() =
        CustomRepository(remoteDataSource.getBaseUrl().create(CustomApi::class.java))
}