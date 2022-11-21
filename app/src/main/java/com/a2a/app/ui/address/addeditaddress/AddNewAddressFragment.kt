package com.a2a.app.ui.address.addeditaddress

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.a2a.app.R
import com.a2a.app.common.Status
import com.a2a.app.data.model.AddressListModel
import com.a2a.app.data.model.CityListModel
import com.a2a.app.data.model.StateListModel
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.FragmentAddNewAddressBinding
import com.a2a.app.ui.address.AddressViewModel
import com.a2a.app.ui.address.LocationPickerActivity
import com.a2a.app.ui.components.*
import com.a2a.app.ui.theme.A2ATheme
import com.a2a.app.ui.theme.MainBgColor
import com.a2a.app.ui.theme.ScreenPadding
import com.a2a.app.ui.theme.SpaceBetweenViewsAndSubViews
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddNewAddressFragment : Fragment(R.layout.fragment_add_new_address) {

    private var stateResponse = mutableListOf<StateListModel.Result>()
    private var stateNameList = mutableListOf<String>()
    private var cities = mutableListOf<CityListModel.Result>()
    private var cityNameList = mutableListOf<String>()
    var selectedStateId: String? = null
    var selectedCityId: String? = null
    var lat: Double = 0.toDouble()
    var long: Double = 0.toDouble()
    var address: AddressListModel.Result? = null;
    val pinList = mutableListOf<String>()
    private val customViewModel by viewModels<CustomViewModel>()
    private val addressViewModel by viewModels<AddressViewModel>()
    private lateinit var viewBinding: FragmentAddNewAddressBinding

    @Inject
    lateinit var viewUtils: ViewUtils

    @Inject
    lateinit var appUtils: AppUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentAddNewAddressBinding.bind(view)

        getArgument()

        if (address != null) {
            selectedStateId = address!!.state.id
            selectedCityId = address!!.city.id
            getCityByState()
            getZipList()

        }
    }

    private fun getArgument() {
        val args: AddNewAddressFragmentArgs by navArgs()
        address = Gson().fromJson(args.address, AddressListModel.Result::class.java)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 23 && resultCode == RESULT_OK) {
            lat = data!!.getDoubleExtra("lat", 0.toDouble())
            long = data.getDoubleExtra("long", 0.toDouble())
            addressViewModel.updateLatLang(lat.toString(), long.toString())
        }
    }

    private fun getState() {
        customViewModel.getAllStates().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Status.Loading -> {
                    viewUtils.showLoading(parentFragmentManager)
                }
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    stateResponse.clear()
                    stateNameList.clear()
                    if (response.value.result.isNullOrEmpty()) {
                        viewUtils.showError("No Data!")
                    } else {
                        stateResponse = response.value.result.toMutableList()
                        stateResponse.forEach { stateNameList.add(it.name) }
                        setupOptions()
                    }
                }
                else -> {
                    viewUtils.stopShowingLoading()
                    viewUtils.showError("Something went wrong!")
                    // finish()
                }
            }
        }
    }

    private fun getZipList() {
        customViewModel.getZipByCity(selectedCityId!!).observe(
            viewLifecycleOwner
        ) { response ->
            when (response) {
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    if (response.value.result.isEmpty()) {
                        viewUtils.showError("No available pin code, select another city/state")
                    }
                    viewBinding.run {
                        pinList.clear()
                        pinList.addAll(response.value.result.map { it.name })
                        val arrayAdapter = ArrayAdapter(
                            context!!, android.R.layout.select_dialog_item, pinList
                        )
                    }
                }
                is Status.Loading -> {
                }
                else -> {
                    viewUtils.stopShowingLoading()
                    viewUtils.showError("Something went wrong!")
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun getCityByState() {
        customViewModel.getAllCityByState(selectedStateId!!)
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Status.Success -> {
                        cityNameList.clear()
                        cities.clear()
                        viewUtils.stopShowingLoading()
                        cities = response.value.result.toMutableList()
                        cities.forEach { cityNameList.add(it.name) }
                    }
                    is Status.Loading -> {
                    }
                    else -> {
                        viewUtils.stopShowingLoading()
                        viewUtils.showError("Something went wrong!")
                        //finish()
                        findNavController().popBackStack()
                    }
                }
            }
    }

    private fun setupOptions() {
        viewBinding.addEditAddressComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                A2ATheme {
                    AddOrEditAddressScreen()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (stateResponse.isNotEmpty()) setupOptions()
        else getState()
    }

    //---------------------------------- Compose UI ------------------------------------------------

    @Composable
    fun AddOrEditAddressScreen() {
        LaunchedEffect(key1 = "", block = {
            addressViewModel.updateTextFields(address)
        })

        Scaffold(topBar = {
            A2ATopAppBar(title = if (address == null) "Add Address" else "Edit Address") {
                findNavController().popBackStack()
            }
        }, content = {
            ContentAddOrEditAddress(
                fullName = addressViewModel.fullName,
                onNameChange = { addressViewModel.updateFullName(it) },
                phone = addressViewModel.phone,
                onPhoneChange = { addressViewModel.updatePhone(it) },
                address1 = addressViewModel.address1,
                onAddress1Change = { addressViewModel.updateAddress1(it) },
                address2 = addressViewModel.address2,
                onAddress2Change = { addressViewModel.updateAddress2(it) },
                country = addressViewModel.country,
                onCountryChange = { addressViewModel.updateCountry(it) },
                state = addressViewModel.state,
                onStateChange = { addressViewModel.updateState(it) },
                city = addressViewModel.city,
                onCityChange = { addressViewModel.updateCity(it) },
                landmark = addressViewModel.landmark,
                onLandmarkChange = { addressViewModel.updateLandmark(it) },
                pincode = addressViewModel.pincode,
                onPincodeChange = { addressViewModel.updatePincode(it) },
                addressType = addressViewModel.addressType,
                onAddressTypeChange = { addressViewModel.updateAddressType(it) },
                addressTypes = addressViewModel.addressTypes,
                countries = addressViewModel.countries
            )
        })
    }

    @Composable
    fun ContentAddOrEditAddress(
        fullName: String,
        onNameChange: (String) -> Unit,
        phone: String,
        onPhoneChange: (String) -> Unit,
        address1: String,
        onAddress1Change: (String) -> Unit,
        address2: String,
        onAddress2Change: (String) -> Unit,
        country: String,
        onCountryChange: (String) -> Unit,
        state: String,
        onStateChange: (String) -> Unit,
        city: String,
        onCityChange: (String) -> Unit,
        pincode: String,
        onPincodeChange: (String) -> Unit,
        landmark: String,
        onLandmarkChange: (String) -> Unit,
        addressType: String,
        onAddressTypeChange: (String) -> Unit,
        addressTypes: List<String>,
        countries: List<String>
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.MainBgColor),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(ScreenPadding)
                    .align(Alignment.TopCenter)
                    .verticalScroll(rememberScrollState())
            ) {
                // full name
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { onNameChange(it) },
                    label = { Text(text = "Full name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

                // Phone
                val maxMobile = 10
                OutlinedTextField(
                    value = phone,
                    onValueChange = { if (it.length <= maxMobile) onPhoneChange(it) },
                    label = { Text(text = "Phone") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
                Text(
                    text = "${phone.length}/$maxMobile",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.fillMaxWidth()
                )

                // Address 1
                OutlinedTextField(
                    value = address1,
                    onValueChange = { onAddress1Change(it) },
                    label = { Text(text = "House No, Building Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                )

                Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

                // Address 2
                OutlinedTextField(
                    value = address2,
                    onValueChange = { onAddress2Change(it) },
                    label = { Text(text = "Road no, Area, Colony") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                )

                Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

                // Country
                A2ADropDown(
                    modifier = Modifier.fillMaxWidth(),
                    value = country,
                    label = "Country",
                    onValueChange = { onCountryChange(it) },
                    list = countries
                )

                Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

                //state
                A2ADropDown(
                    modifier = Modifier.fillMaxWidth(),
                    value = state,
                    label = "State",
                    onValueChange = { stateName ->
                        onPincodeChange("")
                        onCityChange("")
                        stateResponse.find { it.name == stateName }?.let {
                            onStateChange(stateName)
                            selectedStateId = it.id
                        }
                        getCityByState()
                    },
                    list = stateNameList
                )

                Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

                //city
                A2ADropDown(
                    modifier = Modifier.fillMaxWidth(),
                    value = city,
                    label = "City",
                    onValueChange = { cityName ->
                        onPincodeChange("")
                        cities.find { it.name == cityName }?.let {
                            onCityChange(cityName)
                            selectedCityId = it.id
                        }
                        getZipList()
                    },
                    list = cityNameList
                )

                Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

                Row(modifier = Modifier.fillMaxWidth()) {
                    // Pincode
                    A2ADropDown(
                        modifier = Modifier.weight(1f),
                        value = pincode,
                        label = "Pincode",
                        onValueChange = { pin ->
                            onPincodeChange(pin)
                        },
                        list = pinList
                    )
                    /*Column(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(value = pincode, onValueChange = {
                            onPincodeChange(it)
                        }, label = { Text("Pincode") }, modifier = Modifier
                            .clickable {
                                pinExpanded = !pinExpanded
                            }
                            .onGloballyPositioned { coordinate ->
                                pinTextFieldSize = coordinate.size.toSize()
                            },
                            // keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            maxLines = 1, readOnly = true, trailingIcon = {
                                Icon(pinIcon, "", modifier = Modifier.clickable {
                                    pinExpanded = !pinExpanded
                                })
                            })

                        DropdownMenu(
                            expanded = pinExpanded,
                            onDismissRequest = { pinExpanded = false },
                            modifier = Modifier.width(with(LocalDensity.current) { pinTextFieldSize.width.toDp() })
                        ) {
                            pinList.sorted().forEach { label ->
                                DropdownMenuItem(onClick = {
                                    addressViewModel.updateLatLang("", "")
                                    onPincodeChange(label)
                                    pinExpanded = false
                                }) {
                                    Text(text = label)
                                }
                            }
                        }
                    }*/

                    Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))
                    // landmark
                    OutlinedTextField(
                        value = landmark,
                        onValueChange = { onLandmarkChange(it) },
                        label = { Text(text = "Landmark") },
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    )
                }

                //address types
                Row(modifier = Modifier.fillMaxWidth()) {
                    addressTypes.forEach { label ->
                        Log.e("Address", "Address type : $label")
                        if (address != null) Log.e("Address", "Already : ${address!!.title}")

                        A2ARadioButton(label = label, addressType) {
                            onAddressTypeChange(it)
                        }
                    }
                }
            }

            A2AButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                title = "Add",
                allCaps = false
            ) {
                Log.e(
                    "Add Address",
                    "Full name : $fullName\n"
                            + "Phone : $phone\n"
                            + "Address1 : $address1\n"
                            + "Address2 : $address2\n"
                            + "State : $state\n"
                            + "City : $city\n"
                            + "Pincode : $pincode\n"
                            + "Address Type : $addressType\n"
                            + "Lat : ${addressViewModel.lat}\n"
                            + "Lang : ${addressViewModel.lang}"
                )
                //first check all fields are fill
                when {
                    (fullName.isEmpty() ||
                            phone.isEmpty() ||
                            address1.isEmpty() ||
                            address2.isEmpty() ||
                            state.isEmpty() ||
                            city.isEmpty() ||
                            pincode.isEmpty() ||
                            addressType.isEmpty()
                            ) -> viewUtils.showError("Fill all fields, or select options")

                    (addressViewModel.lat.isEmpty() || addressViewModel.lang.isEmpty()) -> startActivityForResult(
                        Intent(
                            context, LocationPickerActivity::class.java
                        ), 23
                    )

                    else -> {
                        val userId = appUtils.getUser()!!.id
                        if (address != null) editAddress()
                        else addAddress(userId)
                    }


                }
            }
        }
    }

    private fun addAddress(userId: String) {
        addressViewModel.addAddress(
            userId = userId,
            cityId = selectedCityId.toString(),
            stateId = selectedStateId.toString()
        )

        addressViewModel.saveAddressResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> viewUtils.showLoading(parentFragmentManager)
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    findNavController().popBackStack()
                }
                is Status.Failure -> viewUtils.stopShowingLoading()
            }
        }
    }

    private fun editAddress() {
        addressViewModel.editAddress(
            addressId = address!!.id,
            cityId = selectedCityId.toString(),
            stateId = selectedStateId.toString()
        )

        addressViewModel.editAddressResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> viewUtils.showLoading(parentFragmentManager)
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    findNavController().popBackStack()
                }
                is Status.Failure -> viewUtils.stopShowingLoading()
            }
        }
    }

    @Preview
    @Composable
    fun AddOrEditScreenPreview() {
        AddOrEditAddressScreen()
    }
}