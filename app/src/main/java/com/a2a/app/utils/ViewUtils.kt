package com.a2a.app.utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.a2a.app.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ViewUtils @Inject constructor(@ApplicationContext private var ctx: Context) :
    DialogFragment() {

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

    fun showLoading(fragmentManager: FragmentManager, title: String = "", message: String = "") {
        val args = Bundle()
        args.putString(ARG_TITLE, title)
        args.putString(ARG_MESSAGE, message)
        this.arguments = args

        this.isCancelable = false
        this.show(fragmentManager, "show")

    }

    fun showLoading(fragmentManager: FragmentManager) {
        showLoading(fragmentManager, "Please wait", "Loading…")
    }

    fun stopShowingLoading() {
        this.dismiss()
    }


    fun showLongToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }


    fun showShortToast(message: String) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
    }

    fun tryAgain() {
        Toast.makeText(ctx, "Try again", Toast.LENGTH_LONG).show()
    }

    fun showError(error: String?) {
        //Toasty.error(context, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
        Toast.makeText(ctx, error, Toast.LENGTH_SHORT).show()
    }

}