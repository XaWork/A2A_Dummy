package com.a2a.app


import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.core.view.doOnLayout
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView


inline fun <T> View.setupDropDown(
    list: Array<T>,
    crossinline itemFormatter: (T) -> String,
    crossinline onItemSelected: (T) -> Unit,
    crossinline onShow: (dialog: ListPopupWindow) -> Unit
) {
    val dropDown = ListPopupWindow(context)
    dropDown.setAdapter(
        ArrayAdapter(
            context,
            android.R.layout.simple_spinner_dropdown_item,
            list.map { itemFormatter(it) })
    )
    dropDown.anchorView = this
    dropDown.isModal = true

    doOnLayout {
        dropDown.width = measuredWidth
    }

    dropDown.setOnItemClickListener { _, _, position, _ ->
        dropDown.dismiss()
        onItemSelected.invoke(list[position])
    }

    setOnClickListener {
        onShow.invoke(dropDown)
    }
}


fun Fragment.hideSoftKeyboard() {
    activity?.apply {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService<InputMethodManager>()
            inputMethodManager?.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
}


fun Activity.hideSoftKeyboard() {
    apply {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService<InputMethodManager>()
            inputMethodManager?.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
}


fun RecyclerView.disableChangeAnimations() {
    (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
}