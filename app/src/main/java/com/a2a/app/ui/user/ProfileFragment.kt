package com.a2a.app.ui.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.fragment.findNavController
import com.a2a.app.R
import com.a2a.app.common.BaseFragment
import com.a2a.app.data.network.UserApi
import com.a2a.app.data.repository.UserRepository
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.databinding.ContentProfileBinding
import com.a2a.app.databinding.FragmentOnBoardingBinding
import com.a2a.app.databinding.FragmentProfileBinding
import com.a2a.app.utils.AppUtils


class ProfileFragment: BaseFragment<FragmentProfileBinding, UserViewModel, UserRepository>(FragmentProfileBinding::inflate) {

    private lateinit var contentProfile: ContentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentProfile = viewBinding.contentProfile

        setToolbar()
        setData()

        with(contentProfile){
            tvBasicDetailsEdit.setOnClickListener{
                findNavController().navigate(R.id.action_profileFragment_to_basicCustomerDetailFragment)
            }
            llCustomerAddresses.setOnClickListener{
                findNavController().navigate(R.id.action_profileFragment_to_addressListFragment)
            }
        }
    }

    private fun setToolbar() {
        viewBinding.includeToolbar.toolbar.title = getString(R.string.profile)
        viewBinding.includeToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setData(){
        val user = AppUtils(context!!).getUser()
        if (user != null) {
            with(viewBinding.contentProfile){
                tvBasicDetailsName.text =  "Name: ${user.fullName}"
                tvEmail.text = user.email
                tvUsername.text = "Mobile:  ${user.mobile}"
            }
        } else {
            findNavController().navigate(R.id.action_global_onBoardingFragment)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

    override fun getViewModel() = UserViewModel::class.java

    override fun getFragmentRepository() = UserRepository(remoteDataSource.getBaseUrl().create(
        UserApi::class.java))
}