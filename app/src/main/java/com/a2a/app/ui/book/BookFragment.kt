package com.a2a.app.ui.book

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.a2a.app.*
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.Status
import com.a2a.app.data.model.*
import com.a2a.app.data.network.UserApi
import com.a2a.app.data.repository.UserRepository
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.FragmentBookBinding
import com.a2a.app.ui.address.AddressSelectionFragment
import com.a2a.app.ui.address.SaveAddressListener
import com.a2a.app.utils.AppUtils
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class BookFragment :
    BaseFragment<FragmentBookBinding, UserViewModel, UserRepository>(FragmentBookBinding::inflate) {

    private lateinit var customViewModel: CustomViewModel
    private lateinit var mainActivity: MainActivity
    private lateinit var categories: List<AllCategoryModel.Result>
    private lateinit var selectedCategory: AllCategoryModel.Result
    private lateinit var selectedSubCategory: AllSubCategoryModel.Result
    private var categoryNames = ArrayList<String>()
    private var subCategoryNames = ArrayList<String>()
    private lateinit var checkTimeSlotResponse: NormalTimeslotModel
    private lateinit var subCategories: List<AllSubCategoryModel.Result>
    private var categoryName = ""
    var categoryId = ""
    var subCategoryId = ""
    var deliveryType = ""
    private var subCategoryName = ""
    private var checkAvailability: CheckCutOffTimeModel? = null
    private var bookingDate = ""
    private var pickupDate = ""
    private var choosedPickupTimeSlot = ""
    private var deliveryDate = ""
    private var deliveryTimeSlot = ""
    private var bookingDateTime = ""
    private var pickUpLocation: AddressListModel.Result? = null
    private var destinationLocation: AddressListModel.Result? = null
    private var pickupRange = "0"
    private var weight = "0"
    private var width = "0"
    private var height = "0"
    private var length = "0"
    private var remarks = ""
    private var timeslot = ""
    private var price = ""
    private var pickupTimeslotPosition = 0
    private var finalPrice = ""
    private var videoRecordingOfPickupAndDelivery = ""
    private var liveGPS = ""
    private var liveTemperatureTracking = ""
    private var pictureOfDeliveryAndPickup = ""
    private var deliveryTimeslots = mutableListOf<String>()
    private val pickupTimeSlots = mutableListOf<String>()
    private lateinit var normalDialogConfirmButton: TextView
    private lateinit var estimateBookingResponse: EstimateBookingModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customViewModel = getCutomViewModel()
        //getArgument()
        setToolbar()
        getAllCategories()

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
                val addressSelection = AddressSelectionFragment()
                addressSelection.saveSetSaveListener(object : SaveAddressListener {
                    override fun onSaved(address: AddressListModel.Result) {
                        tvDestinationAddress.text = address.address
                        destinationLocation = address
                    }
                })
                addressSelection.show(parentFragmentManager, addressSelection.tag)
            }
            rgDeliveryType.setOnCheckedChangeListener { _, checkedId ->
                Log.d("book", "Delivery type is : $deliveryType")
                when (checkedId) {
                    R.id.rbNormal -> {
                        deliveryType = "Normal"
                        viewBinding.contentBook.acPickupRange.isEnabled = false
                    }
                    R.id.rbExpress -> {
                        deliveryType = "Express"
                        viewBinding.contentBook.acPickupRange.isEnabled = true
                    }
                    R.id.rbSuperFast -> {
                        deliveryType = "Superfast"
                        viewBinding.contentBook.acPickupRange.isEnabled = true
                    }
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

            //check any value is not null or empty
            if (pickUpLocation == null)
                toast("Select Pickup Location")
            else if (destinationLocation == null)
                toast("Select Destination Location")
            else if (categoryId.isEmpty())
                toast("Select Category")
            else if (subCategoryId.isEmpty())
                toast("Select SubCategory")
            else if (pickupRange.isEmpty())
                toast("Select Pickup Range")
            else if (weight.isEmpty())
                edtWight.error = "Enter Weight"
            /*else if (width.isEmpty())
                edtWidth.error = "Enter Width"
            else if (height.isEmpty())
                edtHeight.error = "Enter Height"
            else if (length.isEmpty())
                edtLength.error = "Enter Length"*/
            else {
                if (deliveryType.isEmpty())
                    toast("Please specify delivery type(Normal/Express/Super Fast)")
                else {
                    if (deliveryType == "Normal")
                        showNormalDeliveryDialog("show")
                    else
                        showExpressDeliveryDialog("show")
                }
            }
        }
    }

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
                        toast("First select pickup date")
                    } else {
                        dialog.dismiss()
                        checkAvailableTimeslots()
                    }
                }
                "Calculate Price" -> {
                    dialog.dismiss()
                    estimateBooking()
                }
                "Continue" -> {
                    dialog.dismiss()
                    moveToOrderConfirmationScreen()
                }
            }
        }

        when (whatToDo) {
            "show" -> {
                //empty data before showing dialog
                pickupDate = ""
                pickupTimeSlots.clear()
                choosedPickupTimeSlot = ""
                deliveryDate = ""
                deliveryTimeslots.clear()
                deliveryTimeSlot = ""

                dialog.show()
            }
            "pickup timeslot" -> {
                normalDialogConfirmButton.text = "Calculate Price"
                choosedPickupTimeSlot = pickupTimeSlots[0]
                //tvCutOffText.text = checkTimeSlotResponse.timeslot
                pickupDatePicker.setText(pickupDate)
                tvPickupTimeSlot.text = choosedPickupTimeSlot
                tvPickupTimeSlot.setupDropDown(pickupTimeSlots.toTypedArray(), { it }, {
                    choosedPickupTimeSlot = it
                    tvPickupTimeSlot.text = choosedPickupTimeSlot
                }, {
                    it.show()
                    hideSoftKeyboard()
                })
                Log.e("book", "pickup time slots : $pickupTimeSlots")
                dialog.show()
            }
            "edit" -> {
                lytDeliveryOption.visibility = View.VISIBLE
                estimateBookingResponse.estimations[0].run {
                    tvEstimateCost.text =
                        "Estimate Cost: \nRs.${pickup.estimated_price}/."
                    normalDialogConfirmButton.text = "Continue"

                    pickupDatePicker.setText(pickupDate)
                    tvPickupTimeSlot.text = choosedPickupTimeSlot
                    tvCutOffText.text = pickup.remarks
                    tvPickupTimeSlot.setupDropDown(pickupTimeSlots.toTypedArray(), { it }, {
                        choosedPickupTimeSlot = it
                        tvPickupTimeSlot.text = choosedPickupTimeSlot
                    }, {
                        it.show()
                        hideSoftKeyboard()
                    })

                    //delivery
                    deliveryDate = delivery.delivery_date.toDate(
                        "dd/MM/yyyy",
                        "yyyy-MM-dd"
                    )
                    deliveryDatePicker.setText(
                        deliveryDate
                    )
                    deliveryTimeslots.clear()
                    deliveryTimeslots = delivery.delivery_slot.split(",").toMutableList()
                    deliveryTimeSlot = deliveryTimeslots[0]
                    tvDeliveryTimeSlot.text = deliveryTimeSlot
                    tvDeliveryTimeSlot.setupDropDown(deliveryTimeslots.toTypedArray(), { it }, {
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

    private fun showExpressDeliveryDialog(whatToDo: String) {
        val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.dialog_express_delivery)
        val confirmButton = dialog.findViewById(R.id.btnScheduleConfirm) as TextView
        val timePicker = dialog.findViewById<TextView>(R.id.tvDeliveryTimeSlot)
        val deliveryDatePicker = dialog.findViewById<EditText>(R.id.edtDeliveryDate)
        val tvEstimateCost = dialog.findViewById<TextView>(R.id.tvEstimateCost)
        val tvCutOffText = dialog.findViewById<TextView>(R.id.tvCutOffText)

        deliveryDatePicker.setOnClickListener {
            showDatePickerDialog("delivery", deliveryDatePicker)
        }

        confirmButton.setOnClickListener {
            dialog.dismiss()
            when (confirmButton.text) {
                "Calculate Price" -> estimateBooking()
                else -> findNavController().navigate(R.id.action_bookFragment_to_orderConfirmationFragment)
            }
        }

        when (whatToDo) {
            "show" -> dialog.show()
            "edit" -> {
                confirmButton.text = "Continue"
                tvCutOffText.text = estimateBookingResponse.estimations[0].pickup.remarks
                tvEstimateCost.text =
                    "Estimate Cost: \nRs.${estimateBookingResponse.estimations[0].pickup.estimated_price}/."

                deliveryDatePicker.setText(
                    estimateBookingResponse.estimations[0].delivery.delivery_date.toDate(
                        "dd/MM/yy",
                        "yyyy-MM-dd"
                    )
                )
                deliveryTimeslots.clear()
                deliveryTimeslots =
                    estimateBookingResponse.estimations[0].delivery.delivery_slot.split(",")
                        .toMutableList()
                deliveryTimeSlot = deliveryTimeslots[0]
                timePicker.text = deliveryTimeSlot
                timePicker.setupDropDown(deliveryTimeslots.toTypedArray(), { it }, {
                    deliveryTimeSlot = it
                    timePicker.text = deliveryTimeSlot
                }, {
                    it.show()
                    hideSoftKeyboard()
                })
                dialog.show()
            }
        }
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

        if (type == "delivery") {
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

    private fun showTimePickerDialog(timePicker: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(context, { _, selectedHour, selectedMinute ->
            bookingDateTime = "$pickupDate $selectedHour:$selectedMinute"
            /*bookingDateTime = if (selectedHour > 12)
                "$bookingDate ${selectedHour - 12}:$selectedMinute pm"
            else if (selectedHour == 12)
                "$bookingDate 12:$selectedMinute pm"
            else {
                if (selectedHour != 0)
                    "$bookingDate $selectedHour:$selectedMinute am"
                else
                    "$bookingDate 12:$selectedMinute am"
            }*/
            if (checkTiming())
                timePicker.setText("$selectedHour:$selectedMinute")
            else {
                toast("Please select 6 hours later of the current time")
                showTimePickerDialog(timePicker)
            }
        }, hour, minute, false)

        timePickerDialog.setTitle("Select time")
        timePickerDialog.show()
    }

    private fun checkTiming(): Boolean {
        val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm")
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR_OF_DAY, 6)
        val currentDateTime = sdf.format(calendar.time)

        Log.e("time", "Booking Time : $bookingDateTime\nCurrent Time : $currentDateTime")

        try {
            val time1 = sdf.parse(bookingDateTime)
            val time2 = sdf.parse(currentDateTime.toString())

            Log.e("time", "Time difference is : ${time1!!.after(time2)}")

            return time1.after(time2)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    private fun checkAvailableTimeslots() {
        Log.e(
            "book", "check available timeslots -> \n" +
                    "schedule date : ${pickupDate.toDate("yyyy-MM-dd", "dd/MM/yyyy")}\n" +
                    " destination location : ${destinationLocation!!.city.id}\n" +
                    "pickup location : ${pickUpLocation!!.city.id}"
        )

        viewModel.normalTimeSlots(
            pickupDate.toDate("yyyy-MM-dd", "dd/MM/yyyy"),
            "61ae19f4630eda331431616d",
            "61baeddf53355a0009697687"
        ).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {
                    showLoading()
                }
                is Status.Success -> {
                    stopShowingLoading()
                    checkTimeSlotResponse = result.value
                    pickupTimeSlots.clear()
                    for (timeslot in result.value.timeslot)
                        pickupTimeSlots.add(timeslot.pickup.time)
                    showNormalDeliveryDialog("pickup timeslot")
                }
                is Status.Failure -> {
                    stopShowingLoading()
                }
            }
        }
    }

    private fun estimateBooking() {
        val userId = AppUtils(context!!).getUser()!!.id
        viewModel.estimateBooking(
            userId = userId,
            pickupAddress = "61baeddf53355a0009697687",
            destinationAddress = "61ae19f4630eda331431616d",
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
            pickupType = "Superfast",
            deliveryType = deliveryType,
            videoRecording = videoRecordingOfPickupAndDelivery,
            pictureRecording = pictureOfDeliveryAndPickup,
            liveTempreture = liveTemperatureTracking,
            liveTracking = liveGPS,
        ).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {
                    showLoading()
                }
                is Status.Success -> {
                    stopShowingLoading()
                    if (result.value.status == "success") {
                        estimateBookingResponse = result.value
                        price =
                            estimateBookingResponse.estimations[0].pickup.estimated_price.toString()
                        finalPrice =
                            estimateBookingResponse.estimations[0].pickup.estimated_price.toString()
                        timeslot = estimateBookingResponse.estimations[0].pickup.time
                        Log.e("book", "Delivery type is : $deliveryType")
                        when (deliveryType) {
                            "Normal" -> showNormalDeliveryDialog("edit")
                            else -> showExpressDeliveryDialog("edit")
                        }
                    }
                }
                is Status.Failure -> {
                    stopShowingLoading()
                }
            }
        }
    }

    private fun confirmBooking() {
        val sdfDate = SimpleDateFormat("MM/dd/yyyy")
        val sdfTime = SimpleDateFormat("HH:mm")
        val calendar = Calendar.getInstance()
        val currentDate = sdfDate.format(calendar.time)
        val currentTime = sdfTime.format(calendar.time)

        val userId = AppUtils(context!!).getUser()?.id
        viewModel.confirmBooking(
            userId = userId!!,
            pickupAddress = pickUpLocation!!.id,
            destinationAddress = destinationLocation!!.id,
            category = categoryId,
            subCategory = subCategoryId,
            pickupRange = pickupRange,
            weight = weight,
            width = width,
            height = height,
            length = length,
            pickupType = deliveryType,
            deliveryType = deliveryType,
            scheduleTime = when (deliveryType) {
                "Normal" -> {
                    choosedPickupTimeSlot
                }
                else -> {
                    currentTime
                }
            },
            scheduleDate = when (deliveryType) {
                "Normal" -> {
                    pickupDate.toDate("yyyy-MM-dd", "dd/MM/yyyy")
                }
                else -> {
                    currentDate
                }
            },
            price = price,
            finalPrice = finalPrice,
            timeslot = timeslot,
            videoRecording = videoRecordingOfPickupAndDelivery,
            pictureRecording = pictureOfDeliveryAndPickup,
            liveTemparature = liveTemperatureTracking,
            liveTracking = liveGPS
        ).observe(viewLifecycleOwner) {
            when (it) {
                is Status.Loading -> {
                    showLoading()
                }
                is Status.Success -> {
                    stopShowingLoading()
                    /*if (it.value.status == "success") {
                        val stringResponse =
                            Gson().toJson(it.value, ConfirmBookingModel::class.java)
                        val action =
                            BookFragmentDirections.actionBookFragmentToBookingConfrimFragment(
                                stringResponse
                            )
                        findNavController().navigate(action)
                    }*/
                }
                is Status.Failure -> {
                    stopShowingLoading()
                }
            }
        }
    }

    private fun checkCutOffTime() {
        val customViewModel = getCutomViewModel()
        val startCity = pickUpLocation!!.city.id
        val endCity = destinationLocation!!.city.id

        customViewModel.checkCutOffTime(startCity, endCity).observe(viewLifecycleOwner) {
            when (it) {
                is Status.Loading -> {
                    showLoading()
                }
                is Status.Success -> {
                    stopShowingLoading()
                    checkAvailability = it.value

                    /*if (checkAvailability?.result?.timeslot != null) {
                        if (checkAvailability!!.result.timeslot.contentEquals("Both")) {
                            slots.clear()
                            slots.add("Night")
                            slots.add("Afternoon")
                            estimateBooking()
                        } else {
                            slots.clear()
                            slots.add(checkAvailability!!.result.timeslot)
                            estimateBooking()
                        }
                    }
                    else {
                        showError("No available slots!, retry later")
                        stopShowingLoading()
                        findNavController().popBackStack()
                    }
                    estimateBooking()*/

                    deliveryTimeslots.clear()
                    deliveryTimeslots.add("Night")
                    deliveryTimeslots.add("Afternoon")
                    estimateBooking()
                }
                is Status.Failure -> {
                    stopShowingLoading()
                }
            }
        }
    }

    private fun getAllCategories() {
        customViewModel.getAllCategories()
        customViewModel.allCategories.observe(viewLifecycleOwner) {
            when (it) {
                is Status.Loading -> {
                    showLoading()
                }
                is Status.Success -> {
                    stopShowingLoading()
                    categories = it.value.result
                    setAllCategories()
                }
                is Status.Failure -> {
                    stopShowingLoading()
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

    private fun getSubCategories(categoryId: String) {
        customViewModel.getAllSubCategories(categoryId)
        customViewModel.allSubCategories.observe(viewLifecycleOwner) {
            when (it) {
                is Status.Loading -> {
                }
                is Status.Success -> {
                    stopShowingLoading()
                    subCategories = it.value.result
                    setSubCategories()
                }
                is Status.Failure -> {
                    stopShowingLoading()
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

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentBookBinding.inflate(inflater, container, false)

    override fun getViewModel() = UserViewModel::class.java

    override fun getFragmentRepository() =
        UserRepository(remoteDataSource.getBaseUrl().create(UserApi::class.java))

    override fun onResume() {
        super.onResume()
        //   mainActivity.hideToolbarAndBottomNavigation()
    }

}