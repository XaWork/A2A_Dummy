package com.a2a.app.ui.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.a2a.app.R
import com.a2a.app.common.Status
import com.a2a.app.data.model.AllSubCategoryModel
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentSubCategoryBinding
import com.a2a.app.utils.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SubCategoryFragment : Fragment(R.layout.fragment_sub_category) {

    private var categoryId: String? = null
    private var categoryName: String? = ""
    private lateinit var viewBinding: FragmentSubCategoryBinding
    private lateinit var subCategoryList: List<AllSubCategoryModel.Result>
    private val viewModel by viewModels<CustomViewModel>()

    @Inject
    lateinit var viewUtils: ViewUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentSubCategoryBinding.bind(view)

        val args: SubCategoryFragmentArgs by navArgs()
        categoryId = args.categoryId.toString()
        categoryName = args.categoryName.toString()

        setToolbar()

        if (this::subCategoryList.isInitialized)
            setData()
        else
            getSubCategory()
    }

    private fun setToolbar() {
        viewBinding.incToolbar.toolbar.title = categoryName
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getSubCategory() {
        viewModel.getAllSubCategories(categoryId!!)
        .observe(viewLifecycleOwner) { response ->
            when (response) {

                is Status.Loading -> {
                    viewUtils.showLoading(parentFragmentManager)
                }
                is Status.Success -> {
                    viewUtils.stopShowingLoading()
                    subCategoryList = response.value.result
                    setData()
                }
                is Status.Failure -> {
                    viewUtils.stopShowingLoading()
                }
            }

        }
    }

    private fun setData() {
        viewBinding.includeSubCategory.rvSubCategory.run {
            layoutManager = GridLayoutManager(context, 2)
            adapter = SubCategoryAdapter(context, subCategoryList)
        }
    }

}