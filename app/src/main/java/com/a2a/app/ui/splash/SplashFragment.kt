package com.a2a.app.ui.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.databinding.FragmentSplashBinding
import com.a2a.app.utils.AppUtils


class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var viewBinding: ViewBinding
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentSplashBinding.bind(view)
        mainActivity.hideToolbarAndBottomNavigation()

        Handler(Looper.getMainLooper()).postDelayed({
            if (AppUtils(context!!).getUser() != null) {
                Log.d("splash", "go to home")
                findNavController().navigate(R.id.action_global_homeFragment)
            }
            else {
                Log.d("splash", "go to on boarding")
                findNavController().navigate(R.id.action_global_onBoardingFragment)
            }
        }, 2000)
    }
}