package com.a2a.app.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.databinding.FragmentBulkOrderBinding
import com.a2a.app.databinding.FragmentProfileBinding

class BulkOrderFragment : Fragment(R.layout.fragment_bulk_order) {
    private lateinit var viewBinding: FragmentBulkOrderBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentBulkOrderBinding.bind(view)

        setToolbar()
    }

    private fun setToolbar() {
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}