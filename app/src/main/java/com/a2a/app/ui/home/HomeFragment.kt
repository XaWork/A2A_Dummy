package com.a2a.app.ui.home

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.common.Status
import com.a2a.app.data.model.HomeModel
import com.a2a.app.data.model.SettingsModel
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentHomeBinding
import com.a2a.app.ui.components.A2AButton
import com.a2a.app.ui.home.component.*
import com.a2a.app.ui.theme.*
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var mainActivity: MainActivity
    private lateinit var home: HomeModel.Result
    private var settings: SettingsModel? = null

    private val viewModel by viewModels<CustomViewModel>()
    private lateinit var viewBinding: FragmentHomeBinding

    @Inject
    lateinit var viewUtils: ViewUtils

    @Inject
    lateinit var appUtils: AppUtils

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentHomeBinding.bind(view)
        appUtils = AppUtils(context!!)
        settings = appUtils.getSettings()

        if (appUtils.getHome() == null)
            getHome()
        else {
            home = appUtils.getHome()!!
            setData()
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
        viewBinding.homeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                A2ATheme {
                    HomeScreen()
                }
            }
        }
    }

    //------------------------------------------------- Compose UI ---------------------------------


    private val drawerItemList = listOf(
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
        NavigationDrawerItem(
            id = "my plan",
            title = "My Plan",
            icon = R.drawable.food_plan
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

    private val bottomNavigationItems = listOf(
        BottomNavItem(
            id = "city",
            name = "City",
            icon = R.drawable.city
        ),
        BottomNavItem(
            id = "service",
            name = "Service Type",
            icon = R.drawable.sub_category
        ),
        BottomNavItem(
            id = "category",
            name = "Category",
            icon = R.drawable.category
        ),
        BottomNavItem(
            id = "deals",
            name = "Deals",
            icon = R.drawable.deals
        )
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
                }, onNavigateToBook = {
                    findNavController().navigate(R.id.action_global_bookFragment)
                }, onNavigateToWallet = {
                    findNavController().navigate(R.id.action_global_walletFragment)
                })
            },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            drawerContent = {
                DrawerHeader()
                DrawerBody(
                    items = drawerItemList,
                    scope = scope,
                    state = scaffoldState,
                    onItemClick = { navigationDrawerItem ->
                        when (navigationDrawerItem.id) {
                            "home" -> findNavController().navigate(R.id.action_global_homeFragment)
                            "booking" -> {
                                findNavController().navigate(R.id.action_global_orderFragment)
                            }
                            "profile" -> findNavController().navigate(R.id.action_global_profileFragment)
                            "membership" -> findNavController().navigate(R.id.action_global_memberShipFragment)
                            "bulk" -> findNavController().navigate(R.id.action_global_bulkOrderFragment)
                            "my plan" -> findNavController().navigate(R.id.action_global_myPlanFragment)
                            "wallet" -> findNavController().navigate(R.id.action_global_walletFragment)
                            "rate" -> {
                                rateApp()
                            }
                            "share" -> {
                                shareApp()
                            }
                            "contact" -> findNavController().navigate(R.id.action_global_contactUsFragment)
                            "logout" -> {
                                appUtils.logOut()
                                findNavController().navigate(R.id.action_global_onBoardingFragment)
                            }
                        }
                    })
            }, bottomBar = {
                BottomNavigationBar(
                    items = bottomNavigationItems,
                    onItemClick = { bottomNavItem ->
                        when (bottomNavItem.id) {
                            "city" -> findNavController().navigate(R.id.action_global_cityFragment)
                            "service" -> findNavController().navigate(R.id.action_global_serviceTypeFragment)
                            "category" -> findNavController().navigate(R.id.action_global_categoryFragment)
                            "deals" -> findNavController().navigate(R.id.action_global_dealsFragment)
                        }
                    })
            }, content = {
                ContentHome(it)
            })
    }

    @Composable
    fun ContentHome(padding: PaddingValues) {
        Column(
            modifier = Modifier
                .padding(paddingValues = padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colors.MainBgColor)
        ) {
            CityList(cities = home.city.sortedBy { it.name })
            SliderList(sliders = home.slider)
            BookOrCalculatePrice()
            About(Html.fromHtml(settings!!.result.about_us, 0).toString())
            TestimonialList(testimonials = home.testimonials)
            CustomerList(customers = home.clients)
            BlogList(blogs = home.blogs)
        }
    }

    @Composable
    fun CityList(cities: List<HomeModel.Result.City>) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                vertical = SpaceBetweenViewsAndSubViews,
                horizontal = ScreenPadding
            ),
            horizontalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
        ) {
            items(cities, key = { city -> city._id }) { city ->
                CityItem(city = city)
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun SliderList(sliders: List<HomeModel.Result.Slider>) {
        val state = rememberPagerState()
        Box {
            SliderView(
                state = state,
                sliderImages = sliders,
                modifier = Modifier.align(Alignment.TopCenter)
            )

            DotIndicators(
                totalDots = home.slider.size,
                selectedIndex = state.currentPage,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        LaunchedEffect(key1 = state.currentPage) {
            delay(3000)
            var newPosition = state.currentPage + 1
            if (newPosition > home.slider.size - 1)
                newPosition = 0

            state.animateScrollToPage(newPosition)
        }
    }

    @Composable
    fun BookOrCalculatePrice() {
        var pickupPinCode by remember {
            mutableStateOf("")
        }
        var dropOfPinCode by remember {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(ScreenPadding)
        ) {
            OutlinedTextField(
                value = pickupPinCode,
                onValueChange = {
                    pickupPinCode = it
                },
                singleLine = true,
                label = { Text(text = "Pickup Pin Code") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_location_on_24),
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = dropOfPinCode,
                onValueChange = {
                    dropOfPinCode = it
                },
                singleLine = true,
                label = { Text(text = "Drop-of Pin Code") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_location_on_24),
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViews))

            A2AButton(
                title = "Book / Calculate Price",
                allCaps = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape)
            ) {
                findNavController().navigate(R.id.action_global_bookFragment)
            }

            Spacer(modifier = Modifier.height(SpaceBetweenViews))

            Text(
                text = "Same Day Super Fast 12 Hours Intercity Delivery",
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    fun TestimonialList(testimonials: List<HomeModel.Result.Testimonial>) {
        LazyRow(
            contentPadding = PaddingValues(
                vertical = SpaceBetweenViewsAndSubViews,
                horizontal = ScreenPadding
            ),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(testimonials, key = { testimonial -> testimonial._id }) { testimonial ->
                TestimonialItem(testimonial = testimonial)
            }
        }
    }

    @Composable
    fun CustomerList(customers: List<HomeModel.Result.Client>) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                vertical = SpaceBetweenViewsAndSubViews,
                horizontal = ScreenPadding
            ),
            horizontalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
        ) {
            items(customers, key = { customer -> customer._id }) { customer ->
                CustomerItem(customer = customer)
            }
        }
    }

    @Composable
    fun BlogList(blogs: List<HomeModel.Result.Blog>) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                vertical = SpaceBetweenViewsAndSubViews,
                horizontal = ScreenPadding
            ),
            horizontalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
        ) {
            items(blogs, key = { blog -> blog._id }) { blog ->
                BlogItem(blog = blog)
            }
        }
    }

    @OptIn(ExperimentalUnitApi::class)
    @Composable
    fun About(about: String) {
        Column(
            modifier = Modifier
                .padding(ScreenPadding)
                .background(color = MaterialTheme.colors.CardBg)
                .clip(RoundedCornerShape(CardCornerRadius))
        ) {
            Text(
                text = "About",
                modifier = Modifier
                    .background(color = MaterialTheme.colors.primary)
                    .padding(horizontal = HighPadding, vertical = LowPadding)
                    .align(Alignment.End),
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            Text(
                text = about,
                color = Color.Black,
                fontSize = 14.sp,
                letterSpacing = TextUnit(0.5f, TextUnitType.Sp),
                modifier = Modifier.padding(ScreenPadding)
            )
        }
    }

    @Preview
    @Composable
    fun HomeScreenPreview() {
        A2ATheme {
            HomeScreen()
        }
    }

    private fun shareApp() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody = buildString {
            appendLine("http://play.google.com/store/apps/details?id=" + context?.packageName)
        }
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(
            Intent.createChooser(
                sharingIntent,
                getString(R.string.share_via)
            )
        )
    }

    private fun rateApp() {
        val uri = Uri.parse("market://details?id=" + context?.packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        // To count with Play market back stack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context?.packageName)
                )
            )
        }
    }
}