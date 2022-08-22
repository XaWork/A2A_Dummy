package com.a2a.app.ui.common

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.a2a.app.R
import com.a2a.app.data.model.CommonModel
import com.a2a.app.databinding.FragmentViewDetailsBinding
import com.bumptech.glide.Glide
import com.google.gson.Gson


class ViewDetailsFragment : Fragment(R.layout.fragment_view_details) {

    private lateinit var viewBinding: FragmentViewDetailsBinding
    private lateinit var details: CommonModel
    private lateinit var toolbarTitle: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentViewDetailsBinding.bind(view)
        getDetails()
        setToolbar()
        setDetails()

        viewBinding.btnBookNow.setOnClickListener{
            findNavController().navigate(R.id.action_global_bookFragment)
        }
    }

    private fun setDetails() {
        with(viewBinding){
            Glide.with(context!!)
                .load(details.file)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .into(image)

            tvTitle.text = details.name
            tvDesc.text = Html.fromHtml(details.description).toString()
        }
    }

    private fun getDetails() {
        val args: ViewDetailsFragmentArgs by navArgs()
        details = Gson().fromJson(args.details, CommonModel::class.java)
        toolbarTitle = args.name.toString()
    }

    private fun setToolbar() {
        with(viewBinding.incToolbar){
            toolbar.title = toolbarTitle
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}