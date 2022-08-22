package com.a2a.app.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.databinding.FragmentContactUsBinding


class ContactUsFragment : Fragment(R.layout.fragment_contact_us) {

    private lateinit var viewBinding: FragmentContactUsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentContactUsBinding.bind(view)

        setToolbar()

        with(viewBinding){
            callUs.setOnClickListener{
                val mobile = tvMobile.text.toString()
                dialNumber(mobile)
            }
            mailUs.setOnClickListener {
                sendMail("support@tastes2plate.com")
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
        viewBinding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}