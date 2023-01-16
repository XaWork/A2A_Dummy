package com.a2a.app.ui.onboarding.signin

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.common.Status
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.FragmentSignInBinding
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private var detailId: String? = ""
    private var deviceToken: String? = ""

    @Inject
    lateinit var viewUtils: ViewUtils
    @Inject
    lateinit var appUtils: AppUtils

    lateinit var viewBinding: FragmentSignInBinding
    val viewModel by viewModels<UserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewBinding.userViewModel = viewModel
        viewBinding = FragmentSignInBinding.bind(view)

        lifecycleScope.launch {
            appUtils.getToken1.collect { token ->
                deviceToken = token
            }
        }

        with(viewBinding) {
            detailId = ""
            etOtp.isEnabled = false
            btnLogin.text = "GET OTP"
            btnLogin.setOnClickListener {
                if (TextUtils.isEmpty(detailId)) {
                    with(viewBinding) {
                        val mobile = etPhoneNumber.text.toString()
                        if (mobile.isNotEmpty()) {
                            fetchOtp()
                        }
                    }
                } else {
                    validateOtp()
                }
            }
        }
    }

    private fun fetchOtp() {
        with(viewBinding) {
            val mobile = etPhoneNumber.text.toString()
            if (mobile.isNotEmpty()) {/*
                val deviceToken = appUtils.getToken()

                viewModel.fetchOtp(mobile, deviceToken!!)*/
                viewModel.fetchOtp(mobile, deviceToken!!).observe(viewLifecycleOwner) { response ->
                    when (response) {
                        is Status.Loading -> {
                            viewUtils.showLoading(
                                parentFragmentManager,
                                "fetching Otp",
                                "This will only take a short while"
                            )
                        }

                        is Status.Success -> {
                            viewUtils.stopShowingLoading()
                            val loginResponse = response.value
                            if (loginResponse.status == "success") {
                                etOtp.isEnabled = true
                                etPhoneNumber.isEnabled = false
                                viewBinding.btnLogin.text = "LOGIN"
                                detailId = loginResponse.message
                                viewUtils.showError("Otp has been sent to registered mobile number")
                            } else {
                                viewUtils.showError("Mobile number is not registered, Please check the number entered or Sign Up")
                            }
                        }
                        is Status.Failure -> {
                            viewUtils.stopShowingLoading()
                        }
                    }
                }
            } else {
                Toast.makeText(
                    activity,
                    "Please correct the information entered",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun validateOtp() {
        with(viewBinding) {
            if (validates()) {
                val phoneNumber = etPhoneNumber.text.toString()
                val otp = etOtp.text.toString()
                val deviceToken = appUtils.getToken()

                Log.e("TAG", "device token: $deviceToken")

                viewModel.verifyOtp(phoneNumber, otp, deviceToken!!)
                    .observe(viewLifecycleOwner) { response ->
                        when (response) {
                            is Status.Loading -> {
                                viewUtils.showLoading(
                                    parentFragmentManager,
                                    "Validating otp",
                                    "This will only take a short while"
                                )
                            }

                            is Status.Success -> {
                                viewUtils.stopShowingLoading()
                                val otpResponse = response.value
                                if (otpResponse.status == "success") {
                                    viewUtils.showError("Verified")
                                    AppUtils(context!!).saveUser(response.value.data)

                                    // check which data is saved
                                    Log.e("TAG", "validateOtp: ${otpResponse.data}")

                                    moveToDashBoard()
                                } else {
                                    viewUtils.showError("Wrong otp, Please enter correct otp")
                                }
                            }

                            is Status.Failure -> {
                                viewUtils.stopShowingLoading()
                                Toast.makeText(
                                    activity,
                                    response.errorBody.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
            } else {
                Toast.makeText(
                    activity,
                    "Please correct the information entered",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun validates(): Boolean {
        var validation = true

        val phoneNumber = viewBinding.etPhoneNumber.text.toString()

        if (phoneNumber.isEmpty()) {
            Toast.makeText(context, "Mobile Number cannot be left blank!", Toast.LENGTH_LONG)
                .show()
            validation = false
        } else if (viewBinding.etOtp.text.toString().isEmpty()) {
            Toast.makeText(context, "Otp cannot be left blank!", Toast.LENGTH_LONG).show()
            validation = false
        }

        return validation
    }

    private fun moveToDashBoard() {
        findNavController().navigate(R.id.action_global_homeFragment)
    }

    /* override fun getFragmentBinding(
         inflater: LayoutInflater,
         container: ViewGroup?
     ) = FragmentSignInBinding.inflate(inflater, container, false)

     override fun getViewModel() = UserViewModel::class.java

     override fun getFragmentRepository() =
         UserRepository(remoteDataSource.getBaseUrl().create(UserApi::class.java))

     override fun showEmptyTextFieldToast(message: String) {
         toast(message)
     }*/


}