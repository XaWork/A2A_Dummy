package com.a2a.app.ui.user

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.common.Status
import com.a2a.app.data.model.VerifyOtpModel
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.CustomerBasicDetailsBinding
import com.a2a.app.databinding.FragmentBasicCustomerDetailBinding
import com.a2a.app.ui.components.A2AButton
import com.a2a.app.ui.components.A2ATopAppBar
import com.a2a.app.ui.theme.*
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BasicCustomerDetailFragment : Fragment(R.layout.fragment_basic_customer_detail) {

    private lateinit var viewBinding: FragmentBasicCustomerDetailBinding
    private val viewModel by viewModels<UserViewModel>()

    @Inject
    lateinit var appUtils: AppUtils

    @Inject
    lateinit var viewUtils: ViewUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentBasicCustomerDetailBinding.bind(view)

        viewBinding.editProfileComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                A2ATheme {
                    EditProfileScreen()
                }
            }
        }
    }

    private fun saveData(fullname: String, email: String, mobile: String) {
        val userid = appUtils.getUser()!!.id
        viewModel.editProfile(userid, fullname, mobile)
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Status.Loading -> {
                        viewUtils.showLoading(parentFragmentManager)
                    }

                    is Status.Success -> {
                        viewUtils.stopShowingLoading()
                        if (response.value.status.equals("success", ignoreCase = true)) {
                            val customer = appUtils.getUser()?.copy(
                                fullName = fullname
                            )
                            appUtils.saveUser(customer!!)
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
    }

    @Composable
    fun EditProfileScreen() {
        Scaffold(
            topBar = {
                A2ATopAppBar(title = "Edit Profile") {
                    findNavController().popBackStack()
                }
            },
            content = {
                ContentEditProfile()
            }
        )
    }

    @Composable
    fun ContentEditProfile() {
        val user = appUtils.getUser()!!
        var fullName by remember { mutableStateOf(user.fullName) }
        var email by remember { mutableStateOf("") }
        var mobile by remember { mutableStateOf(user.mobile) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.MainBgColor),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(ScreenPadding)
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
            ) {
                Text(
                    text = stringResource(id = R.string.basic_details),
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

                Text(
                    text = "Enter the information below",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(SpaceBetweenViews))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(CardCornerRadius))
                        .background(color = MaterialTheme.colors.CardBg),
                    elevation = CardElevation
                ) {
                    Column(modifier = Modifier.padding(ScreenPadding)) {
                        OutlinedTextField(
                            value = user.fullName,
                            onValueChange = { fullName = it },
                            placeholder = { Text(text = "Full name", color = Color.Gray) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true,
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = Color.Black)
                        )

                        Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = { Text(text = "Email", color = Color.Gray) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true,
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = Color.Black)
                        )

                        Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

                        OutlinedTextField(
                            value = user.mobile,
                            onValueChange = { mobile = it },
                            placeholder = { Text(text = "Mobile", color = Color.Gray) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = Color.Black)
                        )
                    }
                }
            }

            A2AButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                title = "Save Details",
                allCaps = false
            ) {
                if (fullName.isEmpty() || email.isEmpty() || mobile.isEmpty())
                    viewUtils.showShortToast("Fill all data")
                else
                    saveData(fullName, email, mobile)
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun EditProfileScreenPreview() {
        EditProfileScreen()
    }
}