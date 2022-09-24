package com.a2a.app.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.a2a.app.R
import com.a2a.app.ui.common.ProgressDialogFragment

abstract class BaseFragment1: Fragment() {

    private lateinit var progressDialog: ProgressDialogFragment


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
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