package com.a2a.app.ui.category

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.a2a.app.MainActivity
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.Status
import com.a2a.app.data.model.AllSubCategoryModel
import com.a2a.app.data.network.CustomApi
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentSubCategoryBinding

class SubCategoryFragment :
    BaseFragment<FragmentSubCategoryBinding, CustomViewModel, CustomRepository>(
        FragmentSubCategoryBinding::inflate
    ) {

    private var categoryId: String? = null
    private lateinit var subCategoryList: List<AllSubCategoryModel.Result>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
        val args : SubCategoryFragmentArgs by navArgs()
        categoryId = args.categoryId.toString()

        if(this::subCategoryList.isInitialized)
            setData()
        else
            getSubCategory()
    }

    private fun setToolbar(){
        viewBinding.incToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getSubCategory() {
        viewModel.getAllSubCategories(categoryId!!)
        viewModel.allSubCategories.observe(viewLifecycleOwner){response->
            when(response){

                is Status.Loading -> {
                    showLoading()
                }
                is Status.Success -> {
                    stopShowingLoading()
                    subCategoryList = response.value.result
                    setData()
                }
                is Status.Failure -> {
                    stopShowingLoading()
                }
            }

        }
    }

    private fun setData(){
        viewBinding.includeSubCategory.rvSubCategory.run{
            layoutManager = GridLayoutManager(context, 2)
            adapter = SubCategoryAdapter(context, subCategoryList)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSubCategoryBinding.inflate(inflater, container, false)

    override fun getViewModel() = CustomViewModel::class.java

    override fun getFragmentRepository() = CustomRepository(remoteDataSource.getBaseUrl().create(
        CustomApi::class.java))

}