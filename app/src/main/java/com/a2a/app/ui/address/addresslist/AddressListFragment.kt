package com.a2a.app.ui.address.addresslist

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
import com.a2a.app.R
import com.a2a.app.common.Status
import com.a2a.app.data.model.AddressListModel
import com.a2a.app.databinding.FragmentAddressListBinding
import com.a2a.app.ui.address.AddressViewModel
import com.a2a.app.ui.address.SingleAddress
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

    private lateinit var viewBinding: FragmentAddressListBinding
    private val addressViewModel: AddressViewModel by viewModels()

    @Inject
    lateinit var appUtils: AppUtils

    @Inject
    lateinit var viewUtils: ViewUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentAddressListBinding.bind(view)

        addressViewModel.allAddresses.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> viewUtils.showLoading(parentFragmentManager)
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    viewBinding.addressListComposeView.apply {
                        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                        setContent { AddressListScreen(result.value.result) }
                    }
                }
                is Status.Failure -> viewUtils.stopShowingLoading()
            }
        }
    }

    private fun moveToAddAddressScreen() {
        findNavController().navigate(R.id.action_addressListFragment_to_addNewAddressFragment)
    }

    private fun moveToEditAddressScreen(address: AddressListModel.Result) {
        val action =
            AddressListFragmentDirections.actionAddressListFragmentToAddNewAddressFragment(
                Gson().toJson(address, AddressListModel.Result::class.java)
            )
        findNavController().navigate(action)
    }

    private fun deleteAddress(address: AddressListModel.Result) {
        addressViewModel.deleteAddress(userId = appUtils.getUser()!!.id, addressId = address.id)
    }


    override fun onResume() {
        super.onResume()
        val userId = appUtils.getUser()!!.id
        addressViewModel.getAllAddresses(userId = userId)
    }

    //-------------------------------- Compose UI --------------------------------------------------

    @Composable
    fun AddressListScreen(
        addresses: List<AddressListModel.Result>
    ) {
        Scaffold(topBar = {
            A2ATopAppBar(title = "Addresses") {
                findNavController().popBackStack()
            }
        }, content = {
            ContentAddressList(addresses)
        })
    }

    @Composable
    private fun ContentAddressList(allAddresses: List<AddressListModel.Result>) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.MainBgColor)
                .padding(ScreenPadding),
            contentAlignment = Alignment.Center
        ) {
            //when address list is empty
            if (allAddresses.isEmpty()) {
                ShowNoAddressFound(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
            } else {
                ShowAddressList(
                    allAddresses,
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
    private fun ShowAddressList(
        allAddresses: List<AddressListModel.Result>,
        modifier: Modifier = Modifier
    ) {
        LazyColumn(
            modifier = modifier
        ) {
            items(items = allAddresses, key = { address -> address.id }) { address ->
                SingleAddress(address = address, onClick = { whatToDo ->
                    when (whatToDo) {
                        "delete" -> deleteAddress(address)
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