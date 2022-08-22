package com.a2a.app.ui.address

import android.app.Activity
import android.app.AppComponentFactory
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a2a.app.R
import com.a2a.app.common.OnSelectListener
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.AddressListModel
import com.a2a.app.data.network.CustomApi
import com.a2a.app.data.network.UserApi
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.repository.UserRepository
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.data.viewmodelfactory.CustomViewModelFactory
import com.a2a.app.data.viewmodelfactory.UserViewModelFactory
import com.a2a.app.databinding.LocationSelectionFragmentBinding
import com.a2a.app.services.RemoteDataSource
import com.a2a.app.ui.RoundedBottomSheetDialogFragment
import com.a2a.app.utils.AppUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddressSelectionFragment(
) : RoundedBottomSheetDialogFragment(), OnSelectListener {

    lateinit var userId:String
    lateinit var viewModel:UserViewModel
    private lateinit var addressListAdapter: AddressAdapter
    private lateinit var bindingModel: LocationSelectionFragmentBinding
    private var addressList: ArrayList<AddressListModel.Result> = ArrayList()
    lateinit var saveListener: SaveAddressListener
    private lateinit var address: AddressListModel.Result

    fun saveSetSaveListener(saveAddressListener: SaveAddressListener){
        saveListener = saveAddressListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingModel = LocationSelectionFragmentBinding.inflate(inflater, container, false)
        return bindingModel.root
    }

    override fun onAttach(context: Context) {
        val userRepository =
            UserRepository(RemoteDataSource().getBaseUrl().create(UserApi::class.java))

        viewModel =  ViewModelProvider(
            this,
            UserViewModelFactory(userRepository)
        )[UserViewModel::class.java]
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog as BottomSheetDialog
        val behavior: BottomSheetBehavior<*> = dialog.behavior
        behavior.state = BottomSheetBehavior.STATE_EXPANDED;
        behavior.peekHeight = 0;
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addressListAdapter = AddressAdapter(context!!, addressList,"sheet", object:RvItemClick{
            override fun clickWithPosition(name: String, position: Int) {
                when(name){
                    "select" -> {
                        dismiss()
                         address = addressList[position]
                        saveListener.onSaved(address)
                    }
                }
            }
        })

        bindingModel.addressList.run {
            adapter = addressListAdapter
            layoutManager = LinearLayoutManager(context!!)
        }

        userId = AppUtils(context!!).getUser()!!.id
        viewModel.allAddress(userId)
            viewModel.addressList.observe(this) {
                when (it) {
                    is Status.Success -> {
                        bindingModel.loader.visibility = View.GONE
                        if (it.value.result.isNotEmpty()) {
                            addressList.clear()
                            addressList.addAll(it.value.result)

                            addressListAdapter.notifyDataSetChanged()
                            bindingModel.emptyHint.visibility = View.GONE

                        } else {
                            bindingModel.emptyHint.visibility = View.VISIBLE
                        }
                    }
                    Status.Loading -> {
                        bindingModel.loader.visibility = View.VISIBLE
                    }
                    else -> {
                        bindingModel.loader.visibility = View.VISIBLE
                        dismiss()
                        Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show()
                    }
                }
            }

        bindingModel.newAddress.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.action_global_addNewAddressFragment)
        }
    }

    override fun onSelectItem(position: Int, type: String) {
       // saveListener.onSaved()
        dismiss()
    }
}

interface SaveAddressListener{
    fun onSaved(address: AddressListModel.Result)
}