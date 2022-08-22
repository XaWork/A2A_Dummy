package com.a2a.app.ui.customeviews

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller

class CustomDurationScroller(context: Context, interpolator: Interpolator?) : Scroller(context, interpolator) {

    constructor(context: Context): this(context, null)

    private var scrollFactor = 1.0

    fun setScrollDurationFactor(scrollFactor: Double) {
        this.scrollFactor = scrollFactor
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, (duration * scrollFactor).toInt())
    }
}