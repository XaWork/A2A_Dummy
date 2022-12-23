package com.a2a.app.ui.wallet

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.WalletDataModel
import com.a2a.app.data.model.WalletTransactionModel
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.FragmentWalletBinding
import com.a2a.app.ui.components.A2AButton
import com.a2a.app.ui.components.A2ATopAppBar
import com.a2a.app.ui.theme.*
import com.a2a.app.ui.wallet.component.TransactionItem
import com.a2a.app.ui.wallet.component.WalletRechargeDialog
import com.a2a.app.utils.AppUtils
import com.a2a.app.utils.ViewUtils
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class WalletFragment : Fragment(R.layout.fragment_wallet), PaymentResultListener {

    private lateinit var viewBinding: FragmentWalletBinding
    private val viewModel by viewModels<UserViewModel>()
    private lateinit var mainActivity: MainActivity
    private lateinit var walletTransactions: WalletTransactionModel
    private lateinit var walletData: WalletDataModel

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
                    walletData = response.value
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
                    walletTransactions = response.value
                    setData()
                }
                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
                }
            }
        }
    }

    private fun setData() {
        viewBinding.walletComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                A2ATheme {
                    WalletScreen()
                }
            }
        }
    }

    /*private fun setToolbar() {
        viewBinding.incToolbar.toolbar.title = "Wallet"
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }*/

    override fun onPaymentSuccess(p0: String?) {
        viewUtils.showShortToast("Recharge successfully")
        val userId = appUtils.getUser()?.id
        getWalletData(userId!!)
        getWalletTransaction(userId)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        viewUtils.showShortToast("Payment failed, try again!")
    }

    override fun onResume() {
        super.onResume()
        val userId = AppUtils(context!!).getUser()?.id
        if (userId != null) {
            getWalletData(userId)
            getWalletTransaction(userId)
        } else {
            viewUtils.showError("Something went wrong!")
            findNavController().popBackStack()
        }
    }


    //------------------------------------- Compose UI ---------------------------------------------

    @Composable
    fun WalletScreen() {
        Scaffold(topBar = {
            A2ATopAppBar(title = stringResource(id = R.string.wallet)) {
                findNavController().popBackStack()
            }
        }, content = {
            ContentWallet()
        })
    }

    @Composable
    fun ContentWallet() {
        var showDialog by remember {
            mutableStateOf(false)
        }

        if (showDialog)
            WalletRechargeDialog(
                showDialog = { showDialog = it },
                onRechargeConfirm = { rechargeInWallet(it) })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.MainBgColor)
                .padding(ScreenPadding)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardElevation
            ) {
                Column {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colors.primaryVariant)
                            .padding(ScreenPadding),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.total_balance).uppercase(),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))
                        Text(
                            text = if (walletData.plan != null)
                                walletData.walletBalance
                            else
                                stringResource(id = R.string.error),
                            fontSize = 24.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))
                        Text(
                            text = stringResource(id = R.string.recharge),
                            fontSize = 24.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .background(color = MaterialTheme.colors.primary)
                                .clip(RoundedCornerShape(100.dp))
                                .padding(horizontal = HighPadding, vertical = MediumPadding)
                                .clickable { showDialog = true }
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colors.primary)
                            .padding(ScreenPadding),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.total_points_earned).uppercase(),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.wallet_money),
                                contentDescription = "Wallet money",
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(SpaceBetweenViewsAndSubViews))
                            Text(
                                text = if (walletData.plan != null) walletData.customerPoint.toString() else stringResource(
                                    id = R.string.error
                                ),
                                fontSize = 24.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(SpaceBetweenViews))

            if (walletTransactions.totalCount != 0) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)) {
                    items(walletTransactions.result) { walletTransaction ->
                        TransactionItem(walletTransaction)
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun WalletScreenPreview() {
        WalletScreen()
    }
}