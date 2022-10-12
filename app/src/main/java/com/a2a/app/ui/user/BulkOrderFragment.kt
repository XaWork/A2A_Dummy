package com.a2a.app.ui.user

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.common.Status
import com.a2a.app.data.model.CityModel
import com.a2a.app.data.model.CustomBulkOrder
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentBulkOrderBinding
import com.a2a.app.hideSoftKeyboard
import com.a2a.app.setupDropDown
import com.a2a.app.utils.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BulkOrderFragment : Fragment(R.layout.fragment_bulk_order) {

    private var mCityBrandList: ArrayList<CityModel.Result> = ArrayList()
    private val viewModel by viewModels<CustomViewModel>()
    private lateinit var viewBinding: FragmentBulkOrderBinding

    @Inject
    lateinit var viewUtils: ViewUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentBulkOrderBinding.bind(view)

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
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Status.Loading -> {
                        viewUtils.showLoading(parentFragmentManager)
                    }
                    is Status.Success -> {
                        viewUtils.stopShowingLoading()
                        mCityBrandList.addAll(result.value.result)
                        setData()
                    }
                    is Status.Failure -> {
                        viewUtils.stopShowingLoading()
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
                viewUtils.showError("Please select the city")
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
}