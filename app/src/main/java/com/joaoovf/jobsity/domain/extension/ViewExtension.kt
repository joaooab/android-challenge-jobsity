package com.joaoovf.jobsity.domain.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.show() {
	this.visibility = View.VISIBLE
}

fun View.hide() {
	this.visibility = View.INVISIBLE

}

fun View.gone() {
	this.visibility = View.GONE

}

fun View.setVisible(visible: Boolean) {
	if (visible) show() else gone()
}