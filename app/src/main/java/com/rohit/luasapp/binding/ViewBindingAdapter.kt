package com.rohit.luasapp.binding

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Binding adapters are responsible for making the appropriate framework calls to set values.
 * In this file we are creating binding adapters for a View.
 */
object ViewBindingAdapter {

    /**
     * Method to set the View visibility from the XML using the 'isVisible' attribute, passing
     * the data as a boolean.
     *
     * @param view
     * @param visible
     */
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