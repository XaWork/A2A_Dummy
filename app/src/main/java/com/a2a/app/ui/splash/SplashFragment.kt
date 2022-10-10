package com.a2a.app.ui.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.common.BaseFragment1
import com.a2a.app.common.Status
import com.a2a.app.data.viewmodel.SettingViewModel
import com.a2a.app.databinding.FragmentSplashBinding
import com.a2a.app.ui.common.ProgressDialog
import com.a2a.app.ui.common.ProgressDialogFragment
import com.a2a.app.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var viewBinding: ViewBinding
    private lateinit var mainActivity: MainActivity

    @Inject
    lateinit var appUtils: AppUtils
    @Inject
    lateinit var dialog: ProgressDialog

    val viewModel by viewModels<SettingViewModel>()
    private var token: String? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentSplashBinding.bind(view)
        mainActivity.hideToolbarAndBottomNavigation()

        getSettings()
    }

    private fun getSettings() {
        viewModel.getSettings().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Status.Loading -> {}
                is Status.Success -> {
                    appUtils.clearHomePage()
                    appUtils.saveSettings(result.value)
                    moveToNext()
                }
                is Status.Failure -> {}
            }
        }
    }

    private fun moveToNext() {
        if (appUtils.getUser() != null) {
            Log.d("splash", "go to home")
            findNavController().navigate(R.id.action_global_homeFragment)
        } else {
            Log.d("splash", "go to on boarding")
            findNavController().navigate(R.id.action_global_onBoardingFragment)
        }
    }
}