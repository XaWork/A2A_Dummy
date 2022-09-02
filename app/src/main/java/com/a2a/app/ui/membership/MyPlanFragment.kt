package com.a2a.app.ui.membership

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.databinding.FragmentBulkOrderBinding
import com.a2a.app.databinding.FragmentMyPlanBinding


class MyPlanFragment : Fragment(R.layout.fragment_my_plan) {

    private lateinit var viewBinding: FragmentMyPlanBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMyPlanBinding.bind(view)

        setToolbar()
    }

    private fun setToolbar() {
        viewBinding.incToolbar.toolbar.title = "My Plans"
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}