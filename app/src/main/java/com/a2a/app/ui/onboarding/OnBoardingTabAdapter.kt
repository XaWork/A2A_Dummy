package com.a2a.app.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.a2a.app.ui.onboarding.signin.SignInFragment
import com.a2a.app.ui.onboarding.signup.SignUpFragment

class OnBoardingTabAdapter(fragmentManger: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManger, lifecycle) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return SignInFragment()
        }
        return SignUpFragment()
    }
}