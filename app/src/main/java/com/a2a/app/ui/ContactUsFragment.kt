package com.a2a.app.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.data.model.SettingsModel
import com.a2a.app.databinding.FragmentContactUsBinding
import com.a2a.app.utils.AppUtils


class ContactUsFragment : Fragment(R.layout.fragment_contact_us) {

    private lateinit var viewBinding: FragmentContactUsBinding
    private var settings: SettingsModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentContactUsBinding.bind(view)

        settings = AppUtils(context!!).getSettings()
        setToolbar()

        with(viewBinding) {
            settings!!.result.run {
                tvMobile.text = contact_phone
                whatsappInfo.text = whatsapp
                supportMail.text = contact_email
                location.text = Html.fromHtml(address, 0)

                callUs.setOnClickListener {
                    var mobile = ""
                    for (i in contact_phone.trim()) {
                        if (i == '(')
                            break
                        else
                            mobile += i
                    }

                    dialNumber(mobile)
                }
                mailUs.setOnClickListener {
                    sendMail(contact_email)
                }
            }
        }
    }

    private fun sendMail(mail: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(mail))
        startActivity(Intent.createChooser(intent, "Send Via"))
    }

    private fun dialNumber(mobile: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$mobile")
        startActivity(intent)
    }

    private fun setToolbar() {
        viewBinding.toolbar.title = getString(R.string.nav_contact_us)
        viewBinding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}