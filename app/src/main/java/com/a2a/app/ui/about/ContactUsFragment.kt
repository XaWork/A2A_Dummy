package com.a2a.app.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.data.model.SettingsModel
import com.a2a.app.databinding.FragmentContactUsBinding
import com.a2a.app.ui.about.component.ContactItem
import com.a2a.app.ui.components.A2ATopAppBar
import com.a2a.app.ui.theme.A2ATheme
import com.a2a.app.ui.theme.MainBgColor
import com.a2a.app.ui.theme.ScreenPadding
import com.a2a.app.ui.theme.SpaceBetweenViews
import com.a2a.app.utils.AppUtils


class ContactUsFragment : Fragment(R.layout.fragment_contact_us) {

    private lateinit var viewBinding: FragmentContactUsBinding
    private var settings: SettingsModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentContactUsBinding.bind(view)

        settings = AppUtils(context!!).getSettings()
        setData()
    }

    private fun setData() {
        viewBinding.contactUsComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                A2ATheme {
                    ContactUsScreen()
                }
            }
        }
    }

    private fun sendMail() {
        val mail = settings!!.result.contact_email
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(mail))
        startActivity(Intent.createChooser(intent, "Send Via"))
    }

    private fun dialNumber() {
        val mobile = settings!!.result.contact_phone.split(" ")
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$mobile")
        startActivity(intent)
    }

    private fun getContactInfo(): List<ContactInfo> {
        var contactInfoList = emptyList<ContactInfo>()
        settings?.result?.run {
            contactInfoList = listOf(
                ContactInfo(
                    id = "phone",
                    info = contact_phone,
                    icon = R.drawable.call
                ),
                ContactInfo(
                    id = "whatsapp",
                    info = whatsapp,
                    icon = R.drawable.call
                ),
                ContactInfo(
                    id = "mail",
                    info = contact_email,
                    icon = R.drawable.mail
                ),
                ContactInfo(
                    id = "address",
                    info = Html.fromHtml(address, 0).toString(),
                    icon = R.drawable.location
                ),
            )
        }
        return contactInfoList
    }


    //-------------------------------------- Compose UI --------------------------------------------

    @Composable
    fun ContactUsScreen() {
        Scaffold(topBar = {
            A2ATopAppBar(title = stringResource(id = R.string.nav_contact_us)) {
                findNavController().popBackStack()
            }
        }, content = {
            ContentContactUs()
        })
    }

    @Composable
    fun ContentContactUs() {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.MainBgColor)
                .padding(ScreenPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Text(
                    text = stringResource(id = R.string.customer_support),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(SpaceBetweenViews))

                Image(
                    painter = painterResource(id = R.drawable.logo), contentDescription = "logo",
                    modifier = Modifier.size(120.dp)
                )

                Spacer(modifier = Modifier.height(SpaceBetweenViews))

                Divider()
            }

            items(getContactInfo()) { item ->
                ContactItem(item, onItemClick = {
                    when (item.id) {
                        "phone" -> dialNumber()
                        "whatsapp" -> {}
                        "mail" -> sendMail()
                    }
                })
            }

        }
    }
}