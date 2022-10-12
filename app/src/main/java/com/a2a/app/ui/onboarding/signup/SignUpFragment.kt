package com.a2a.app.ui.onboarding.signup

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.common.Status
import com.a2a.app.data.model.VerifyOtpModel
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.FragmentSignUpBinding
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {


    private var detailId: String? = ""
    private val pattern = Pattern.compile(EMAIL_PATTERN)
    private var matcher: Matcher? = null
    private var phoneNumber: String? = ""
    lateinit var viewBinding: FragmentSignUpBinding
    private val viewModel by viewModels<UserViewModel>()
    private var data: VerifyOtpModel? = null
    @Inject
    lateinit var viewUtils: ViewUtils
    @Inject
    lateinit var appUtils: AppUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentSignUpBinding.bind(view)

        with(viewBinding) {
            detailId = ""
            etOtp.isEnabled = false
            btnGetOtp.text = getString(R.string.get_otp)
            btnGetOtp.setOnClickListener {
                if (tc.isChecked) {
                    if (detailId?.isEmpty()!!) {
                        signUp()
                    } else {
                        validateOtp()
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Please accept the Terms and Conditions!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            val text =
                "By signing up, you agree to our <span style=\"color:#de2228\">Terms</span> and <span style=\"color:#de2228\">Conditions</span>"
            terms.text = Html.fromHtml(text.trim())

            terms.setOnClickListener {
                //startActivity(Intent(context, TermsActivity::class.java))
            }
            rgUserType.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbCorporate -> {
                        showCorporateUserFields()
                    }
                    R.id.rbIndividual -> {
                        showIndividualUserFields()
                    }
                }
            }
        }
    }

    private fun showCorporateUserFields() {
        with(viewBinding) {
            //set radio button background
            scrollView.stopNestedScroll()
            rbCorporate.setBackgroundResource(R.drawable.storke)
            rbIndividual.setBackgroundResource(0)

            etFullName.visibility = View.VISIBLE
            etCompanyAddress.visibility = View.VISIBLE
            etCompanyName.visibility = View.VISIBLE
            etGST.visibility = View.VISIBLE
            etPan.visibility = View.VISIBLE
            etCompanyRegistrationCertificate.visibility = View.VISIBLE
        }
    }

    private fun showIndividualUserFields() {
        with(viewBinding) {
            //set radio button background
            rbIndividual.setBackgroundResource(R.drawable.storke)
            rbCorporate.setBackgroundResource(0)

            etFullName.visibility = View.GONE
            etCompanyAddress.visibility = View.GONE
            etCompanyName.visibility = View.GONE
            etGST.visibility = View.GONE
            etPan.visibility = View.GONE
            etCompanyRegistrationCertificate.visibility = View.GONE
        }
    }

    private fun signUp() {
        with(viewBinding) {
            val email = etEmail.text.toString()
            phoneNumber = etPhoneNumber.text.toString()
            val referredBy = referral.text.toString()
            var deviceToken = appUtils.getToken()
            if (validates()) {
                viewModel.registration(
                    email = email,
                    mobile = phoneNumber!!,
                    deviceToken = deviceToken!!,
                    reffer = referredBy
                ).observe(viewLifecycleOwner) { response ->
                    when (response) {
                        is Status.Loading -> {
                            viewUtils.showLoading(parentFragmentManager)
                        }

                        is Status.Success -> {
                            viewUtils.stopShowingLoading()
                            if (response.value.status == "success") {
                                etOtp.isEnabled = true
                                detailId = response.value.message
                                viewBinding.btnGetOtp.text = "Verify OTP"
                                Toast.makeText(
                                    activity,
                                    response.value.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    activity,
                                    response.value.message,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }

                        is Status.Failure -> {
                            viewUtils.stopShowingLoading()
                            viewUtils.tryAgain()
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
            if (validates() && !TextUtils.isEmpty(etOtp.text)) {
                val phoneNumber = etPhoneNumber.text.toString()
                val otp = etOtp.text.toString()
                var deviceToken = appUtils.getToken()

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
                                    data = otpResponse
                                    AppUtils(context!!).saveUser(otpResponse.data)

                                    moveToDashBoard()
                                } else {
                                    viewUtils.showShortToast(response.value.message)
                                }
                            }
                            is Status.Failure -> {
                                viewUtils.stopShowingLoading()
                                viewUtils.tryAgain()
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

    private fun moveToDashBoard() {
        findNavController().navigate(R.id.action_global_homeFragment)
    }

    private fun validates(): Boolean {

        var validation = true

        val email = viewBinding.etEmail.text.toString()
        val mobile = viewBinding.etPhoneNumber.text.toString()


        if (!validateEmail(email)) {
            Toast.makeText(context, "Invalid Email!", Toast.LENGTH_LONG).show()
            validation = false
        } else if (mobile.isEmpty()) {
            Toast.makeText(
                context,
                getString(R.string.please_enter_mobile_number),
                Toast.LENGTH_LONG
            ).show()
            validation = false
        }

        return validation
    }

    private fun validateEmail(email: String): Boolean {
        matcher = pattern.matcher(email)
        return matcher!!.matches()
    }

    companion object {
        private const val EMAIL_PATTERN =
            "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$"
    }

}