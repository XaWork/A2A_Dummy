package com.a2a.app.bindingadapters

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a2a.app.data.model.CommonModel
import com.a2a.app.ui.common.CommonAdapter



@BindingAdapter("bindList")
fun setRecycleViewAdapter(
    view: RecyclerView,
    dataList: List<CommonModel>?,
){
    if(dataList.isNullOrEmpty()){
        Log.d("rvBindingAdapter", "list is null or empty")
        return
    }

    val layoutManager: RecyclerView.LayoutManager? = view.layoutManager
    if(layoutManager == null)
        view.layoutManager = LinearLayoutManager(view.context)

    val adapter: CommonAdapter? = view.adapter as? CommonAdapter
    if(adapter == null)
        //view.adapter = CommonAdapter(data = dataList.toMutableList(), context = view.context)
    else
        adapter.updateDataList(dataList)

}
