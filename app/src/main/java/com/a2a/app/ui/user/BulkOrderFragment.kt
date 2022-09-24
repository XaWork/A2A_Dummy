package com.a2a.app.ui.user

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.Status
import com.a2a.app.data.model.CityModel
import com.a2a.app.data.model.CustomBulkOrder
import com.a2a.app.data.network.CustomApi
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentBookBinding
import com.a2a.app.databinding.FragmentBulkOrderBinding
import com.a2a.app.databinding.FragmentProfileBinding
import com.a2a.app.hideSoftKeyboard
import com.a2a.app.setupDropDown

class BulkOrderFragment :
    BaseFragment<FragmentBulkOrderBinding, CustomViewModel, CustomRepository>(
        FragmentBulkOrderBinding::inflate
    ) {

    private var mCityBrandList: ArrayList<CityModel.Result> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
        getCities()

        viewBinding.flPlaceBulkOrder.setOnClickListener {
            save()
        }
    }

    private fun setToolbar() {
        viewBinding.incToolbar.toolbar.title = getString(R.string.bulk_order)
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getCities() {
        viewModel.getAllCities()
        viewModel.allCities.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {
                    showLoading()
                }
                is Status.Success -> {
                    stopShowingLoading()
                    mCityBrandList.addAll(result.value.result)
                    setData()
                }
                is Status.Failure -> {
                    stopShowingLoading()
                }
            }
        }
    }

    private fun setData() {
        viewBinding.contentBulkOrder.etBulkCity.setupDropDown(
            mCityBrandList.toTypedArray(), {
                it.name
            }, {
                viewBinding.contentBulkOrder.etBulkCity.setText(it.name)
            }, {
                if (mCityBrandList.isNotEmpty()) {
                    it.show()
                }
                hideSoftKeyboard()
            }
        )
    }

    private fun save() {
        with(viewBinding.contentBulkOrder) {
            if (validates()) {
                val firstName = etFullName.text.toString()
                val email = etEmail.text.toString()
                val phone = etMobile.text.toString()
                val address = etAddress.text.toString()
                val city = etBulkCity.text.toString()
                val message = etMessage.text.toString()

                val bulkOrder = CustomBulkOrder()
                bulkOrder.firstName = firstName
                bulkOrder.email = email
                bulkOrder.phone = phone
                bulkOrder.address = address
                bulkOrder.city = city
                bulkOrder.message = message

               /* viewModel.createBulkOrder(bulkOrder).observe(this, Observer { response ->
                    when (response!!.status()) {
                        Status.LOADING -> {
                            showLoading()
                        }
                        Status.SUCCESS -> {
                            stopShowingLoading()
                            alert(response.data().message)
                        }
                        Status.ERROR, Status.EMPTY -> {
                            stopShowingLoading()
                            Toast.makeText(
                                baseContext,
                                response.error().message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })*/
            }
            /*else {
                Toast.makeText(this, getString(R.string.error_incorrect_data), Toast.LENGTH_SHORT)
                    .show()
            }*/
        }
    }

    private fun validates(): Boolean {
        with(viewBinding.contentBulkOrder) {
            tilFullName.isErrorEnabled = false
            tilEmail.isErrorEnabled = false
            tilPhoneNumber.isErrorEnabled = false
            tilAddress.isErrorEnabled = false
            tilMessage.isErrorEnabled = false

            var validation = true

            val firstName = etFullName.text.toString()
            val email = etEmail.text.toString()
            val phone = etMobile.text.toString()
            val address = etAddress.text.toString()
            val city = etBulkCity.text.toString()
            val message = etMessage.text.toString()

            if (firstName.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || message.isEmpty()) {
                tilFullName.error = getString(R.string.enter_valid_details)
                validation = false
            }

            if (city.isEmpty()) {
                showError("Please select the city")
                validation = false
            }
            return validation
        }
    }


    private fun alert(msg: String) {
        val alert = AlertDialog.Builder(context!!)
            .setTitle(getString(R.string.app_name))
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.alert_ok)) { dialog, which ->
                findNavController().popBackStack()
            }.create()
        alert.show()

        val buttonbackground = alert.getButton(DialogInterface.BUTTON_POSITIVE)
        buttonbackground.setTextColor(Color.BLACK)

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentBulkOrderBinding.inflate(inflater, container, false)

    override fun getViewModel() = CustomViewModel::class.java

    override fun getFragmentRepository() =
        CustomRepository(remoteDataSource.getBaseUrl().create(CustomApi::class.java))
}