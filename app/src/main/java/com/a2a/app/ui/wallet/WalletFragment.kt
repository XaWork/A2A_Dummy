package com.a2a.app.ui.wallet

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.network.UserApi
import com.a2a.app.data.repository.UserRepository
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.data.viewmodel.UserViewModel1
import com.a2a.app.databinding.FragmentWalletBinding
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.Event.Application.Execution.Thread.Frame
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class WalletFragment : Fragment(R.layout.fragment_wallet), PaymentResultListener {

    private lateinit var viewBinding: FragmentWalletBinding
    private val viewModel by viewModels<UserViewModel1>()
    private lateinit var mainActivity: MainActivity

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
        viewBinding = FragmentWalletBinding.bind(view)
        Checkout.preload(context)

        setToolbar()
        val userId = AppUtils(context!!).getUser()?.id
        if (userId != null) {
            getWalletData(userId)
            getWalletTransaction(userId)
        } else {
            viewUtils.showError("Something went wrong!")
            findNavController().popBackStack()
        }

        viewBinding.btnRecharge.setOnClickListener {
            showRechargeDialog()
        }
    }

    private fun showRechargeDialog() {
        val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.dialog_wallet_recharge)
        val edtAmount = dialog.findViewById<EditText>(R.id.edtAmount)
        val btnRecharge = dialog.findViewById<TextView>(R.id.btnRecharge)
        val btnCancel = dialog.findViewById<TextView>(R.id.btnCancel)

        btnRecharge.setOnClickListener {
            val amount = edtAmount.text.toString()
            if (amount.isEmpty())
                edtAmount.error = "Enter Amount"
            else
                rechargeInWallet(amount.toInt())
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun rechargeInWallet(amount: Int) {
        val co = Checkout()
        val user = appUtils.getUser()
        try {
            val options = JSONObject();
            options.put("name", "A2A")
            options.put("description", "A2A Wallet Recharge")
            options.put("currency", "INR")
            options.put("amount", (amount * 100).toFloat())

            val preFill = JSONObject();
            preFill.put("email", user?.email)
            preFill.put("contact", user?.mobile)
            options.put("prefill", preFill)
            co.open(mainActivity, options)
        } catch (e: Exception) {
            viewUtils.showShortToast("Error in payment:  + ${e.message}")
            e.printStackTrace()
        }
    }

    private fun getWalletData(userId: String) {
        viewModel.getWalletData(userId).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Status.Loading -> {
                    viewUtils.showLoading(parentFragmentManager)
                }
                is Status.Success -> {
                    viewUtils.stopShowingLoading()

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
                    viewUtils.stopShowingLoading()
                }
            }
        }
    }

    private fun getWalletTransaction(userId: String) {
        viewModel.getWalletTransaction(userId).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Status.Loading -> {
                }
                is Status.Success -> {
                    if (response.value.totalCount != 0) {
                        viewBinding.errorLayout.visibility = View.GONE
                        viewBinding.rvTransactions.run {
                            layoutManager = LinearLayoutManager(context)
                            adapter = TransactionAdapter(
                                response.value.result,
                                context,
                                object : RvItemClick {
                                    override fun clickWithPosition(name: String, position: Int) {}
                                })
                        }
                    } else {
                        viewBinding.errorLayout.visibility = View.VISIBLE
                    }
                }
                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
                }
            }
        }
    }

    private fun setToolbar() {
        viewBinding.incToolbar.toolbar.title = "Wallet"
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        viewUtils.showShortToast("Recharge successfully")
        val userId = appUtils.getUser()?.id
        getWalletData(userId!!)
        getWalletTransaction(userId)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        viewUtils.showShortToast("Payment failed, try again!")
    }

}