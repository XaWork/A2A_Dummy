package com.a2a.app.ui.book

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.Status
import com.a2a.app.data.model.AddressListModel
import com.a2a.app.data.model.AllCategoryModel
import com.a2a.app.data.model.AllSubCategoryModel
import com.a2a.app.data.network.CustomApi
import com.a2a.app.data.network.UserApi
import com.a2a.app.data.repository.UserRepository
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.FragmentBookBinding
import com.a2a.app.ui.address.AddressSelectionFragment
import com.a2a.app.ui.address.SaveAddressListener
import okhttp3.internal.assertThreadDoesntHoldLock
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
    private var bookingDate = ""
    private var bookingTime = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customViewModel = getCutomViewModel()
        setToolbar()
        getAllCategories()

        with(viewBinding.contentBook) {
            val user = appUtils.getUser()
            tvPickupUserName.text = user?.fullName
            tvDestinationUserName.text = user?.fullName

            pickLocation.setOnClickListener {
                val addressSelection = AddressSelectionFragment()
                addressSelection.saveSetSaveListener(object : SaveAddressListener {
                    override fun onSaved(address: AddressListModel.Result) {
                        tvPickupAddress.text = address.address
                    }
                })
                addressSelection.show(parentFragmentManager, addressSelection.tag)
            }
            destinationLocation.setOnClickListener {
                val addressSelection = AddressSelectionFragment()
                addressSelection.saveSetSaveListener(object : SaveAddressListener {
                    override fun onSaved(address: AddressListModel.Result) {
                        tvDestinationAddress.text = address.address
                    }
                })
                addressSelection.show(parentFragmentManager, addressSelection.tag)
            }
            /*rgDeliveryType.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbNormal -> {
                        deliveryType = "normal"
                    }
                    R.id.rbExpress -> {
                        deliveryType = "express"
                    }
                    R.id.rbSuperFast -> {
                        deliveryType = "super fast"
                    }
                }
            }*/
        }
        viewBinding.btnBookNow.setOnClickListener {
            when(deliveryType){
                "normal" -> showScheduleBookingConfirmDialog()
                else -> showInstantBookingConfirmDialog()
            }
        }
    }

    private fun showInstantBookingConfirmDialog() {
        val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.dialog_instant_booking)
        val confirmButton = dialog.findViewById(R.id.btnInstantConfirm) as TextView
        confirmButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun showScheduleBookingConfirmDialog() {
        val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.dialog_schedule_booking)
        val confirmButton = dialog.findViewById(R.id.btnScheduleConfirm) as TextView
        val datePicker = dialog.findViewById<EditText>(R.id.edtDate)
        val timePicker = dialog.findViewById<EditText>(R.id.edtTime)

        datePicker.setOnClickListener {
            showDatePickerDialog()
            datePicker.setText(bookingDate)
        }
        timePicker.setOnClickListener {
            showTimePickerDialog()
            timePicker.setText(bookingTime)
        }

        confirmButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun showDatePickerDialog() {
        val calendar: Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context!!,
            { _, year1, month1, day1 ->
                bookingDate = "$day1-${month1 + 1}-$year1"
            }, year, month, dayOfMonth
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(context, { _, selectedHour, selectedMinute ->
            bookingTime = "$selectedHour : $selectedMinute"
        }, hour, minute, false)

        timePickerDialog.setTitle("Select time")
        timePickerDialog.show()
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