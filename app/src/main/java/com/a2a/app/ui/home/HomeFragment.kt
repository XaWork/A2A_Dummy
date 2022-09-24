package com.a2a.app.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.CommonModel
import com.a2a.app.data.model.HomeModel
import com.a2a.app.data.model.SettingsModel
import com.a2a.app.data.network.CustomApi
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentHomeBinding
import com.a2a.app.mappers.toCommonModel
import com.a2a.app.ui.address.LocationPickerActivity
import com.a2a.app.utils.AppUtils
import com.google.gson.Gson

class HomeFragment :
    BaseFragment<FragmentHomeBinding, CustomViewModel, CustomRepository>(FragmentHomeBinding::inflate) {

    private lateinit var mainActivity: MainActivity
    private var pickupLocation: String? = null
    private var destinationLocation: String? = null
    private lateinit var home: HomeModel.Result
    private var settings: SettingsModel? = null

    private val pickUpLocationResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val address = result.data?.getStringExtra("address")
                viewBinding.edtPickup.setText(address)
                pickupLocation = address
            }
        }

    private val dropOfLocationResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val address = result.data?.getStringExtra("address")
                viewBinding.edtDropOf.setText(address)
                destinationLocation = address
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
        mainActivity.setNavHeader()
        settings = AppUtils(context!!).getSettings()

        if (AppUtils(context!!).getHome() == null)
            getHome()
        else
            setData()

        with(viewBinding) {
            tvAboutUs.text = Html.fromHtml(settings!!.result.about_us, 0)
          /*  edtPickup.setOnClickListener {
                pickUpLocationResultLauncher.launch(
                    Intent(
                        context,
                        LocationPickerActivity::class.java
                    )
                )
            }
            edtDropOf.setOnClickListener {
                dropOfLocationResultLauncher.launch(
                    Intent(
                        context,
                        LocationPickerActivity::class.java
                    )
                )
            }*/
            btnCalculatePrice.setOnClickListener {
                mainActivity.hideToolbarAndBottomNavigation()
                val action = HomeFragmentDirections.actionGlobalBookFragment(
                    /*pickupLocation = pickupLocation,
                    destinationLocation = destinationLocation*/
                )
                findNavController().navigate(action)
                /*if (pickupLocation.isNullOrEmpty() || destinationLocation.isNullOrEmpty())
                    toast("Select pickup and delivery location")
                else {
                    mainActivity.hideToolbarAndBottomNavigation()
                    val action = HomeFragmentDirections.actionGlobalBookFragment(
                        *//*pickupLocation = pickupLocation,
                        destinationLocation = destinationLocation*//*
                    )
                    findNavController().navigate(action)
                }*/
            }
        }
    }

    private fun getHome() {
        viewModel.getHome().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {
                    showLoading()
                }
                is Status.Success -> {
                    stopShowingLoading()
                    val appUtils = AppUtils(context!!)
                    appUtils.saveHomePage(result.value.result)
                    setData()
                }
                is Status.Failure -> {
                    stopShowingLoading()
                }
            }
        }
    }

    private fun setData() {
        home = AppUtils(context!!).getHome()!!

        setCarousel()
        setTestimonials()
        setCustomer()
        setBlog()
    }

    private fun setCarousel() {
        viewBinding.rvCarousal.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CarousalAdapter(
                home.slider,
                context
            )
        }
    }

    private fun setTestimonials() {
        viewBinding.rvTestimonial.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = TestiMonialAdapter(
                home.testimonials,
                context
            )
        }
    }

    private fun setCustomer() {
        viewBinding.rvCustomer.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CustomerAdapter(context, home.clients)
        }
    }

    private fun setBlog() {
        viewBinding.rvBlog.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = BlogAdapter(context, home.blogs, object : RvItemClick {
                override fun clickWithPosition(name: String, position: Int) {
                    mainActivity.hideToolbarAndBottomNavigation()
                    val details = home.blogs[position].toCommonModel()
                    val action = HomeFragmentDirections.actionGlobalViewDetailsFragment(
                        name = "Blog",
                        details = Gson().toJson(details, CommonModel::class.java)
                    )
                    findNavController().navigate(action)
                }
            })
        }
    }
}