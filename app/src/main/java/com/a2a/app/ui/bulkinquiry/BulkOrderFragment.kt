package com.a2a.app.ui.bulkinquiry

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.common.Status
import com.a2a.app.data.model.CityModel
import com.a2a.app.databinding.FragmentBulkOrderBinding
import com.a2a.app.ui.components.A2AButton
import com.a2a.app.ui.components.A2ADropDown
import com.a2a.app.ui.components.A2ATopAppBar
import com.a2a.app.ui.theme.*
import com.a2a.app.utils.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BulkOrderFragment : Fragment(R.layout.fragment_bulk_order) {

    private var cityList: ArrayList<CityModel.Result> = ArrayList()

    //private val viewModel by viewModels<CustomViewModel>()
    private val viewModel by viewModels<BulkInquiryViewModel>()
    private lateinit var viewBinding: FragmentBulkOrderBinding

    @Inject
    lateinit var viewUtils: ViewUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentBulkOrderBinding.bind(view)
    }

    private fun getCities() {
        viewModel.getAllCities()
        viewModel.cities
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Status.Loading -> {
                        viewUtils.showLoading(parentFragmentManager)
                    }
                    is Status.Success -> {
                        viewUtils.stopShowingLoading()
                        cityList.addAll(result.value.result)
                        setData()
                    }
                    is Status.Failure -> {
                        viewUtils.stopShowingLoading()
                    }
                }
            }
    }


    private fun setData() {
        viewBinding.bulkInquiryComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                A2ATheme {
                    BulkInquiryScreen()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getCities()
    }


    // ------------------------------------ Compose UI ---------------------------------------------
    @Composable
    fun BulkInquiryScreen() {

        Scaffold(topBar = {
            A2ATopAppBar("Bulk Inquiry") {
                findNavController().popBackStack()
            }
        }, content = {
            ContentBulkInquiry(
                fullName = viewModel.fullName,
                onNameChange = { viewModel.updateFullName(it) },
                email = viewModel.email,
                onEmailChange = { viewModel.updateEmail(it) },
                mobile = viewModel.mobile,
                onMobileChange = { viewModel.updateMobile(it) },
                address = viewModel.email,
                onAddressChange = { viewModel.updateAddress(it) },
                city = viewModel.city,
                onCityChange = { viewModel.updateCity(it) },
                onCityIdChange = { viewModel.updateCityId(it) },
                message = viewModel.message,
                onMessageChange = { viewModel.updateMessage(it) },
            )
        })
    }

    @Composable
    fun ContentBulkInquiry(
        fullName: String,
        onNameChange: (String) -> Unit,
        email: String,
        onEmailChange: (String) -> Unit,
        mobile: String,
        onMobileChange: (String) -> Unit,
        address: String,
        onAddressChange: (String) -> Unit,
        city: String,
        onCityChange: (String) -> Unit,
        onCityIdChange: (String) -> Unit,
        message: String,
        onMessageChange: (String) -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.MainBgColor)
                .padding(all = ScreenPadding),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.CardBg)
                    .clip(
                        shape = RoundedCornerShape(
                            CardCornerRadius
                        )
                    )
                    .align(Alignment.TopCenter),
                elevation = CardElevation
            ) {
                Column(
                    modifier = Modifier
                        .padding(all = ScreenPadding)
                ) {
                    //full name
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { onNameChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = stringResource(id = R.string.full_name)) }
                    )

                    //Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { onEmailChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = stringResource(id = R.string.email)) }
                    )

                    //mobile
                    OutlinedTextField(
                        value = mobile,
                        onValueChange = { onMobileChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = stringResource(id = R.string.mobile_number)) }
                    )

                    //address
                    OutlinedTextField(
                        value = address,
                        onValueChange = { onAddressChange(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        label = { Text(text = stringResource(id = R.string.address)) }
                    )

                    //city
                    val cityNameList = ArrayList<String>()
                    cityList.forEach { cityNameList.add(it.name) }

                    A2ADropDown(
                        modifier = Modifier.fillMaxWidth(),
                        value = city,
                        label = "City",
                        onValueChange = { cityName ->
                            Log.e("bulk order", "City : $cityName")
                            onCityChange(cityName)
                            cityList.find { it.name == cityName }?.let { city ->
                                onCityIdChange(city.id)
                            }
                        },
                        list = cityNameList
                    )

                    //message
                    OutlinedTextField(
                        value = message,
                        onValueChange = { onMessageChange(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        label = { Text(text = stringResource(id = R.string.your_message)) }
                    )
                }
            }

            A2AButton(
                title = stringResource(id = R.string.confirm),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                allCaps = false
            ) {
                findNavController().popBackStack()
            }
        }
    }

    @Preview
    @Composable
    fun BulkInquiryScreenPreview() {
        BulkInquiryScreen()
    }
}