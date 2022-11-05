package com.a2a.app.ui.user

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.databinding.FragmentProfileBinding
import com.a2a.app.ui.components.A2ATopAppBar
import com.a2a.app.ui.theme.*
import com.a2a.app.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var viewBinding: FragmentProfileBinding

    @Inject
    lateinit var appUtils: AppUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentProfileBinding.bind(view)

        viewBinding.profileComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ProfileScreen()
            }
        }
    }

    private fun moveToAddressListScreen() {
        findNavController().navigate(R.id.action_profileFragment_to_addressListFragment)
    }

    private fun moveToEditProfileScreen() {
        findNavController().navigate(R.id.action_profileFragment_to_basicCustomerDetailFragment)
    }

    @Composable
    fun ProfileScreen() {
        Scaffold(topBar = {
            A2ATopAppBar(title = "Profile") {
                findNavController().popBackStack()
            }
        }, content = {
            ContentProfile()
        })
    }

    @Composable
    fun ContentProfile() {
        val user = appUtils.getUser()!!

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.MainBgColor)
                .padding(ScreenPadding)
        ) {
            Text(
                text = stringResource(id = R.string.basic_details),
                color = Color.Black,
                fontSize = 20.sp
            )
            Text(
                text = stringResource(id = R.string.basic_info_about_you),
                color = Color.Gray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViews))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.CardBg)
                    .padding(HighPadding)
                    .clip(RoundedCornerShape(LowCornerRadius)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = ParagraphStyle()) {
                            withStyle(style = SpanStyle(fontSize = 16.sp, color = Color.Black)) {
                                append("${user.fullName}\n\n")
                            }
                            withStyle(style = SpanStyle(fontSize = 16.sp, color = Color.Gray)) {
                                //append("Email: ${user.email}\n\nMobile: ${user.mobile}\n")
                                append(user.email.let{"Email : $it\n\n"})
                                append(user.mobile.let{"Mobile : $it\n\n"})
                            }
                        }
                    },
                    modifier = Modifier.align(Alignment.TopStart),
                )

                Spacer(modifier = Modifier.height(SpaceBetweenViews))

                Text(
                    text = stringResource(id = R.string.edit),
                    color = Blue200,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clickable { moveToEditProfileScreen() }
                )
            }

            Spacer(modifier = Modifier.height(SpaceBetweenViews))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.CardBg)
                    .padding(HighPadding)
                    .clip(RoundedCornerShape(CardCornerRadius))
                    .clickable { moveToAddressListScreen() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.your_addresses),
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
        }
    }

    @Preview
    @Composable
    fun ProfileScreenPreview() {
        ProfileScreen()
    }
}