package com.a2a.app.ui.membership

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.databinding.FragmentBulkOrderBinding
import com.a2a.app.databinding.FragmentMemberShipBinding


class MemberShipFragment : Fragment(R.layout.fragment_member_ship) {

    private lateinit var viewBinding: FragmentMemberShipBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMemberShipBinding.bind(view)

        setToolbar()
        viewBinding.errorLayout.visibility = View.VISIBLE
    }

    private fun setToolbar() {
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}