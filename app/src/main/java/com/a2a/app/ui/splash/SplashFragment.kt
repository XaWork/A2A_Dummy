package com.a2a.app.ui.splash

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.common.Status
import com.a2a.app.data.viewmodel.SettingViewModel
import com.a2a.app.databinding.FragmentSplashBinding
import com.a2a.app.ui.common.ProgressDialog
import com.a2a.app.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var viewBinding: FragmentSplashBinding
    private lateinit var mainActivity: MainActivity

    @Inject
    lateinit var appUtils: AppUtils

    @Inject
    lateinit var dialog: ProgressDialog

    val viewModel by viewModels<SettingViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentSplashBinding.bind(view)
        mainActivity.hideToolbarAndBottomNavigation()

        viewBinding.splashComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                SplashScreen()
            }
        }

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