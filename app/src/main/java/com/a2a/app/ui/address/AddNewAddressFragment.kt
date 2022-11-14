package com.a2a.app.ui.address

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import com.a2a.app.hideSoftKeyboard
import com.a2a.app.setupDropDown
import com.a2a.app.ui.components.A2AButton
import com.a2a.app.ui.components.A2ARadioButton
import com.a2a.app.ui.components.A2ATopAppBar
import com.a2a.app.ui.components.TextFieldPlaceHolder
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

    private lateinit var stateResponse: StateListModel
    lateinit var cities: List<CityListModel.Result>
    var selectedStateId: String? = null
    var selectedCityId: String? = null
    var lat: Double = 0.toDouble()
    var long: Double = 0.toDouble()
    var selectedAddressType: String? = null
    var address: AddressListModel.Result? = null;
    val pinList = mutableListOf<String>()
    var validPin = false;
    private val customViewModel by viewModels<CustomViewModel>()
    private val viewModel by viewModels<UserViewModel>()
    private lateinit var viewBinding: FragmentAddNewAddressBinding

    @Inject
    lateinit var viewUtils: ViewUtils

    @Inject
    lateinit var appUtils: AppUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentAddNewAddressBinding.bind(view)

        val args: AddNewAddressFragmentArgs by navArgs()
        address = Gson().fromJson(args.address, AddressListModel.Result::class.java)
        setToolbar()
        with(viewBinding) {
            addressType.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.home -> {
                        tilOtherText.visibility = View.GONE
                        selectedAddressType = "home"
                    }
                    R.id.work -> {
                        selectedAddressType = "work"
                        tilOtherText.visibility = View.GONE
                    }
                    else -> {
                        selectedAddressType = null
                        tilOtherText.visibility = View.VISIBLE
                    }
                }
            }

            addressType.check(R.id.home)
            save.setOnClickListener {
                run {
                    when {
                        name.text.toString().trim().isEmpty() -> {
                            viewUtils.showError("Enter name!")
                        }
                        etPhone.text.toString().trim().isEmpty() -> {
                            viewUtils.showError("Enter phone number!")
                        }
                        etAddress1.text.toString().trim().isEmpty() -> {
                            viewUtils.showError("Enter address")
                        }
                        etState.text.toString().trim().isEmpty() -> {
                            viewUtils.showError("Enter state")
                        }
                        etPin.text.toString().trim().isEmpty() || !validPin -> {
                            viewUtils.showError("Enter valid pin code")
                        }
                        etCity.text.toString().trim().isEmpty() -> {
                            viewUtils.showError("Select City")
                        }
                        /*etpostOffice.text.toString().trim().isEmpty() -> {
                            showError("Enter landmark")
                        }*/
                        tilOtherText.visibility == View.VISIBLE && otherAddress.text.toString()
                            .trim().isEmpty() -> {
                            viewUtils.showError("Enter address type, or select an option!")
                        }
                        (lat == 0.toDouble() || long == 0.toDouble()) -> {
                            startActivityForResult(
                                Intent(
                                    context,
                                    LocationPickerActivity::class.java
                                ), 23
                            )
                        }
                        else -> {
                            saveAddress()
                        }
                    }
                }
            }


            run {
                etCountry.setupDropDown(
                    listOf("India").toTypedArray(),
                    { it },
                    {
                        etCountry.setText(it)
                    }, {
                        it.show()
                        hideSoftKeyboard()
                    }
                )
            }
        }

        if (address != null)
            setData()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 23 && resultCode == RESULT_OK) {
            lat = data!!.getDoubleExtra("lat", 0.toDouble())
            long = data.getDoubleExtra("long", 0.toDouble())
        }
    }

    private fun setToolbar() {
        val toolbarBinding = viewBinding.incToolbar
        toolbarBinding.tvTitle.text = getString(R.string.app_full_name)
        toolbarBinding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun saveAddress() {
        if (address != null) {
            editAddress()
        } else {
            addAddress()
        }

    }

    private fun editAddress() {
        viewBinding.run {
            viewModel.editAddress(
                addressId = address!!.id,
                title = name.text.toString(),
                contactMobile = etPhone.text.toString(),
                city = selectedCityId!!,
                state = selectedStateId!!,
                pinCode = etPin.text.toString(),
                postOffice = etpostOffice.text.toString(),
                address = etAddress1.text.toString(),
                address2 = etAddress2.text.toString(),
                lat = lat.toString(),
                lng = long.toString(),
                contactName = name.text.toString()
            ).observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Status.Loading -> {
                        viewUtils.showLoading(parentFragmentManager)
                    }
                    is Status.Success -> {
                        viewUtils.stopShowingLoading()
                        viewUtils.showError("Address Saved!")
                        findNavController().popBackStack()
                    }
                    else -> {
                        viewUtils.stopShowingLoading()
                        viewUtils.showError("Something went wrong! retry")
                    }
                }
            }
        }
    }

    private fun addAddress() {
        viewBinding.run {
            viewModel.addAddress(
                userId = appUtils.getUser()!!.id,
                title = name.text.toString(),
                contactMobile = etPhone.text.toString(),
                city = selectedCityId!!,
                state = selectedStateId!!,
                pinCode = etPin.text.toString(),
                postOffice = etpostOffice.text.toString(),
                address = etAddress1.text.toString(),
                address2 = etAddress2.text.toString(),
                lat = lat.toString(),
                lng = long.toString(),
                contactName = name.text.toString()
            ).observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Status.Success -> {
                        viewUtils.stopShowingLoading()
                        viewUtils.showError("Address Saved!")
                        findNavController().popBackStack()
                    }
                    is Status.Loading -> {
                        viewUtils.showLoading(parentFragmentManager)
                    }
                    else -> {
                        viewUtils.stopShowingLoading()
                        viewUtils.showError("Something went wrong! retry")
                    }
                }
            }
        }
    }

    private fun setData() {
        viewBinding.run {
            validPin = true
            name.setText(address!!.contactName)
            etPhone.setText(address!!.contactMobile)
            etAddress1.setText(address!!.address)
            etState.setText(address!!.state.name)
            etAddress2.setText(address!!.address2)
            etPin.setText(address!!.pincode)
            //Log.e("landmark", "landmark get : ${address!!.landmark}", )
            //etpostOffice.setText(address!!.landmark)
            etCountry.setText("India")
            etCity.setText(address!!.city!!.name)
            selectedCityId = address!!.city!!.id
            selectedStateId = address!!.state.id

            when {
                address!!.title.equals("Home", ignoreCase = true) -> {
                    home.isChecked = true
                }
                address!!.title.equals("Work", ignoreCase = true) -> {
                    work.isChecked = true
                }
                else -> {
                    other.isChecked = true
                    viewBinding.tilOtherText.editText!!.setText(address!!.title)
                }
            }
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
                    if (response.value.result.isNullOrEmpty()) {
                        viewUtils.showError("No Data!")
                        //finish()
                    } else {
                        //cityList.addAll(response.data().result)
                        stateResponse = response.value

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
        validPin = false
        customViewModel
            .getZipByCity(selectedCityId!!)
            .observe(
                viewLifecycleOwner
            ) { response ->
                when (response) {
                    is Status.Success -> {
                        viewUtils.stopShowingLoading()
                        if (response.value.result.isNullOrEmpty()) {
                            viewUtils.showError("No available pin code, select another city/state")
                        }
                        viewBinding.run {
                            pinList.clear()
                            pinList.addAll(response.value.result.map { it.name })
                            val arrayAdapter = ArrayAdapter(
                                context!!,
                                android.R.layout.select_dialog_item,
                                pinList
                            )
                            etPin.setAdapter(arrayAdapter)
                            etPin.setOnItemClickListener { parent, view, position, id ->
                                validPin = true
                            }
                        }
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

    private fun getCityByState() {
        customViewModel
            .getAllCityByState(selectedStateId!!)
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Status.Success -> {
                        viewUtils.stopShowingLoading()
                        cities = response.value.result
                        selectedCityId = null
                        viewBinding.etCity.setupDropDown(
                            cities.toTypedArray(),
                            { it.name },
                            { city ->
                                viewBinding.etCity.setText(city.name)
                                viewBinding.etPin.setText("")
                                if (selectedCityId == null && selectedCityId != city.id) {
                                    selectedCityId = city.id
                                    getZipList()
                                }
                            },
                            {
                                if (cities.isNotEmpty()) {
                                    it.show()
                                }
                            })
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
        viewBinding.run {
            etState.setupDropDown(
                stateResponse.result.toTypedArray(), { it.name },
                { state ->
                    etCity.setText("")
                    etPin.setText("")
                    selectedStateId = state.id
                    etState.setText(state.name)
                    getCityByState()
                }, {
                    it.show()
                    hideSoftKeyboard()
                }
            )
        }

        /*viewBinding.addEditAddressComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                A2ATheme {
                    AddOrEditAddressScreen()
                }
            }
        }*/
    }

    override fun onResume() {
        super.onResume()
        if (this::stateResponse.isInitialized)
            setupOptions()
        else
            getState()
    }

    //---------------------------------- Compose UI ------------------------------------------------

    @Composable
    fun AddOrEditAddressScreen() {
        Scaffold(topBar = {
            A2ATopAppBar(title = if (address == null) "Add Address" else "Edit Address") {
                findNavController().popBackStack()
            }
        }, content = {
            ContentAddOrEditAddress()
        })
    }

    @Composable
    fun ContentAddOrEditAddress() {
        var fullName by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var address1 by remember { mutableStateOf("") }
        var address2 by remember { mutableStateOf("") }
        var country by remember { mutableStateOf("") }
        var state by remember { mutableStateOf("") }
        var city by remember { mutableStateOf("") }
        var pincode by remember { mutableStateOf("") }
        var landmark by remember { mutableStateOf("") }
        var addressType by remember { mutableStateOf("") }

        val addressTypes = listOf("Home", "Work", "Other")
        val countries = listOf("India")

        var textFieldSize by remember { mutableStateOf(Size.Zero) }

        var stateExpanded by remember { mutableStateOf(false) }
        val stateIcon = if (stateExpanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        var cityExpanded by remember { mutableStateOf(false) }
        val cityIcon = if (cityExpanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        var countryExpanded by remember { mutableStateOf(false) }
        val countryIcon = if (countryExpanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown


        if (address != null) {
            fullName = address!!.contactName
            phone = address!!.contactMobile
            address1 = address!!.address
            address2 = address!!.address2
            country = "India"
            state = address!!.state.name
            city = address!!.city.name
            pincode = address!!.pincode
            addressType = address!!.title
        }

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
                    .scrollable(rememberScrollState(), orientation = Orientation.Vertical)
            ) {
                // full name
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholder = { TextFieldPlaceHolder(text = "Full name") },
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
                    onValueChange = { if (it.length <= maxMobile) phone = it },
                    placeholder = { TextFieldPlaceHolder(text = "Phone") },
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

                //Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

                // Address 1
                OutlinedTextField(
                    value = address1,
                    onValueChange = { address1 = it },
                    placeholder = { TextFieldPlaceHolder(text = "House No, Building Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                )

                Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

                // Address 2
                OutlinedTextField(
                    value = address2,
                    onValueChange = { address2 = it },
                    placeholder = { TextFieldPlaceHolder(text = "Road no, Area, Colony") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                )

                Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

                // Country
                OutlinedTextField(
                    value = country,
                    onValueChange = { country = it },
                    placeholder = { TextFieldPlaceHolder(text = "Country") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinate ->
                            textFieldSize = coordinate.size.toSize()
                        },
                    // keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    maxLines = 1,
                    trailingIcon = {
                        Icon(
                            countryIcon,
                            "",
                            modifier = Modifier.clickable { countryExpanded = !countryExpanded })
                    }
                )

                DropdownMenu(
                    expanded = countryExpanded,
                    onDismissRequest = { countryExpanded = false },
                    modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                ) {
                    countries.forEach { label ->
                        DropdownMenuItem(onClick = {
                            country = label
                            countryExpanded = false
                        }) {
                            Text(text = label)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

                //state
                OutlinedTextField(
                    value = state,
                    onValueChange = { state = it },
                    placeholder = { TextFieldPlaceHolder(text = "State") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinate ->
                            textFieldSize = coordinate.size.toSize()
                        },
                    maxLines = 1,
                    trailingIcon = {
                        Icon(
                            stateIcon,
                            "",
                            modifier = Modifier.clickable { stateExpanded = !stateExpanded })
                    }
                )

                DropdownMenu(
                    expanded = stateExpanded,
                    onDismissRequest = { stateExpanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                ) {
                    stateResponse.result.forEach { label ->
                        DropdownMenuItem(onClick = {
                            state = label.name
                            stateExpanded = false
                            selectedStateId = label.id

                            //set city and pin to empty when user change state
                            // bcoz according to state we city and pin
                            city = ""
                            pincode = ""
                            getCityByState()
                        }) {
                            Text(text = label.name)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

                //city
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    placeholder = { TextFieldPlaceHolder(text = "City") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinate ->
                            textFieldSize = coordinate.size.toSize()
                        },
                    maxLines = 1,
                    trailingIcon = {
                        Icon(
                            cityIcon,
                            "",
                            modifier = Modifier.clickable { cityExpanded = !cityExpanded })
                    }
                )

                DropdownMenu(
                    expanded = cityExpanded,
                    onDismissRequest = { cityExpanded = false }
                ) {
                    cities.forEach { label ->
                        DropdownMenuItem(onClick = {
                            pincode = ""
                            cityExpanded = false
                            city = label.name
                            selectedCityId = label.id
                        }) {
                            Text(text = label.name)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

                Row(modifier = Modifier.fillMaxWidth()) {
                    // pincode
                    OutlinedTextField(
                        value = pincode,
                        onValueChange = { pincode = it },
                        placeholder = { TextFieldPlaceHolder(text = "PinCode") },
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    )

                    Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))
                    // landmark
                    OutlinedTextField(
                        value = landmark,
                        onValueChange = { landmark = it },
                        placeholder = { TextFieldPlaceHolder(text = "Landmark") },
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    )
                }

                //address types
                Row(modifier = Modifier.fillMaxWidth()) {
                    addressTypes.forEach { label ->
                        A2ARadioButton(label = label, addressType) {
                            addressType = it
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

            }
        }
    }

    @Preview
    @Composable
    fun AddOrEditScreenPreview() {
        AddOrEditAddressScreen()
    }
}