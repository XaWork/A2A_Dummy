package com.a2a.app.ui.city

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.common.ItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.CityModel
import com.a2a.app.data.model.CommonModel
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentCityBinding
import com.a2a.app.mappers.toCommonModel
import com.a2a.app.ui.common.CommonAdapter
import com.a2a.app.ui.components.SingleCommon
import com.a2a.app.ui.theme.*
import com.a2a.app.utils.ViewUtils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CityFragment : Fragment(R.layout.fragment_city) {

    private lateinit var mainActivity: MainActivity
    private lateinit var allCities: CityModel
    private val cityList = ArrayList<CommonModel>()

    private val viewModel by viewModels<CustomViewModel>()
    private lateinit var viewBinding: FragmentCityBinding

    @Inject
    lateinit var viewUtils: ViewUtils

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.showToolbarAndBottomNavigation()
        viewBinding = FragmentCityBinding.bind(view)

        //setData()
        //get all cities
        if (this::allCities.isInitialized)
            setData()
        else {
            getAllCities()
        }
    }

    private fun getAllCities() {
        viewModel.getAllCities().observe(viewLifecycleOwner) {
            when (it) {
                is Status.Loading -> {
                    viewUtils.showLoading(parentFragmentManager)
                }

                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    allCities = it.value

                    //convert data to common list
                    for (city in allCities.result) {
                        cityList.add(city.toCommonModel())
                    }
                    //sort cities by name
                    cityList.sortedBy { city -> city.name }

                    setData()
                }

                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
                }
            }
        }
    }

    private fun setData() {
        viewBinding.cityComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                CityScreen(cityList = cityList)
            }
        }
        //cityList.add(CommonModel("1", "", "customer name", "", "", ""))
        // viewBinding.setVariable(BR.dataList, cityList.toList())
        /*viewBinding.rvCity.run {
            layoutManager = LinearLayoutManager(context)
            adapter = CommonAdapter(cityList.sortedBy { it.name } as MutableList<CommonModel>,
                context,
                object : ItemClick {
                    override fun clickRvItem(name: String, model: Any) {
                        when (name) {
                            "details" -> {
                                mainActivity.hideToolbarAndBottomNavigation()
                                val details =
                                    Gson().toJson(model as CommonModel, CommonModel::class.java)
                                val action = CityFragmentDirections.actionGlobalViewDetailsFragment(
                                    details = details,
                                    name = "City Details"
                                )
                                findNavController().navigate(action)
                            }
                        }
                    }

                })
        }*/
    }

    @Composable
    fun CityScreen(cityList: List<CommonModel>) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.MainBgColor)
                .padding(ScreenPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(CardCornerRadius))
            ) {
                items(cityList) { city ->
                    SingleCommon(city) { task, item ->
                        when (task) {
                            "details" -> {
                                mainActivity.hideToolbarAndBottomNavigation()
                                val details =
                                    Gson().toJson(item, CommonModel::class.java)
                                val action = CityFragmentDirections.actionGlobalViewDetailsFragment(
                                    details = details,
                                    name = "City Details"
                                )
                                findNavController().navigate(action)
                            }
                        }
                    }
                }
            }
        }
    }
}