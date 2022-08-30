package com.a2a.app.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.common.BaseFragment
import com.a2a.app.data.network.CustomApi
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentHomeBinding
import com.a2a.app.ui.address.LocationPickerActivity

class HomeFragment :
    BaseFragment<FragmentHomeBinding, CustomViewModel, CustomRepository>(FragmentHomeBinding::inflate) {

    private lateinit var mainActivity: MainActivity
    private val pickUpLocationResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK){
                val address = result.data?.getStringExtra("address")
                viewBinding.edtPickup.setText(address)
            }
        }
    private val dropOfLocationResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK){
                val address = result.data?.getStringExtra("address")
                viewBinding.edtDropOf.setText(address)
            }
        }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getViewModel() = CustomViewModel::class.java

    override fun getFragmentRepository() =
        CustomRepository(remoteDataSource.getBaseUrl().create(CustomApi::class.java))

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.showToolbarAndBottomNavigation()
        mainActivity.selectHomeNavMenu()

        setCarousel()
        setTestimonials()
        setCustomer()
        setBlog()

        with(viewBinding) {
            edtPickup.setOnClickListener {

                pickUpLocationResultLauncher.launch(Intent(context, LocationPickerActivity::class.java))
            }
            edtDropOf.setOnClickListener {

                dropOfLocationResultLauncher.launch(Intent(context, LocationPickerActivity::class.java))
            }
        }
    }

    private fun setCarousel() {
        viewBinding.rvCarousal.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CarousalAdapter(
                listOf(R.drawable.rect_gray, R.drawable.rounded_back, R.drawable.side_nav_bar),
                context
            )
        }
    }

    private fun setTestimonials() {
        viewBinding.rvTestimonial.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = TestiMonialAdapter(
                listOf("", "", "", "", "", "", "", "", "", "", "", "", "", ""),
                context
            )
        }
    }

    private fun setCustomer() {
        viewBinding.rvCustomer.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CustomerAdapter(context)
        }
    }

    private fun setBlog() {
        viewBinding.rvBlog.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = BlogAdapter(context)
        }
    }
}