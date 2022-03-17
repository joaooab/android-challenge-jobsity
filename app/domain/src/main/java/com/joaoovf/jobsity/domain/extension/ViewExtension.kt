package com.joaoovf.jobsity.domain.extension

import android.view.View
import com.google.android.material.textfield.TextInputLayout

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

fun TextInputLayout.getString(): String {
	return try {
		this.editText?.text.toString()
	} catch (e: Exception) {
		""
	}
}