package com.a2a.app.ui.book

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.Status
import com.a2a.app.data.model.AddressListModel
import com.a2a.app.data.model.AllCategoryModel
import com.a2a.app.data.model.AllSubCategoryModel
import com.a2a.app.data.model.CheckCutOffTimeModel
import com.a2a.app.data.network.UserApi
import com.a2a.app.data.repository.UserRepository
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.FragmentBookBinding
import com.a2a.app.hideSoftKeyboard
import com.a2a.app.setupDropDown
import com.a2a.app.ui.address.AddressSelectionFragment
import com.a2a.app.ui.address.SaveAddressListener
import com.a2a.app.utils.AppUtils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BookFragment :
    BaseFragment<FragmentBookBinding, UserViewModel, UserRepository>(FragmentBookBinding::inflate) {

    private lateinit var customViewModel: CustomViewModel
    private lateinit var categories: List<AllCategoryModel.Result>
    private var categoryNames = ArrayList<String>()
    private var subCategoryNames = ArrayList<String>()
    private lateinit var subCategories: List<AllSubCategoryModel.Result>
    private var categoryName = ""
    var categoryId = ""
    var subCategoryId = ""
    var deliveryType = ""
    private var subCategoryName = ""
    var checkAvailability: CheckCutOffTimeModel? = null
    private var bookingDate = ""
    private var pickupDate = ""
    private var pickupTime = ""
    private var deliveryDate = ""
    private var deliveryTimeSlot = ""
    private var bookingDateTime = ""
    private var pickUpLocation: AddressListModel.Result? = null
    private var destinationLocation: AddressListModel.Result? = null
    private var pickupRange = ""
    private var weight = ""
    private var width = ""
    private var height = ""
    private var length = ""
    private var remarks = ""
    private val slots = mutableListOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customViewModel = getCutomViewModel()
        setToolbar()
        getAllCategories()

        with(viewBinding.contentBook) {
            val user = appUtils.getUser()
            tvPickupUserName.text = user?.fullName
            tvDestinationUserName.text = user?.fullName

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
                when (checkedId) {
                    R.id.rbNormal -> {
                        deliveryType = "Normal"
                    }
                    R.id.rbExpress -> {
                        deliveryType = "Express"
                    }
                    R.id.rbSuperFast -> {
                        deliveryType = "Superfast"
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
            pickupRange = edtPickupRange.text.toString().trim()
            weight = edtWight.text.toString().trim()
            width = edtWidth.text.toString().trim()
            height = edtHeight.text.toString().trim()
            length = edtLength.text.toString().trim()

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
            /*else if (pickupRange.isEmpty())
                edtPickupRange.error = "Enter Pickup Range"
            else if (weight.isEmpty())
                edtWight.error = "Enter Weight"
            else if (width.isEmpty())
                edtWidth.error = "Enter Width"
            else if (height.isEmpty())
                edtHeight.error = "Enter Height"
            else if (length.isEmpty())
                edtLength.error = "Enter Length"*/
            else {
                if (deliveryType.isEmpty())
                    toast("Please specify delivery type(Normal/Express/Super Fast")
                else
                    checkCutOffTime()
            }
        }
    }

    private fun showNormalDeliveryDialog(estimateCost: Int) {
        val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.dialog_normal_delivery)
        val confirmButton = dialog.findViewById(R.id.btnInstantConfirm) as TextView
        val pickupTimePicker = dialog.findViewById<EditText>(R.id.edtPickupTime)
        val pickupDatePicker = dialog.findViewById<EditText>(R.id.edtPickupDate)
        val deliveryDatePicker = dialog.findViewById<EditText>(R.id.edtDeliveryDate)
        val deliveryTimeSlot = dialog.findViewById<TextView>(R.id.tvDeliveryTimeSlot)
        val tvEstimateCost = dialog.findViewById<TextView>(R.id.tvEstimateCost)

        tvEstimateCost.text = "Estimate Cost: \nRs.$estimateCost/."
        deliveryTimeSlot.setupDropDown(slots.toTypedArray(), { it }, {
            deliveryTimeSlot.text = it
        }, {
            it.show()
            hideSoftKeyboard()
        })

        pickupDatePicker.setOnClickListener {
            showDatePickerDialog("pickup", pickupDatePicker)
        }
        pickupTimePicker.setOnClickListener {
            showTimePickerDialog(pickupTimePicker)
        }
        deliveryDatePicker.setOnClickListener {
            showDatePickerDialog("delivery", deliveryDatePicker)
        }

        confirmButton.setOnClickListener {
            dialog.dismiss()
            confirmInstantBooking()
        }
        dialog.show()
    }

    private fun confirmInstantBooking() {
        findNavController().navigate(R.id.action_bookFragment_to_bookingConfrimFragment)
    }

    private fun showExpressDeliveryDialog(estimateCost: Int) {
        val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.dialog_express_delivery)
        val confirmButton = dialog.findViewById(R.id.btnScheduleConfirm) as TextView
        val timePicker = dialog.findViewById<TextView>(R.id.tvDeliveryTimeSlot)
        val deliveryDatePicker = dialog.findViewById<EditText>(R.id.edtDeliveryDate)
        val tvEstimateCost = dialog.findViewById<TextView>(R.id.tvEstimateCost)

        tvEstimateCost.text = "Estimate Cost: \nRs.$estimateCost/."

        timePicker.setupDropDown(slots.toTypedArray(), { it }, {
            timePicker.text = it
        }, {
            it.show()
            hideSoftKeyboard()
        })

        deliveryDatePicker.setOnClickListener {
            showDatePickerDialog("delivery", deliveryDatePicker)
        }

        confirmButton.setOnClickListener {
            dialog.dismiss()
            confirmInstantBooking()

        }
        dialog.show()
    }

    private fun estimateBooking() {
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
            pickupType = deliveryType,
            deliveryType = deliveryType
        ).observe(viewLifecycleOwner) {
            when (it) {
                is Status.Loading -> {
                    showLoading()
                }
                is Status.Success -> {
                    stopShowingLoading()
                    /*toast("Booking confirm")
                    val action = BookFragmentDirections.actionBookFragmentToBookingConfrimFragment()
                    findNavController().navigate(action)*/
                    if (deliveryType == "Normal")
                        showNormalDeliveryDialog(
                            estimateCost = it.value.estimated_price
                        )
                    else
                        showExpressDeliveryDialog(
                            estimateCost = it.value.estimated_price
                        )
                }
                is Status.Failure -> {
                    stopShowingLoading()
                }
            }
        }
    }

    private fun showDatePickerDialog(type: String, dateTimePicker: EditText) {
        val calendar: Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context!!,
            { _, year1, month1, day1 ->
                when (type) {
                    "pickup" -> pickupDate = "${month1 + 1}/$day1/$year1"
                    "delivery" -> deliveryDate = "${month1 + 1}/$day1/$year1"
                }
                dateTimePicker.setText("${month1 + 1}/$day1/$year1")
            }, year, month, dayOfMonth
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
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

                    slots.clear()
                    slots.add("Night")
                    slots.add("Afternoon")
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

}