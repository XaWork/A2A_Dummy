package com.a2a.app.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.a2a.app.R
import com.a2a.app.databinding.FragmentOnBoardingBinding
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingFragment : Fragment(R.layout.fragment_on_boarding) {

    private lateinit var viewBinding: FragmentOnBoardingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentOnBoardingBinding.bind(view)

        setTabs()
    }

    private fun setTabs() {
        with(viewBinding) {
            val tabs = arrayOf("Login", "SignUp")

            val adapter = OnBoardingTabAdapter(
                fragmentManger = parentFragmentManager,
                lifecycle = lifecycle
            )
            viewPager2.adapter = adapter

            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = tabs[position]
            }.attach()
        }
    }

}