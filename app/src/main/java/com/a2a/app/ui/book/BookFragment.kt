package com.a2a.app.ui.book

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.*
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.*
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.FragmentBookBinding
import com.a2a.app.ui.address.AddressSelectionFragment
import com.a2a.app.ui.address.SaveAddressListener
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class BookFragment :
    Fragment(R.layout.fragment_book) {

    private val customViewModel by viewModels<CustomViewModel>()
    private val viewModel by viewModels<UserViewModel>()
    private val serviceTypes = ArrayList<String>()
    private lateinit var cutOffTimeResponse: CheckCutOffTimeModel.Result
    private var pickupTime: String = ""
    private val cutOffTimeServices = ArrayList<String>()
    private lateinit var viewBinding: FragmentBookBinding
    private lateinit var mainActivity: MainActivity
    private var categories = ArrayList<AllCategoryModel.Result>()
    private lateinit var selectedCategory: AllCategoryModel.Result
    private lateinit var selectedSubCategory: AllSubCategoryModel.Result
    private var categoryNames = ArrayList<String>()
    private var subCategoryNames = ArrayList<String>()
    private lateinit var checkTimeSlotResponse: NormalTimeslotModel
    private var subCategories = ArrayList<AllSubCategoryModel.Result>()
    private var categoryName = ""
    var categoryId = ""
    var subCategoryId = ""
    var deliveryType = ""
    private var subCategoryName = ""
    private var bookingDate = ""
    private var pickupDate = ""
    private var choosedPickupTimeSlot = "Time"
    private var deliveryDate = ""
    private var deliveryTimeSlot = ""
    private var bookingDateTime = ""
    private var pickUpLocation: AddressListModel.Result? = null
    private var destinationLocation: AddressListModel.Result? = null
    private var pickupRange = "0"
    private var weight = "0"
    private var width = "1"
    private var height = "1"
    private var length = "1"
    private var videoRecordingOfPickupAndDelivery = ""
    private var liveGPS = ""
    private var liveTemperatureTracking = ""
    private var pictureOfDeliveryAndPickup = ""
    private lateinit var deliveryTimeslots: List<String>
    private val pickupTimeSlots = mutableListOf<String>()
    private lateinit var normalDialogConfirmButton: TextView
    private lateinit var estimateBookingResponse: EstimateBookingModel

    @Inject
    lateinit var appUtils: AppUtils

    @Inject
    lateinit var viewUtils: ViewUtils

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentBookBinding.bind(view)
        setToolbar()

        getAllCategories()
        getServiceTypes()

        with(viewBinding.contentBook) {
            val user = appUtils.getUser()
            tvPickupUserName.text = user?.fullName
            tvDestinationUserName.text = user?.fullName

            //pickup range
            val pickupRanges = listOf(2, 5, 10, 15, 20, 25, 30, 40)
            val arrayAdapter = ArrayAdapter(context!!, R.layout.single_text_view, pickupRanges)
            viewBinding.contentBook.acPickupRange.run {
                setAdapter(arrayAdapter)
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun afterTextChanged(p0: Editable?) {}
                })
            }

            clPickUpLocation.setOnClickListener {
                val addressSelection = AddressSelectionFragment()
                addressSelection.saveSetSaveListener(object : SaveAddressListener {
                    override fun onSaved(address: AddressListModel.Result) {
                        tvPickupAddress.text = address.address
                        pickUpLocation = address
                    }
                })
                addressSelection.show(parentFragmentManager, addressSelection.tag)
            }

            clDestinationLocation.setOnClickListener {
                if (pickUpLocation == null)
                    viewUtils.showShortToast("First select pickup location")
                else {
                    val addressSelection = AddressSelectionFragment()
                    addressSelection.saveSetSaveListener(object : SaveAddressListener {
                        override fun onSaved(address: AddressListModel.Result) {
                            if (pickUpLocation!!.id == address.id)
                                viewUtils.showShortToast("Pickup and Destination location should not be same")
                            else {
                                tvDestinationAddress.text = address.address
                                destinationLocation = address
                                cutoffTimeCheck()
                            }
                        }
                    })
                    addressSelection.show(parentFragmentManager, addressSelection.tag)
                }
            }
        }
        viewBinding.btnBookNow.setOnClickListener {
            checkFieldData()
        }
    }

    private fun checkFieldData() {
        with(viewBinding.contentBook) {
            //get data that user enter
            pickupRange = acPickupRange.text.toString().trim()
            weight = edtWight.text.toString().trim()
            width = edtWidth.text.toString().trim()
            height = edtHeight.text.toString().trim()
            length = edtLength.text.toString().trim()
            liveGPS = if (cbGps.isChecked)
                "Y"
            else
                "N"
            videoRecordingOfPickupAndDelivery = if (cbVideoRecording.isChecked)
                "Y"
            else
                "N"
            liveTemperatureTracking = if (cbTemperature.isChecked)
                "Y"
            else
                "N"
            pictureOfDeliveryAndPickup = if (cbPictureOfPickupAndDelivery.isChecked)
                "Y"
            else
                "N"

            Log.d(
                "booking",
                "PickupLocation - $pickUpLocation\n" +
                        "DestinationLocation - $destinationLocation\n" +
                        "Category - $categoryId\n" +
                        "SubCategory - $subCategoryId\n" +
                        "Pickup Range - $pickupRange\n" +
                        "Weight - $weight\n" +
                        "Width - $width\n" +
                        "Height - $height\n" +
                        "Length - $length\n" +
                        "Delivery Type - $deliveryType\n" +
                        "Booking Date - $bookingDate\n" +
                        "Booking time - $bookingDateTime\n"
            )

            //if user not enter any value then send by default 1
            if (width.isEmpty())
                width = "1"
            if (length.isEmpty())
                length = "1"
            if (height.isEmpty())
                height = "1"

            //check any value is not null or empty
            if (pickUpLocation == null)
                viewUtils.showShortToast("Select Pickup Location")
            else if (destinationLocation == null)
                viewUtils.showShortToast("Select Destination Location")
            else if (categoryId.isEmpty())
                viewUtils.showShortToast("Select Category")
            else if (subCategoryId.isEmpty())
                viewUtils.showShortToast("Select SubCategory")
            else if (pickupRange.isEmpty())
                viewUtils.showShortToast("Select Pickup Range")
            else if (weight.isEmpty())
                edtWight.error = "Enter Weight"
            else {
                if (deliveryType.isEmpty())
                    viewUtils.showShortToast("Please specify delivery type(Normal/Express/Super Fast)")
                else {
                    if (deliveryType == "Normal")
                        showNormalDeliveryDialog("show")
                    else
                        estimateBooking()
                    //showExpressDeliveryDialog("show")
                }
            }
        }
    }


    //----------------------------------- Date Time Handler ----------------------------------------


    private fun showNormalDeliveryDialog(whatToDo: String) {
        val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.dialog_normal_delivery)
        normalDialogConfirmButton = dialog.findViewById(R.id.btnConfirm)
        val tvPickupTimeSlot = dialog.findViewById<TextView>(R.id.tvPickupTimeslot)
        val pickupDatePicker = dialog.findViewById<EditText>(R.id.edtPickupDate)
        val deliveryDatePicker = dialog.findViewById<EditText>(R.id.edtDeliveryDate)
        val lytDeliveryOption = dialog.findViewById<ConstraintLayout>(R.id.lytDeliveryOption)
        val tvDeliveryTimeSlot = dialog.findViewById<TextView>(R.id.tvDeliveryTimeSlot)
        val tvEstimateCost = dialog.findViewById<TextView>(R.id.tvEstimateCost)
        val tvCutOffText = dialog.findViewById<TextView>(R.id.tvCutOffText)

        pickupDatePicker.setOnClickListener {
            pickupDate = ""
            dialog.dismiss()
            showDatePickerDialog("pickup", pickupDatePicker)
        }
        deliveryDatePicker.setOnClickListener {
            showDatePickerDialog("delivery", deliveryDatePicker)
        }

        normalDialogConfirmButton.setOnClickListener {
            when (normalDialogConfirmButton.text) {
                "Check available timeslots" -> {
                    if (pickupDate.isEmpty()) {
                        viewUtils.showShortToast("First select pickup date")
                    } else {
                        dialog.dismiss()
                        checkAvailableTimeslots()
                    }
                }
                "Calculate Price" -> {
                    if (choosedPickupTimeSlot.isEmpty())
                        viewUtils.showShortToast("Select timeslot")
                    else {
                        dialog.dismiss()
                        estimateBooking()
                    }
                }
                "Continue" -> {
                    if (deliveryTimeSlot.isEmpty())
                        viewUtils.showShortToast("Select timeslot for delivery")
                    else {
                        dialog.dismiss()
                        moveToOrderConfirmationScreen()
                    }
                }
            }
        }

        when (whatToDo) {
            "show" -> {
                //empty data before showing dialog
                pickupDate = ""
                pickupTimeSlots.clear()
                choosedPickupTimeSlot = "Time"
                deliveryDate = ""
                deliveryTimeSlot = ""

                dialog.show()
            }
            "pickup timeslot" -> {
                normalDialogConfirmButton.text = "Calculate Price"
                //choosedPickupTimeSlot = pickupTimeSlots[0]
                //tvCutOffText.text = checkTimeSlotResponse.timeslot
                pickupDatePicker.setText(pickupDate)
                tvPickupTimeSlot.text = choosedPickupTimeSlot
                tvPickupTimeSlot.setupDropDown(pickupTimeSlots.toTypedArray(), { it }, {
                    if (checkTiming(it)) {
                        choosedPickupTimeSlot = it
                        tvPickupTimeSlot.text = choosedPickupTimeSlot
                    } else
                        viewUtils.showShortToast("Please select time 6 hours after of current time")
                }, {
                    it.show()
                    hideSoftKeyboard()
                })
                Log.e("book", "pickup time slots : $pickupTimeSlots")
                dialog.show()
            }
            "edit" -> {
                lytDeliveryOption.visibility = View.VISIBLE
                estimateBookingResponse.run {
                    tvEstimateCost.text =
                        "Estimate Cost: \nRs.${estimations.estimated_price}/."
                    normalDialogConfirmButton.text = "Continue"

                    pickupDatePicker.setText(pickupDate)
                    tvPickupTimeSlot.text = choosedPickupTimeSlot
                    tvCutOffText.text = remarks
                    tvPickupTimeSlot.setupDropDown(pickupTimeSlots.toTypedArray(), { it }, {
                        if (checkTiming(it)) {
                            choosedPickupTimeSlot = it
                            tvPickupTimeSlot.text = choosedPickupTimeSlot
                        } else
                            viewUtils.showShortToast("Please select time 6 hours after of current time")
                    }, {
                        it.show()
                        hideSoftKeyboard()
                    })

                    //delivery
                    deliveryDate = delivery_date.toDate(
                        "dd/MM/yyyy",
                        "yyyy-MM-dd"
                    )
                    deliveryDatePicker.setText(
                        deliveryDate
                    )
                    deliveryTimeslots = delivery_slots

                    /*deliveryTimeSlot = deliveryTimeslots[0]
                    tvDeliveryTimeSlot.text = deliveryTimeSlot*/

                    tvDeliveryTimeSlot.setupDropDown(deliveryTimeslots.toTypedArray(), { it }, {

                        /*Default Delivery date = 28/10/2022 and Timeslot = Night
                            Now Suppose, customer changes the timeslot to Morning or afternoon then Delivery date need to change to 29/10/2022.
                            Example 2
                            Default Delivery date = 29/10/2022 and Timeslot = Morning
                            Now Suppose, customer changes the timeslot to afternoon or night then Delivery date = 29/10/2022 same as it is*/

                        checkDeliveryDate(
                            deliveryDatePicker = deliveryDatePicker,
                            dlDate = estimateBookingResponse.delivery_date.toDate(
                                "dd/MM/yyyy",
                                "yyyy-MM-dd"
                            ),
                            deliveryTimeSlot = it
                        )
                        deliveryTimeSlot = it
                        tvDeliveryTimeSlot.text = deliveryTimeSlot
                    }, {
                        it.show()
                        hideSoftKeyboard()
                    })
                }
                dialog.show()
            }
        }
    }

    private fun moveToOrderConfirmationScreen() {
        val orderConfirmationData = OrderConfirmationData(
            selectedCategory,
            selectedSubCategory,
            viewBinding.contentBook.edtWight.text.toString(),
            estimateBookingResponse,
            deliveryType,
            pickupDate,
            choosedPickupTimeSlot,
            deliveryTimeSlot,
            deliveryDate,
            videoRecordingOfPickupAndDelivery,
            pictureOfDeliveryAndPickup,
            liveTemperatureTracking,
            liveGPS,
            pickupRange,
            width,
            length,
            height,
            pickUpLocation!!,
            destinationLocation!!
        )

        val orderConfirmationStringData =
            Gson().toJson(orderConfirmationData, OrderConfirmationData::class.java)
        val action =
            BookFragmentDirections.actionBookFragmentToOrderConfirmationFragment(
                orderConfirmationStringData
            )
        findNavController().navigate(action)
    }

    private fun showExpressDeliveryDialog() {
        val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.dialog_express_delivery)
        val confirmButton = dialog.findViewById(R.id.btnScheduleConfirm) as TextView
        val timePicker = dialog.findViewById<TextView>(R.id.tvDeliveryTimeSlot)
        val deliveryDatePicker = dialog.findViewById<EditText>(R.id.edtDeliveryDate)
        val tvEstimateCost = dialog.findViewById<TextView>(R.id.tvEstimateCost)
        val tvCutOffText = dialog.findViewById<TextView>(R.id.tvCutOffText)

        /*deliveryDatePicker.setOnClickListener {
            showDatePickerDialog("delivery", deliveryDatePicker)
        }*/

        confirmButton.setOnClickListener {
            dialog.dismiss()
            when (confirmButton.text) {
                "Calculate Price" -> estimateBooking()
                else -> {
                    if (deliveryTimeSlot.isEmpty()) {
                        showExpressDeliveryDialog()
                        viewUtils.showShortToast("Select timeslot for delivery")
                    } else {
                        moveToOrderConfirmationScreen()
                    }
                }
            }
        }

        estimateBookingResponse.run {
            deliveryDate = delivery_date.toDate(
                "dd/MM/yyyy",
                "yyyy-MM-dd"
            )
        }
        confirmButton.text = "Continue"
        tvCutOffText.text = estimateBookingResponse.remarks
        tvEstimateCost.text =
            "Estimate Cost: \nRs.${estimateBookingResponse.estimations.estimated_price}/."

        deliveryDatePicker.setText(
            estimateBookingResponse.delivery_date.toDate(
                "dd/MM/yyyy",
                "yyyy-MM-dd"
            )
        )

        deliveryTimeslots =
            estimateBookingResponse.delivery_slots

        /* deliveryTimeSlot = deliveryTimeslots[0]
         timePicker.text = deliveryTimeSlot*/

        timePicker.setupDropDown(deliveryTimeslots.toTypedArray(), { it }, {

            /*Default Delivery date = 28/10/2022 and Timeslot = Night
            Now Suppose, customer changes the timeslot to Morning or afternoon then Delivery date need to change to 29/10/2022.

            Example 2
            Default Delivery date = 29/10/2022 and Timeslot = Morning
            Now Suppose, customer changes the timeslot to afternoon or night then Delivery date = 29/10/2022 same as it is*/

            checkDeliveryDate(
                deliveryDatePicker, estimateBookingResponse.delivery_date.toDate(
                    "dd/MM/yyyy",
                    "yyyy-MM-dd"
                ), it
            )

            deliveryTimeSlot = it
            timePicker.text = deliveryTimeSlot
        }, {
            it.show()
            hideSoftKeyboard()
        })
        dialog.show()
    }

    private fun checkDeliveryDate(
        deliveryDatePicker: EditText,
        dlDate: String,
        deliveryTimeSlot: String
    ) {
        val sdf = SimpleDateFormat("HH:mm")
        //val calendar = Calendar.getInstance()
        //val currentTime = sdf.format(calendar.time).split(":").toList()[0].toInt()

        /*8AM to 12 -> Morning
        12AM to 16PM -> Afternoon
        16PM to 19:30PM -> Evening
        19:30PM to 22:30AM -> Night*/

        val settings = appUtils.getSettings()!!
        val morning: Date = sdf.parse(settings.result.morning_end) as Date
        val afternoon = sdf.parse(settings.result.afternoon_end) as Date
        val evening = sdf.parse(settings.result.evening_end) as Date
        val night = sdf.parse(settings.result.night_end) as Date

        val finalDeliveryTime = when (deliveryType) {
            "Normal" -> sdf.parse(cutOffTimeResponse.final_delivery_time)
            "Express" -> sdf.parse(cutOffTimeResponse.express_final_delivery_time_first)
            else -> sdf.parse(cutOffTimeResponse.super_final_delivery_time_first)
        }

        val availableTimeslots =
            if (finalDeliveryTime?.before(morning) == true)
                listOf("Morning", "Afternoon", "Evening", "Night")
            else if (finalDeliveryTime?.before(afternoon) == true)
                listOf("Afternoon", "Evening", "Night")
            else if (finalDeliveryTime?.before(evening) == true)
                listOf("Evening", "Night")
            else if (finalDeliveryTime?.before(night) == true)
                listOf("Night")
            else
                listOf("")

        if (!availableTimeslots.contains(deliveryTimeSlot)) {
            val sd = SimpleDateFormat("dd/MM/yyyy")
            val cal = Calendar.getInstance()
            cal.time = sd.parse(dlDate) as Date
            cal.add(Calendar.DATE, 1)
            val newDate = sd.format(cal.time)
            deliveryDatePicker.setText(newDate.toString())
            deliveryDate = newDate
        }

        Log.e(
            "book",
            "Check Delivery Data -> \n " +
                    "Final Delivery time : $finalDeliveryTime\n" +
                    "Current timeslot : $availableTimeslots\n" +
                    "Morning end : $morning\n" +
                    "Afternoon end : $afternoon\n" +
                    "Evening end : $evening\n" +
                    "Night end : $night\n"
        )
    }

    private fun showDatePickerDialog(type: String, dateTimePicker: EditText) {
        when (type) {
            "pickup" -> {}
            "delivery" -> {}
        }
        val calendar: Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context!!,
            { _, year1, month1, day1 ->
                Log.e("book", "Type : $type\n Date : $day1${month1 + 1}/$year1")
                when (type) {
                    "pickup" -> {
                        pickupDate = "$day1/${month1 + 1}/$year1"
                        checkAvailableTimeslots()
                    }
                    "delivery" -> deliveryDate = "$day1/${month1 + 1}/$year1"
                }
                dateTimePicker.setText("$day1/${month1 + 1}/$year1")
            }, year, month, dayOfMonth
        )

        if (type == "delivery" && deliveryType == "Normal") {
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val date = simpleDateFormat.parse(pickupDate)
            val deliveryCalendar = Calendar.getInstance()

            deliveryCalendar.time = date!!

            deliveryCalendar.set(
                deliveryCalendar.get(Calendar.YEAR),
                deliveryCalendar.get(Calendar.MONTH),
                deliveryCalendar.get(Calendar.DAY_OF_MONTH)
            )

            val minDateInMilliSeconds = deliveryCalendar.timeInMillis
            datePickerDialog.datePicker.minDate = minDateInMilliSeconds
        } else {
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        }
        datePickerDialog.show()
    }

    private fun checkTiming(timeSlot: String): Boolean {
        //user can select only timeslot after normal time(this time comes from setting api),
        // if user select time after normal time then return true otherwise false
        val timeslotList = timeSlot.split("-").toList()
        pickupTime = timeslotList[0]
        val datetime1 = "$pickupDate ${timeslotList[0]}"
        timeslotList[1]

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR_OF_DAY, 6)
        val currentDateTime = sdf.format(calendar.time)

        Log.e("time", "Booking Time : $bookingDateTime\nCurrent Time : $currentDateTime")

        try {
            val time1 = sdf.parse(datetime1)
            val time2 = sdf.parse(currentDateTime.toString())

            Log.e("time", "Time difference is : ${time1!!.after(time2)}")

            return time1.after(time2)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    //----------------------------------- API Calls ------------------------------------------------

    private fun cutoffTimeCheck() {
        Log.e(
            "book",
            "Start City : ${pickUpLocation!!.city.id}\n End city : ${destinationLocation!!.city.id}"
        )

        val startDestination = pickUpLocation!!.city.id
        val endDestination = destinationLocation!!.city.id
        viewModel.cutoffTimeCheck(startDestination, endDestination)
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Status.Loading -> {
                        viewUtils.showLoading(parentFragmentManager)
                    }
                    is Status.Success -> {
                        viewUtils.stopShowingLoading()
                        cutOffTimeResponse = result.value.result
                        additionalServiceToBeEnabled()
                        setServices()
                    }
                    is Status.Failure -> {
                        viewUtils.stopShowingLoading()
                    }
                }
            }
    }

    private fun additionalServiceToBeEnabled() {
        //if any additional service value is 0 then that should be disable
        with(viewBinding.contentBook) {
            cutOffTimeResponse.run {
                if (picture_recording == "0")
                    cbPictureOfPickupAndDelivery.isEnabled = false
                else if (video_recording == "0")
                    cbVideoRecording.isEnabled = false
                else if (live_temparature == "0")
                    cbTemperature.isEnabled = false
                else if (live_tracking == "0")
                    cbGps.isEnabled = false
            }
        }
    }

    private fun checkAvailableTimeslots() {
        Log.e(
            "book", "check available timeslots -> \n" +
                    "schedule date : ${pickupDate.toDate("yyyy-MM-dd", "dd/MM/yyyy")}\n" +
                    "destination location : ${destinationLocation!!.id}\n" +
                    "pickup location : ${pickUpLocation!!.id}"
        )

        viewModel.normalTimeSlots(
            pickupDate.toDate("yyyy-MM-dd", "dd/MM/yyyy"),
            destinationLocation!!.id,
            pickUpLocation!!.id
        ).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {
                    viewUtils.showLoading(parentFragmentManager)
                }
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    checkTimeSlotResponse = result.value
                    pickupTimeSlots.clear()
                    for (timeslot in result.value.normal_slots.slots)
                        pickupTimeSlots.add(timeslot.pickup.time)

                    pickupTimeSlots.sort()
                    showNormalDeliveryDialog("pickup timeslot")
                }
                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
                }
            }
        }
    }

    private fun estimateBooking() {
        Log.e("book", "Pickup time : $pickupTime")

        val userId = AppUtils(context!!).getUser()!!.id
        viewModel.estimateBooking(
            userId = userId,
            pickupAddress = pickUpLocation!!.id,
            destinationAddress = destinationLocation!!.id,
            category = categoryId,
            subCategory = subCategoryId,
            pickupRange = pickupRange,
            weight = weight,
            width = width,
            height = height,
            length = length,
            scheduleDate = if (deliveryType == "Normal") pickupDate.toDate(
                "yyyy-MM-dd",
                "dd/MM/yyyy"
            ) else "",
            scheduleTime = if (deliveryType == "Normal") choosedPickupTimeSlot else "",
            pickupType = deliveryType.replace(" ", ""),
            deliveryType = deliveryType.replace(" ", ""),
            videoRecording = videoRecordingOfPickupAndDelivery,
            pictureRecording = pictureOfDeliveryAndPickup,
            liveTemperature = liveTemperatureTracking,
            liveTracking = liveGPS,
        ).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {
                    viewUtils.showLoading(parentFragmentManager)
                }
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    if (result.value.status == "success") {
                        estimateBookingResponse = result.value
                        Log.e("book", "Delivery type is : $deliveryType")
                        when (deliveryType) {
                            "Normal" -> showNormalDeliveryDialog("edit")
                            else -> showExpressDeliveryDialog()
                        }
                    }
                }
                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
                }
            }
        }
    }

    private fun getAllCategories() {
        customViewModel.getAllCategories()
            .observe(viewLifecycleOwner) {
                when (it) {
                    is Status.Loading -> {
                        viewUtils.showLoading(parentFragmentManager)
                    }
                    is Status.Success -> {
                        viewUtils.stopShowingLoading()
                        categories.clear()
                        categories = it.value.result as ArrayList<AllCategoryModel.Result>
                        setAllCategories()
                    }
                    is Status.Failure -> {
                        viewUtils.stopShowingLoading()
                    }
                }
            }
    }

    private fun setAllCategories() {
        viewBinding.contentBook.acSubCategory.setText("")

        for (category in categories) {
            categoryNames.add(category.name)
        }

        val arrayAdapter = ArrayAdapter(context!!, R.layout.single_text_view, categoryNames)

        viewBinding.contentBook.acCategory.run {
            setAdapter(arrayAdapter)
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    categoryName = p0.toString()

                    for (category in categories) {
                        if (category.name == p0.toString()) {
                            categoryId = category.id
                            selectedCategory = category
                        }
                    }

                    getSubCategories(categoryId)
                }
            })
        }
    }

    private fun getServiceTypes() {
        customViewModel.serviceTypes().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {}
                is Status.Success -> {
                    serviceTypes.clear()
                    result.value.result.forEach { service ->
                        serviceTypes.add(service.name.lowercase())
                    }
                }
                is Status.Failure -> {}
            }
        }
    }

    private fun setServices() {
        cutOffTimeServices.clear()
        if (cutOffTimeResponse.normal)
            cutOffTimeServices.add("normal")
        if (cutOffTimeResponse.express)
            cutOffTimeServices.add("express")
        if (cutOffTimeResponse.super_fast)
            cutOffTimeServices.add("super fast")

        val finalServiceList = ArrayList<String>()
        for (service in serviceTypes) {
            for (cutOffService in cutOffTimeServices) {
                Log.e("book", "Service type : $service\n Cut off time service : $cutOffService")
                if (service == cutOffService)
                    finalServiceList.add(service)
            }
        }

        //finalServiceList.add("normal")
        viewBinding.contentBook.rvServiceType.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = BookingFormServiceTypeAdapter(
                data = finalServiceList,
                context = context,
                itemClick = object : RvItemClick {
                    override fun clickWithPosition(name: String, position: Int) {
                        Log.e("book", "Select service is ${name.replace(" ", "")}")
                        deliveryType = name
                        when (name) {
                            "Normal" ->
                                viewBinding.contentBook.acPickupRange.isEnabled = false
                            "Express" ->
                                viewBinding.contentBook.acPickupRange.isEnabled = true
                            "Suprefast" ->
                                viewBinding.contentBook.acPickupRange.isEnabled = true
                        }
                    }
                }
            )
        }
    }


    private fun getSubCategories(categoryId: String) {
        customViewModel.getAllSubCategories(categoryId)
            .observe(viewLifecycleOwner) {
                when (it) {
                    is Status.Loading -> {}
                    is Status.Success -> {
                        viewUtils.stopShowingLoading()
                        subCategories.clear()
                        subCategories = it.value.result as ArrayList<AllSubCategoryModel.Result>
                        setSubCategories()
                    }
                    is Status.Failure -> {
                        viewUtils.stopShowingLoading()
                    }
                }
            }
    }

    private fun setSubCategories() {
        subCategoryNames.clear()
        for (subCategory in subCategories) {
            subCategoryNames.add(subCategory.name)
        }

        val arrayAdapter = ArrayAdapter(context!!, R.layout.single_text_view, subCategoryNames)

        viewBinding.contentBook.acSubCategory.run {
            setAdapter(arrayAdapter)
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    subCategoryName = p0.toString()

                    for (subCategory in subCategories) {
                        if (subCategory.name == p0.toString()) {
                            subCategoryId = subCategory.id
                            selectedSubCategory = subCategory
                        }
                    }
                }
            })
        }
    }

    private fun setToolbar() {
        viewBinding.incToolbar.toolbar.title = "Booking"
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}