package com.a2a.app.ui.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.a2a.app.databinding.ContentAddressBinding
import com.a2a.app.databinding.FragmentAddressListBinding
import com.a2a.app.utils.AppUtils
import com.google.gson.Gson


class AddressListFragment : BaseFragment<FragmentAddressListBinding, UserViewModel, UserRepository>(
    FragmentAddressListBinding::inflate
) {

    private lateinit var contentAddress: ContentAddressBinding
    private var addressList: ArrayList<AddressListModel.Result> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentAddress = viewBinding.contentAddress

        setToolbar()

        viewBinding.contentAddress.tvAddNewAddress.setOnClickListener{
            findNavController().navigate(R.id.action_addressListFragment_to_addNewAddressFragment)
        }
    }

    private fun setToolbar() {
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getAddressList() {
        val userId = AppUtils(context!!).getUser()?.id
        viewModel.allAddress(userId!!)

        viewModel.addressList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Status.Loading -> {
                    showLoading()
                }
                is Status.Success -> {
                    stopShowingLoading()
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
                    stopShowingLoading()
                }
            }
        }
    }

    private fun setData() {
        contentAddress.rvAddressList.run {
            layoutManager = LinearLayoutManager(context)
            adapter = AddressAdapter(context, addressList, object : RvItemClick {
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
                    showLoading()
                }
                is Status.Success -> {
                    stopShowingLoading()
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
                    stopShowingLoading()
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

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddressListBinding.inflate(inflater, container, false)

    override fun getViewModel() = UserViewModel::class.java

    override fun getFragmentRepository() = UserRepository(
        remoteDataSource.getBaseUrl().create(
            UserApi::class.java
        )
    )
}