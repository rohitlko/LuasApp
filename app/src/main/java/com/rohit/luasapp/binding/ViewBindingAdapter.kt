package com.rohit.luasapp.binding

import android.view.View
import androidx.databinding.BindingAdapter

object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("isVisible")
    fun bindVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

}