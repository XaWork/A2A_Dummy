package com.a2a.app.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.CommonModel
import com.a2a.app.data.model.HomeModel
import com.a2a.app.data.model.SettingsModel
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentHomeBinding
import com.a2a.app.mappers.toCommonModel
import com.a2a.app.ui.address.LocationPickerActivity
import com.a2a.app.ui.city.CityFragmentDirections
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var mainActivity: MainActivity
    private var pickupLocation: String? = null
    private var destinationLocation: String? = null
    private lateinit var home: HomeModel.Result
    private var settings: SettingsModel? = null

    private val viewModel by viewModels<CustomViewModel>()
    private lateinit var viewBinding: FragmentHomeBinding

    @Inject
    lateinit var viewUtils: ViewUtils

    @Inject
    lateinit var appUtils: AppUtils

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentHomeBinding.bind(view)
        viewBinding.mainLayout.foreground =
            ContextCompat.getDrawable(context!!, R.drawable.rect_white)

        mainActivity.showToolbarAndBottomNavigation()
        mainActivity.selectHomeNavMenu()
        mainActivity.setNavHeader()
        appUtils = AppUtils(context!!)
        settings = appUtils.getSettings()

        if (appUtils.getHome() == null)
            getHome()
        else
            setData()

        /*   viewBinding.homeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                A2ATheme {
                    HomeScreen()
                }
            }
        }*/

        with(viewBinding) {
            tvAboutUs.text = Html.fromHtml(settings!!.result.about_us, 0)
            edtPickup.setOnClickListener {
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
            }
            btnCalculatePrice.setOnClickListener {
                mainActivity.hideToolbarAndBottomNavigation()
                val action = HomeFragmentDirections.actionGlobalBookFragment(
                    pickupLocation = pickupLocation,
                    destinationLocation = destinationLocation
                )
                findNavController().navigate(action)
            }
        }
    }

    private fun getHome() {
        viewModel.getHome().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {
                    viewUtils.showLoading(parentFragmentManager)
                }
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    home = result.value.result
                    val appUtils = AppUtils(context!!)
                    appUtils.saveHomePage(result.value.result)
                    setData()
                }
                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
                }
            }
        }
    }

    private fun setData() {
        viewBinding.mainLayout.foreground = null
        home = AppUtils(context!!).getHome()!!

        setCities()
        setCarousel()
        setTestimonials()
        setCustomer()
        setBlog()
    }

    private fun setCities() {
        viewBinding.rvCity.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CityAdapter(
                context = context,
                data = home.city,
                itemClick = object : RvItemClick {
                    override fun clickWithPosition(name: String, position: Int) {
                        mainActivity.hideToolbarAndBottomNavigation()

                        val model =
                            CommonModel(
                                home.city[position]._id,
                                home.city[position].file,
                                home.city[position].name,
                                home.city[position].description,
                                home.city[position].slug,
                                home.city[position].sub_heading
                            )

                        val details =
                            Gson().toJson(model, CommonModel::class.java)
                        val action = CityFragmentDirections.actionGlobalViewDetailsFragment(
                            details = details,
                            name = "City Details"
                        )
                        findNavController().navigate(action)
                    }
                }
            )
        }
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

    val drawerItemList = listOf(
        NavigationDrawerItem(
            id = "home",
            title = "Home",
            icon = R.drawable.ic_store_mall_directory_green_24dp
        ),
        NavigationDrawerItem(
            id = "booking",
            title = "Booking",
            icon = R.drawable.ic_local_dining_green_24dp
        ),
        NavigationDrawerItem(
            id = "profile",
            title = "Profile",
            icon = R.drawable.ic_person_pin_green_24dp
        ),
        NavigationDrawerItem(
            id = "bulk",
            title = "Bulk Inquiry",
            icon = R.drawable.shopping_bag
        ),
        NavigationDrawerItem(
            id = "membership",
            title = "Membership Plan",
            icon = R.drawable.ic_rank
        ),
        NavigationDrawerItem(id = "wallet", title = "Wallet", icon = R.drawable.wallet),
        NavigationDrawerItem(
            id = "rate",
            title = "Rate App",
            icon = R.drawable.ic_thumb_up_green_24dp
        ),
        NavigationDrawerItem(
            id = "share",
            title = "Refer App & Earn Points",
            icon = R.drawable.ic_share_green_24dp
        ),
        NavigationDrawerItem(
            id = "contact",
            title = "Contact Us",
            icon = R.drawable.ic_person_pin_green_24dp
        ),
        NavigationDrawerItem(id = "logout", title = "Logout", icon = R.drawable.ic_logout),
    )

    @Composable
    fun HomeScreen() {

        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                A2AMainAppBar(onNavigationItemClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                })
            },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            drawerContent = {
                DrawerHeader()
                DrawerBody(items = drawerItemList, onItemClick = {})
            }, content = {
                ContentHome()
            })
    }

    @Composable
    fun ContentHome() {
    }
}