package com.a2a.app.ui.address

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.R
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.AddressListModel
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.ContentAddressBinding
import com.a2a.app.databinding.FragmentAddressListBinding
import com.a2a.app.ui.components.A2AButton
import com.a2a.app.ui.components.A2ATopAppBar
import com.a2a.app.ui.theme.MainBgColor
import com.a2a.app.ui.theme.ScreenPadding
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddressListFragment : Fragment(R.layout.fragment_address_list) {

    private lateinit var contentAddress: ContentAddressBinding
    private lateinit var viewBinding: FragmentAddressListBinding
    private lateinit var addresses: MutableList<AddressListModel.Result>

    @Inject
    lateinit var appUtils: AppUtils

    @Inject
    lateinit var viewUtils: ViewUtils

    private val viewModel by viewModels<UserViewModel>()
    private var addressList: ArrayList<AddressListModel.Result> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentAddressListBinding.bind(view)

        /* contentAddress = viewBinding.contentAddress

         setToolbar()

         viewBinding.contentAddress.tvAddNewAddress.setOnClickListener {
             moveToAddAddressScreen()
         }*/
    }

    private fun moveToAddAddressScreen() {
        findNavController().navigate(R.id.action_addressListFragment_to_addNewAddressFragment)
    }

    /* private fun setToolbar() {
         viewBinding.incToolbar.toolbar.title = "Address List"
         viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
             findNavController().popBackStack()
         }
     }*/

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

                    addresses = addressList.toMutableStateList()

                    viewBinding.addressListComposeView.apply {
                        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                        setContent { AddressListScreen() }
                    }

                    /*with(contentAddress) {
                        if (addressList.isNotEmpty()) {
                            noAddressFound.visibility = View.GONE
                            rvAddressList.visibility = View.VISIBLE
                            setData()
                        } else {
                            noAddressFound.visibility = View.VISIBLE
                            rvAddressList.visibility = View.GONE
                        }
                    }*/
                }
                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
                }
            }
        }
    }

 /*   private fun setData() {
        contentAddress.rvAddressList.run {
            layoutManager = LinearLayoutManager(context)
            adapter = AddressAdapter(context, addressList, "profile", object : RvItemClick {
                override fun clickWithPosition(name: String, position: Int) {
                    when (name) {
                        "delete" -> {
                            // deleteAddress(addressList[position].id)
                        }
                        "edit" -> {
                            //moveToEditAddressScreen(position)
                        }
                    }
                }
            })
        }
    }*/

    private fun moveToEditAddressScreen(position: Int = 0, address: AddressListModel.Result) {

        val action =
            AddressListFragmentDirections.actionAddressListFragmentToAddNewAddressFragment(
                Gson().toJson(address, AddressListModel.Result::class.java)
            )
        findNavController().navigate(action)
    }

    private fun deleteAddress(addressId: String, address: AddressListModel.Result) {
        viewModel.deleteAddress(addressId).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Status.Loading -> {
                    viewUtils.showLoading(parentFragmentManager)
                }
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    addresses.remove(address)

                    /*with(contentAddress) {
                        removeFromAddressList(addressId);
                        if (addressList.isNotEmpty()) {
                            noAddressFound.visibility = View.GONE
                            rvAddressList.visibility = View.VISIBLE
                            setData()
                        } else {
                            noAddressFound.visibility = View.VISIBLE
                            rvAddressList.visibility = View.GONE
                        }
                    }*/
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

    //-------------------------------- Compose UI --------------------------------------------------

    @Composable
    fun AddressListScreen() {
        Scaffold(topBar = {
            A2ATopAppBar(title = "Addresses") {
                findNavController().popBackStack()
            }
        }, content = {
            ContentAddressList()
        })
    }

    @Composable
    private fun ContentAddressList() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.MainBgColor)
                .padding(ScreenPadding),
            contentAlignment = Alignment.Center
        ) {
            //when address list is empty
            if (addresses.isEmpty()) {
                ShowNoAddressFound(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
            } else {
                ShowAddressList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                )
            }

            A2AButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(50.dp)),
                title = "Add New Address",
                allCaps = false
            ) {
                moveToAddAddressScreen()
            }
        }
    }

    @Composable
    private fun ShowAddressList(modifier: Modifier = Modifier) {
        LazyColumn(
            modifier = modifier
        ) {
            items(items = addresses, key = { address -> address.id }) { address ->
                SingleAddress(address = address, onClick = { whatToDo ->
                    when (whatToDo) {
                        "delete" -> deleteAddress(address.id, address)
                        "edit" -> moveToEditAddressScreen(address = address)
                    }
                })
            }
        }
    }

    @Composable
    private fun ShowNoAddressFound(modifier: Modifier) {
        Text(
            text = "No Address Found",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = modifier,
            textAlign = TextAlign.Center
        )
    }

}