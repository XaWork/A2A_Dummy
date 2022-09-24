package com.a2a.app.ui.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.a2a.app.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class ProgressDialog @Inject constructor() : DialogFragment() {

    var title = ""
    var message = ""

    init {
        val args = Bundle()
        args.putString(ARG_TITLE, title)
        args.putString(ARG_MESSAGE, message)
        arguments = args
    }

    companion object {
        private val ARG_TITLE = "title"
        private val ARG_MESSAGE = "message"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.loading_view, container)
        val tvTitle = view.findViewById<TextView>(R.id.tvProgress_title)
        val tvMessage = view.findViewById<TextView>(R.id.tvProgress_message)
        title = arguments!!.getString(ARG_TITLE)!!
        message = arguments!!.getString(ARG_MESSAGE)!!
        tvTitle.text = title
        tvMessage.text = message
        return view
    }

    fun showLoading(fragmentManager: FragmentManager,title: String = "", message: String = "") {
        val args = Bundle()
        args.putString(ARG_TITLE, title)
        args.putString(ARG_MESSAGE, message)
        this.arguments = args

        this.isCancelable = false
        this.show(fragmentManager, "show")

    }

    fun showLoading(fragmentManager: FragmentManager) {
        showLoading(fragmentManager,"Please wait", "Loading…")
    }

    fun stopShowingLoading() {
        this.dismiss()
    }


}