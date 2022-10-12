package com.a2a.app.ui.address

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
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
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddNewAddressFragment : Fragment(R.layout.fragment_add_new_address) {

    private lateinit var stateResponse: StateListModel
    lateinit var cites: List<CityListModel.Result>
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
                        }/*
                        etpostOffice.text.toString().trim().isEmpty() -> {
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
            viewBinding.run {
                viewModel.editAddress(
                    userId = AppUtils(context!!).getUser()?.id!!,
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
        } else {
            viewBinding.run {
                viewModel.addAddress(
                    userId = AppUtils(context!!).getUser()?.id!!,
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

    private fun getCityAndZip() {
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
                        cites = response.value.result
                        selectedCityId = null
                        viewBinding.etCity.setupDropDown(
                            cites.toTypedArray(),
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
                                if (cites.isNotEmpty()) {
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
    }

    override fun onResume() {
        super.onResume()
        if (this::stateResponse.isInitialized)
            setupOptions()
        else
            getCityAndZip()
    }
}