package com.a2a.app.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.a2a.app.R
import com.a2a.app.data.network.CustomApi
import com.a2a.app.data.network.UserApi
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.repository.UserRepository
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.data.viewmodel.UserViewModel
import com.a2a.app.data.viewmodelfactory.CustomViewModelFactory
import com.a2a.app.data.viewmodelfactory.UserViewModelFactory
import com.a2a.app.services.RemoteDataSource
import com.a2a.app.ui.common.ProgressDialogFragment
import com.a2a.app.utils.AppUtils

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel, BR : BaseRepository>(
    private val bindingInflate: (inflater: LayoutInflater) -> VB
) : Fragment()   {

    private lateinit var progressDialog: ProgressDialogFragment
    protected lateinit var appUtils: AppUtils
    private var binding: VB? = null
    val viewBinding: VB
        get() = binding as VB

    protected lateinit var viewModel: VM
    protected val remoteDataSource = RemoteDataSource()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflate.invoke(inflater)
        appUtils = AppUtils(requireContext())

        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory)[getViewModel()]

        if (binding == null) {
            throw IllegalArgumentException("Binding cannot be null")
        }
        return viewBinding.root
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentRepository(): BR

    fun getCutomViewModel(): CustomViewModel {
        val userRepository =
            CustomRepository(remoteDataSource.getBaseUrl().create(CustomApi::class.java))

        return ViewModelProvider(
            this,
            CustomViewModelFactory(userRepository)
        )[CustomViewModel::class.java]
    }

    fun getUserViewModel(): UserViewModel {
        val userRepository =
            UserRepository(remoteDataSource.getBaseUrl().create(UserApi::class.java))

        return ViewModelProvider(
            this,
            UserViewModelFactory(userRepository)
        )[UserViewModel::class.java]
    }

    fun showLoading() {
        showLoading(getString(R.string.please_wait), getString(R.string.loading))
    }

    fun showLoading(title: String, message: String) {
        val manager = parentFragmentManager
        progressDialog = ProgressDialogFragment.newInstance(title, message)
        progressDialog.isCancelable = false
        progressDialog.show(manager, "progress")
    }

    fun stopShowingLoading() {
        progressDialog.dismiss()
    }

    fun showError(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
    fun tryAgain() {
        Toast.makeText(context, "Try again", Toast.LENGTH_LONG).show()
    }

}