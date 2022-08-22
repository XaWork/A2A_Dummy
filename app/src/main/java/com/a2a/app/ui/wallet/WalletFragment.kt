package com.a2a.app.ui.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.network.UserApi
import com.a2a.app.data.repository.UserRepository
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.FragmentWalletBinding
import com.a2a.app.utils.AppUtils

class WalletFragment :
    BaseFragment<FragmentWalletBinding, UserViewModel, UserRepository>(FragmentWalletBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
        val userId = AppUtils(context!!).getUser()?.id
        if (userId != null) {
            getWalletData(userId)
            getWalletTransaction(userId)
        } else {
            showError("Something went wrong!")
            findNavController().popBackStack()
        }
    }

    private fun getWalletData(userId: String) {
        viewModel.getWalletData(userId)
        viewModel.walletData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Status.Loading -> {
                    showLoading()
                }
                is Status.Success -> {
                    stopShowingLoading()

                    if (response.value.plan != null) {
                        viewBinding.run {
                            walletMoney.text = response.value.walletBalance
                            walletPoint.text = response.value.customerPoint.toString()
                        }
                    } else {
                        viewBinding.run {
                            walletMoney.text = "Error"
                            walletPoint.text = "Error"
                        }
                    }
                }
                is Status.Failure -> {
                    stopShowingLoading()
                }
            }
        }
    }

    private fun getWalletTransaction(userId: String) {
        viewModel.getWalletTransactions(userId)
        viewModel.walletTransaction.observe(viewLifecycleOwner){response ->
            when(response){
                is Status.Loading -> {
                }
                is Status.Success -> {
                    if (response.value.totalCount != 0) {
                        viewBinding.errorLayout.visibility = View.GONE
                        viewBinding.rvTransactions.run{
                            layoutManager = LinearLayoutManager(context)
                            adapter = TransactionAdapter(response.value.result, context, object:RvItemClick{
                                override fun clickWithPosition(name: String, position: Int) {}
                            })
                        }
                    } else {
                        viewBinding.errorLayout.visibility = View.VISIBLE
                    }
                }
                is Status.Failure -> {
                    stopShowingLoading()
                }
            }
        }
    }

    private fun setToolbar() {
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentWalletBinding.inflate(inflater, container, false)

    override fun getViewModel() = UserViewModel::class.java

    override fun getFragmentRepository() = UserRepository(
        remoteDataSource.getBaseUrl().create(
            UserApi::class.java
        )
    )
}