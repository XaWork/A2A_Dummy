package com.a2a.app.ui.book

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.a2a.app.R
import com.a2a.app.data.model.ConfirmBookingModel
import com.a2a.app.databinding.FragmentBookingConfrimBinding
import com.google.gson.Gson


class BookingConfrimFragment : Fragment(R.layout.fragment_booking_confrim) {

    private lateinit var viewBinding: FragmentBookingConfrimBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentBookingConfrimBinding.bind(view)

        with(viewBinding){
            //get arguments
            val args: BookingConfrimFragmentArgs by navArgs()
            val confirmationResponse = Gson().fromJson(args.confirmation, ConfirmBookingModel::class.java)

            deliveryDateTime.text = confirmationResponse.timeslot
            orderId.text = "Order Number #${confirmationResponse.orderId}"

            continueShopping.setOnClickListener{
                findNavController().navigate(R.id.action_global_homeFragment)
            }
        }
    }
}