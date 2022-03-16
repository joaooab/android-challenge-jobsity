package com.joaoovf.jobsity.domain.extension

import android.view.View

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