package com.a2a.app.common

import android.view.View

interface RvItemClick {
    fun clickWithPosition(name: String, position:Int)
    //fun clickWithView(name: String, position:Int, view: View)
}