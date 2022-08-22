package com.a2a.app.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.common.BaseFragment
import com.a2a.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home){

    private lateinit var viewBinding: FragmentHomeBinding
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentHomeBinding.bind(view)

        mainActivity.showToolbarAndBottomNavigation()
        mainActivity.selectHomeNavMenu()

        setCarousel()
        setTestimonials()
        setCustomer()
        setBlog()
    }

    private fun setCarousel() {
        viewBinding.rvCarousal.run{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CarousalAdapter(
                listOf(R.drawable.rect_gray, R.drawable.rounded_back, R.drawable.side_nav_bar),
                context
            )
        }
    }

    private fun setTestimonials() {
        viewBinding.rvTestimonial.run{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = TestiMonialAdapter( listOf("", "", "", "", "", "", "", "", "", "", "", "", "", ""), context)
        }
    }

    private fun setCustomer() {
        viewBinding.rvCustomer.run{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CustomerAdapter(context)
        }
    }

    private fun setBlog() {
        viewBinding.rvBlog.run{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = BlogAdapter(context)
        }
    }
}