package com.a2a.app.ui.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.R
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.AddressListModel
import com.a2a.app.data.network.UserApi
import com.a2a.app.data.repository.UserRepository
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.data.viewmodel.UserViewModel1
import com.a2a.app.databinding.ContentAddressBinding
import com.a2a.app.databinding.FragmentAddressListBinding
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddressListFragment : Fragment(R.layout.fragment_address_list) {

    private lateinit var contentAddress: ContentAddressBinding
    private lateinit var viewBinding: FragmentAddressListBinding

    @Inject
    lateinit var appUtils: AppUtils

    @Inject
    lateinit var viewUtils: ViewUtils

    private val viewModel by viewModels<UserViewModel1>()
    private var addressList: ArrayList<AddressListModel.Result> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentAddressListBinding.bind(view)

        contentAddress = viewBinding.contentAddress

        setToolbar()

        viewBinding.contentAddress.tvAddNewAddress.setOnClickListener {
            findNavController().navigate(R.id.action_addressListFragment_to_addNewAddressFragment)
        }
    }

    private fun setToolbar() {
        viewBinding.incToolbar.toolbar.title = "Address List"
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getAddressList() {
        val userId = appUtils.getUser()!!.id

        viewModel.addressList(userId).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Status.Loading -> {
                    viewUtils.showLoading(parentFragmentManager)
                }
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    addressList.clear()
                    addressList.addAll(response.value.result)

                    with(contentAddress) {
                        if (addressList.isNotEmpty()) {
                            noAddressFound.visibility = View.GONE
                            rvAddressList.visibility = View.VISIBLE
                            setData()
                        } else {
                            noAddressFound.visibility = View.VISIBLE
                            rvAddressList.visibility = View.GONE
                        }
                    }
                }
                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
                }
            }
        }
    }

    private fun setData() {
        contentAddress.rvAddressList.run {
            layoutManager = LinearLayoutManager(context)
            adapter = AddressAdapter(context, addressList, "profile", object : RvItemClick {
                override fun clickWithPosition(name: String, position: Int) {
                    when (name) {
                        "delete" -> {
                            deleteAddress(position)
                        }
                        "edit" -> {
                            val address = addressList[position]

                            val action =
                                AddressListFragmentDirections.actionAddressListFragmentToAddNewAddressFragment(
                                    Gson().toJson(address, AddressListModel.Result::class.java)
                                )
                            findNavController().navigate(action)
                        }
                    }
                }
            })
        }
    }

    private fun deleteAddress(position: Int) {
        val userId = AppUtils(context!!).getUser()?.id
        val addressId = addressList[position].id

        viewModel.deleteAddress(userId!!, addressId).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Status.Loading -> {
                    viewUtils.showLoading(parentFragmentManager)
                }
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    with(contentAddress) {
                        removeFromAddressList(addressId);
                        if (addressList.isNotEmpty()) {
                            noAddressFound.visibility = View.GONE
                            rvAddressList.visibility = View.VISIBLE
                            setData()
                        } else {
                            noAddressFound.visibility = View.VISIBLE
                            rvAddressList.visibility = View.GONE
                        }
                    }
                }
                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
                }
            }
        }
    }

    private fun removeFromAddressList(addressId: String) {

        val iter: MutableListIterator<AddressListModel.Result> = addressList.listIterator()
        while (iter.hasNext()) {
            if (iter.next().id == addressId) {
                iter.remove()
            }
        }

    }


    override fun onResume() {
        super.onResume()
        getAddressList()
    }

}