package com.a2a.app.ui.servicetype

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.databinding.FragmentCityBinding
import com.a2a.app.databinding.FragmentServiceTypeBinding
import com.a2a.app.ui.common.CommonAdapter


class ServiceTypeFragment : Fragment(R.layout.fragment_service_type) {
    private lateinit var viewBinding: FragmentServiceTypeBinding
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentServiceTypeBinding.bind(view)
        mainActivity.showToolbarAndBottomNavigation()

        viewBinding.rvServiceType.run{
            layoutManager = LinearLayoutManager(context)
            //adapter = CommonAdapter(context)
        }
    }
}