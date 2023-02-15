package com.a2a.app.ui.book

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.data.model.AllCategoryModel
import com.a2a.app.data.model.AllSubCategoryModel
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentBook1Binding
import com.a2a.app.ui.book.component.AddressPicker
import com.a2a.app.ui.components.*
import com.a2a.app.ui.theme.*
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BookFragment1 : Fragment(R.layout.fragment_book1) {

    private lateinit var viewBinding: FragmentBook1Binding

    @Inject
    lateinit var appUtils: AppUtils

    @Inject
    lateinit var viewUtils: ViewUtils


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentBook1Binding.bind(view)

        viewBinding.book1ComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                A2ATheme {
                    BookScreen()
                }
            }
        }
    }

    //-------------------------------------------- Compose UI --------------------------------------

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun BookScreen(
        viewModel: BookViewModel = hiltViewModel()
    ) {
        val state = viewModel.state.value
        Log.e("Book1", state.toString())
        val context = LocalContext.current

        LaunchedEffect(key1 = state) {
            if (state.isLoading)
                viewUtils.showLoading(parentFragmentManager)
            if (!state.isLoading)
                viewUtils.stopShowingLoading()
            if (state.error != null) {
                //viewUtils.showLongToast(viewModel.state.value.error!!)
                //findNavController().popBackStack()
                Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            }
        }

        val bottomSheetScaffoldState =
            rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
        val scaffoldState =
            rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetScaffoldState)
        val scope = rememberCoroutineScope()

        var addressType by remember {
            mutableStateOf("")
        }

        var pickupAddress by remember {
            mutableStateOf("")
        }
        var destinationAddress by remember {
            mutableStateOf("")
        }
        BottomSheetScaffold(scaffoldState = scaffoldState, topBar = {
            A2ATopAppBar(title = stringResource(R.string.booking)) {
                findNavController().popBackStack()
            }
        }, sheetContent = {
            //Log.e("book", addresses)
            A2ABottomSheetDialog(addressList = viewModel.state.value.addresses,
                navigateToAddNewAddressScreen = {
                    findNavController().navigate(R.id.action_global_addNewAddressFragment)
                },
                onAddressChange = { address ->
                    Log.e(
                        "Book Fragment 1 ",
                        "Address change: $address\nAddress type is $addressType"
                    )
                    when (addressType) {
                        "pickup" -> viewModel.onEvent(
                            BookEvent.PickupAddressChanged(
                                listOf(
                                    address
                                )
                            )
                        )
                        "destination" -> {
                            if (viewModel.state.value.pickupAddress[0].id == address.id)
                                viewUtils.showShortToast("Pickup and Destination addresses must be different.")
                            else
                                viewModel.onEvent(
                                    BookEvent.DestinationAddressChanged(
                                        listOf(address)
                                    )
                                )
                        }
                    }
                },
                onClose = {
                    scope.launch {
                        bottomSheetScaffoldState.collapse()
                    }
                })
        }, sheetBackgroundColor = MaterialTheme.colors.CardBg, sheetPeekHeight = 0.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.MainBgColor),
                contentAlignment = Alignment.Center
            ) {
                ContentBook(viewModel = viewModel,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(it),
                    onAddressTypeChange = { type ->
                        viewModel.onEvent(BookEvent.GetAddresses)
                        addressType = type
                        scope.launch {
                            if (bottomSheetScaffoldState.isCollapsed) bottomSheetScaffoldState.expand()
                            else bottomSheetScaffoldState.collapse()
                        }
                    })

                A2AButton(
                    title = stringResource(id = R.string.book_now),
                    allCaps = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    viewModel.onEvent(BookEvent.EstimateBooking)
                }
            }
        }
    }

    @Composable
    fun ContentBook(
        viewModel: BookViewModel,
        modifier: Modifier = Modifier,
        onAddressTypeChange: (String) -> Unit
    ) {
        val state = viewModel.state.value

        val fullName by remember {
            mutableStateOf("")
        }
        var serviceType by remember {
            mutableStateOf("Normal")
        }
        var category by remember {
            mutableStateOf("")
        }
        /*val categories by remember {
            mutableStateOf(state.value.serviceTypes)
        }
        val subCategories by remember {
            mutableStateOf(state.value.subCategories.map { it.name })
        }*/
        var subCategory by remember {
            mutableStateOf("")
        }
        var pickupRange by remember {
            mutableStateOf("")
        }
        var weight by remember {
            mutableStateOf("")
        }
        var width by remember {
            mutableStateOf("")
        }
        var height by remember {
            mutableStateOf("")
        }
        var length by remember {
            mutableStateOf("")
        }
        var remarks by remember {
            mutableStateOf("")
        }
        var videoRecording by remember {
            mutableStateOf(false)
        }
        var picture by remember {
            mutableStateOf(false)
        }
        var liveGps by remember {
            mutableStateOf(false)
        }
        var liveTemperature by remember {
            mutableStateOf(false)
        }
        val context = LocalContext.current

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    start = ScreenPadding, end = ScreenPadding, top = ScreenPadding, bottom = 50.dp
                )
                .verticalScroll(rememberScrollState())
        ) {
            AddressPicker(icon = R.drawable.pickuplocation_icon,
                title = "Pickup\nLocation",
                fullName = appUtils.getUser()!!.fullName,
                address = if (state.pickupAddress.isEmpty())
                    "" else
                    state.pickupAddress[0].address,
                backgroundColor = MaterialTheme.colors.primaryVariant,
                onClick = {
                    onAddressTypeChange("pickup")
                })

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            AddressPicker(icon = R.drawable.destination_icon,
                title = "Destination\nLocation",
                fullName = appUtils.getUser()!!.fullName,
                address = if (state.destinationAddress.isEmpty())
                    "" else
                    viewModel.state.value.destinationAddress[0].address,
                backgroundColor = MaterialTheme.colors.primary,
                onClick = {
                    if (state.pickupAddress.isEmpty())
                        Toast.makeText(
                            context,
                            "First select pickup location",
                            Toast.LENGTH_SHORT
                        ).show()
                    else onAddressTypeChange("destination")
                })

            Spacer(modifier = Modifier.height(SpaceBetweenViews))

            Text(
                text = stringResource(id = R.string.select_service_type),
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(CardCornerRadius)
                    ), verticalAlignment = Alignment.CenterVertically
            ) {
                state.serviceTypes.forEach { service ->
                    A2ARadioButton(
                        label = service,
                        addressType = state.serviceType,
                        onRadioButtonSelected = {
                            viewModel.onEvent(BookEvent.ServiceTypeChanged(it))
                        })
                }
            }

            Spacer(modifier = Modifier.height(SpaceBetweenViews))

            Text(
                text = "More Information",
                color = MaterialTheme.colors.primaryVariant,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            A2ADropDown(value = category,
                label = stringResource(id = R.string.category),
                onValueChange = {
                    state.categories.forEach { category ->
                        if (category.name == it) {
                            viewModel.onEvent(BookEvent.GetSubCategories(category.id))
                            //onCatChange(category.id)
                        }
                    }
                    category = it
                },
                list = state.categories.map { it.name })

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            A2ADropDown(value = subCategory,
                label = stringResource(id = R.string.sub_category),
                onValueChange = {
                    subCategory = it
                },
                list = state.subCategories.map { it.name })

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            A2ADropDown(
                value = state.pickupRange,
                label = stringResource(id = R.string.pickup_range_km),
                onValueChange = { viewModel.onEvent(BookEvent.PickupRangeChanged(it)) },
                list = state.pickupRanges.sorted().map { it.toString() }
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
            ) {
                A2ATextField(
                    value = state.weight,
                    label = stringResource(id = R.string.weight_kg),
                    onValueChange = { viewModel.onEvent(BookEvent.WeightChanged(it)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))
                A2ATextField(
                    value = state.width,
                    label = stringResource(id = R.string.width_cm),
                    onValueChange = { viewModel.onEvent(BookEvent.WidthChanged(it))},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
            ) {
                A2ATextField(
                    value = state.height,
                    label = stringResource(id = R.string.height_cm),
                    onValueChange = { viewModel.onEvent(BookEvent.HeightChanged(it)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))
                A2ATextField(
                    value = state.length,
                    label = stringResource(id = R.string.length_cm),
                    onValueChange = { viewModel.onEvent(BookEvent.LengthChanged(it))},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            A2ATextField(
                value = state.remarks,
                label = stringResource(id = R.string.remarks),
                onValueChange = { viewModel.onEvent(BookEvent.RemarksChanged(it)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            Text(
                text = stringResource(id = R.string.paid_additional_service_optional),
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            A2ACheckbox(
                enabled = state.additionalService.videoRecordingAvailable,
                checked = viewModel.state.value.videoRecording,
                onCheckedChange = { viewModel.onEvent(BookEvent.VideoRecordingChanged(!viewModel.state.value.videoRecording)) },
                title = stringResource(id = R.string.video_recording_pd)
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            A2ACheckbox(
                enabled = state.additionalService.pictureRecordingAvailable,
                checked = state.picture,
                onCheckedChange = { viewModel.onEvent(BookEvent.PictureChanged(!state.picture)) },
                title = stringResource(id = R.string.picture_pd)
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            A2ACheckbox(
                enabled = state.additionalService.liveGPSTrackingAvailable,
                checked = state.liveGPS,
                onCheckedChange = { viewModel.onEvent(BookEvent.LiveGPSChanged(!state.liveGPS)) },
                title = stringResource(id = R.string.live_gps_tracking)
            )

            Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

            A2ACheckbox(
                enabled = state.additionalService.liveTemperatureTrackingAvailable,
                checked = state.liveTemperature,
                onCheckedChange = { viewModel.onEvent(BookEvent.LiveTemperatureChanged(!state.liveTemperature)) },
                title = stringResource(id = R.string.live_temperature_tracking)
            )
        }
    }

    @Preview
    @Composable
    fun BookScreenPreview() {
        BookScreen()
    }

}