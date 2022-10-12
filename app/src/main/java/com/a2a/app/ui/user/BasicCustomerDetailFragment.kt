package com.a2a.app.ui.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.common.Status
import com.a2a.app.data.model.VerifyOtpModel
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.CustomerBasicDetailsBinding
import com.a2a.app.databinding.FragmentBasicCustomerDetailBinding
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BasicCustomerDetailFragment : Fragment(R.layout.fragment_basic_customer_detail) {

    private lateinit var contentCustomerBasicDetails: CustomerBasicDetailsBinding
    private lateinit var user: VerifyOtpModel.Data
    private lateinit var viewBinding: FragmentBasicCustomerDetailBinding
    private val viewModel by viewModels<UserViewModel>()

    @Inject
    lateinit var appUtils: AppUtils

    @Inject
    lateinit var viewUtils: ViewUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentBasicCustomerDetailBinding.bind(view)
        contentCustomerBasicDetails = viewBinding.contentCustomerBasicDetails

        user = appUtils.getUser()!!

        with(contentCustomerBasicDetails) {
            etEmail.setText(user.email)
            etEmail.isEnabled = false
            etUsername.isEnabled = false
            if (user.fullName.isNotEmpty()) {
                etFullname.setText(user.fullName)
            }
            etUsername.setText(user.mobile)

            saveDetails.setOnClickListener {
                saveData()
            }
        }
    }

    private fun saveData() {

        if (validates()) {
            val fullName = contentCustomerBasicDetails.etFullname.text.toString()
            val mobile = contentCustomerBasicDetails.etUsername.text.toString()

            viewModel.editProfile(user.id, fullName, mobile)
                .observe(viewLifecycleOwner) { response ->
                    when (response) {
                        is Status.Loading -> {
                            viewUtils.showLoading(parentFragmentManager)
                        }

                        is Status.Success -> {
                            viewUtils.stopShowingLoading()
                            if (response.value.status.equals("success", ignoreCase = true)) {
                                val customer = AppUtils(context!!).getUser()?.copy(
                                    fullName = fullName
                                )
                                AppUtils(context!!).saveUser(customer!!)
                                viewUtils.showShortToast(response.value.message)
                                findNavController().popBackStack()
                            } else {
                                viewUtils.showError("Failed to update, retry!")
                            }
                        }

                        is Status.Failure -> {
                            viewUtils.stopShowingLoading()
                            //toast(response.error().message.toString())
                        }

                    }
                }

        } else {
            viewUtils.showShortToast(getString(R.string.error_incorrect_data))
        }
    }


    private fun validates(): Boolean {
        with(contentCustomerBasicDetails) {
            tilFirstName.isErrorEnabled = false
            tilUsername.isErrorEnabled = false

            var validation = true

            val username = tilEmail.editText!!.text.toString()
            val fullName = etFullname.text.toString()

            if (fullName.isEmpty()) {
                tilFirstName.error = getString(R.string.enter_valid_details)
                validation = false
            }
            if (username.isEmpty()) {
                tilUsername.error = getString(R.string.enter_valid_details)
                validation = false
            }
            return validation
        }
    }

}