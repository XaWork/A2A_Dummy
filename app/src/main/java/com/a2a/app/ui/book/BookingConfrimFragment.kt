package com.a2a.app.ui.book

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.databinding.FragmentBookingConfrimBinding


class BookingConfrimFragment : Fragment(R.layout.fragment_booking_confrim) {

    private lateinit var viewBinding: FragmentBookingConfrimBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentBookingConfrimBinding.bind(view)

        with(viewBinding){
            deliveryDateTime.text = "Afternoon"
            continueShopping.setOnClickListener{
                findNavController().navigate(R.id.action_global_homeFragment)
            }
        }
    }
}