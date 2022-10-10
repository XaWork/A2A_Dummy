package com.a2a.app.ui.category

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.MainActivity
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.ItemClick
import com.a2a.app.common.RvItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.AllCategoryModel
import com.a2a.app.data.model.CommonModel
import com.a2a.app.data.network.CustomApi
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentCategoryBinding
import com.a2a.app.mappers.toCommonModel
import com.a2a.app.ui.common.CommonAdapter
import com.google.gson.Gson

class CategoryFragment :
    BaseFragment<FragmentCategoryBinding, CustomViewModel, CustomRepository>(FragmentCategoryBinding::inflate) {

    private lateinit var mainActivity: MainActivity
    private lateinit var allCategories: AllCategoryModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.showToolbarAndBottomNavigation()

        if (this::allCategories.isInitialized)
            setData()
        else
            getAllCategories()
    }

    private fun getAllCategories() {
        viewModel.getAllCategories()
        viewModel.allCategories.observe(viewLifecycleOwner) {
            when (it) {
                is Status.Loading -> showLoading()
                is Status.Success -> {
                    stopShowingLoading()
                    allCategories = it.value
                    setData()
                }
                is Status.Failure -> stopShowingLoading()
            }
        }

    }

    private fun setData() {
        val allCategoryList = ArrayList<CommonModel>()

        for (category in allCategories.result) {
            allCategoryList.add(category.toCommonModel())
        }

        //viewBinding.setVariable(BR.dataList, allCategoryList)
        viewBinding.rvCategory.run {
            layoutManager = LinearLayoutManager(context)
            adapter = CommonAdapter(allCategoryList.sortedBy { it.name } as MutableList<CommonModel>, context, object : ItemClick {
                override fun clickRvItem(name: String, model: Any) {
                    val category = model as CommonModel
                    when(name){
                        "details"->{
                            mainActivity.hideToolbarAndBottomNavigation()
                            val details = Gson().toJson(category, CommonModel::class.java)
                            val action = CategoryFragmentDirections.actionGlobalViewDetailsFragment(
                                details = details,
                                name = "Category Details")
                            findNavController().navigate(action)
                        }
                        "sub"->{
                            mainActivity.hideToolbarAndBottomNavigation()
                            val categoryId = category.id
                            val action =
                                CategoryFragmentDirections.actionCategoryFragmentToSubCategoryFragment(
                                    categoryId = categoryId,
                                    categoryName = category.name
                                )
                            findNavController().navigate(action)
                        }
                    }
                }
            })
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCategoryBinding.inflate(inflater, container, false)

    override fun getViewModel() = CustomViewModel::class.java

    override fun getFragmentRepository() =
        CustomRepository(remoteDataSource.getBaseUrl().create(CustomApi::class.java))
}