package com.a2a.app.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.Status
import com.a2a.app.data.model.VerifyOtpModel
import com.a2a.app.data.network.UserApi
import com.a2a.app.data.repository.UserRepository
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.CustomerBasicDetailsBinding
import com.a2a.app.databinding.FragmentBasicCustomerDetailBinding
import com.a2a.app.utils.AppUtils

class BasicCustomerDetailFragment :
    BaseFragment<FragmentBasicCustomerDetailBinding, UserViewModel, UserRepository>(
        FragmentBasicCustomerDetailBinding::inflate
    ) {

    private lateinit var contentCustomerBasicDetails: CustomerBasicDetailsBinding
    private lateinit var user: VerifyOtpModel.Data

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentCustomerBasicDetails = viewBinding.contentCustomerBasicDetails

        user = AppUtils(context!!).getUser()!!

        with(contentCustomerBasicDetails) {
            if (user != null) {
                etEmail.setText(user.email)
                etEmail.isEnabled = false
                etUsername.isEnabled = false
                if (user.fullName.isNotEmpty()) {
                    etFullname.setText(user.fullName)
                }
                etUsername.setText(user.mobile)
            } else
                findNavController().navigate(R.id.action_global_onBoardingFragment)

            saveDetails.setOnClickListener {
                saveData()
            }
        }
    }

    private fun saveData() {

        if (validates()) {
            val fullName = contentCustomerBasicDetails.etFullname.text.toString()
            val mobile = contentCustomerBasicDetails.etUsername.text.toString()

            viewModel.editProfile(user.id, fullName, mobile).observe(viewLifecycleOwner){response->
                when (response) {
                    is Status.Loading -> {
                        showLoading()
                    }

                    is Status.Success -> {
                        stopShowingLoading()
                        if (response.value.status.equals("success", ignoreCase = true)) {
                            val customer = AppUtils(context!!).getUser()?.copy(
                                fullName = fullName
                            )
                            AppUtils(context!!).saveUser(customer!!)
                            toast(response.value.message)
                            findNavController().popBackStack()
                        } else {
                            showError("Failed to update, retry!")
                        }
                    }

                    is Status.Failure -> {
                        stopShowingLoading()
                        //toast(response.error().message.toString())
                    }

                }
            }

        } else {
           toast(getString(R.string.error_incorrect_data))
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

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentBasicCustomerDetailBinding.inflate(inflater, container, false)

    override fun getViewModel() = UserViewModel::class.java

    override fun getFragmentRepository() = UserRepository(
        remoteDataSource.getBaseUrl().create(
            UserApi::class.java
        )
    )

}