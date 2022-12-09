package com.a2a.app.ui.category

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.common.ItemClick
import com.a2a.app.common.Status
import com.a2a.app.data.model.AllCategoryModel
import com.a2a.app.data.model.CommonModel
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentCategoryBinding
import com.a2a.app.mappers.toCommonModel
import com.a2a.app.ui.common.CommonAdapter
import com.a2a.app.ui.components.A2ATopAppBar
import com.a2a.app.ui.components.SingleCommon
import com.a2a.app.ui.theme.CardCornerRadius
import com.a2a.app.ui.theme.MainBgColor
import com.a2a.app.ui.theme.ScreenPadding
import com.a2a.app.utils.ViewUtils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.fragment_category) {

    private lateinit var mainActivity: MainActivity
    private lateinit var viewBinding: FragmentCategoryBinding
    private lateinit var allCategories: AllCategoryModel
    private val viewModel by viewModels<CustomViewModel>()

    @Inject
    lateinit var viewUtils: ViewUtils

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentCategoryBinding.bind(view)

       // mainActivity.showToolbarAndBottomNavigation()

        if (this::allCategories.isInitialized)
            setData()
        else
            getAllCategories()
    }

    private fun getAllCategories() {
        viewModel.getAllCategories()
            .observe(viewLifecycleOwner) {
                when (it) {
                    is Status.Loading -> viewUtils.showLoading(parentFragmentManager)
                    is Status.Success -> {
                        viewUtils.stopShowingLoading()
                        allCategories = it.value
                        setData()
                    }
                    is Status.Failure -> viewUtils.stopShowingLoading()
                }
            }

    }

    private fun setData() {
        //map to common model
        val allCategoryList = ArrayList<CommonModel>()
        for (category in allCategories.result) {
            allCategoryList.add(category.toCommonModel())
        }

        //sort data by name
        allCategoryList.sortedBy { it.name }

        //show in recycle view
        viewBinding.categoryComposeview.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                CategoryScreen(categories = allCategoryList)
            }
        }

        //viewBinding.setVariable(BR.dataList, allCategoryList)
        /*      viewBinding.rvCategory.run {
                  layoutManager = LinearLayoutManager(context)
                  adapter =
                      CommonAdapter(allCategoryList.sortedBy { it.name } as MutableList<CommonModel>,
                          context,
                          object : ItemClick {
                              override fun clickRvItem(name: String, model: Any) {
                                  val category = model as CommonModel
                                  when (name) {
                                      "details" -> {
      
                                      }
                                      "sub" -> {
      
                                      }
                                  }
                              }
                          })
              }*/
    }

    private fun moveToViewDetailsScreen(category: CommonModel) {
       // mainActivity.hideToolbarAndBottomNavigation()
        val details = Gson().toJson(category, CommonModel::class.java)
        val action =
            CategoryFragmentDirections.actionGlobalViewDetailsFragment(
                details = details,
                name = "Category Details"
            )
        findNavController().navigate(action)
    }

    private fun moveToSubCategoryScreen(category: CommonModel) {
       // mainActivity.hideToolbarAndBottomNavigation()
        val categoryId = category.id
        val action =
            CategoryFragmentDirections.actionCategoryFragmentToSubCategoryFragment(
                categoryId = categoryId,
                categoryName = category.name
            )
        findNavController().navigate(action)
    }

    @Composable
    fun CategoryScreen(categories: List<CommonModel>) {
        Scaffold(topBar = {
            A2ATopAppBar(title = "Category") {
                findNavController().popBackStack()
            }
        }, content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.MainBgColor)
                    .padding(ScreenPadding)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(CardCornerRadius))
                ) {
                    items(categories) { service ->
                        SingleCommon(item = service) { task, item ->
                            when (task) {
                                "details" -> moveToViewDetailsScreen(category = item)
                                "subcategory" -> moveToSubCategoryScreen(category = item)
                            }
                        }
                    }
                }
            }
        })
    }
}